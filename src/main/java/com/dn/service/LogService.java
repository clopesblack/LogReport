package com.dn.service;

import com.dn.model.LogEvent;
import com.dn.model.LogLine;
import com.dn.model.Rendering;
import com.dn.repository.RenderingRepository;
import com.dn.service.helper.LogEventMapper;
import com.dn.service.helper.RenderingMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.dn.model.EventType.*;

@Service
public class LogService {

    private final RenderingRepository repository;
    private final LogEventMapper logEventMapper;
    private final RenderingMapper renderingMapper;
    private final Map<String, LogLine> threadStartRenderingMap;

    public LogService(final RenderingRepository repository, final LogEventMapper logEventMapper, final RenderingMapper renderingMapper) {
        this.repository = repository;
        this.logEventMapper = logEventMapper;
        this.renderingMapper = renderingMapper;
        this.threadStartRenderingMap = new HashMap<>();
    }

    public void saveRenderingsFrom(final String fileName) throws IOException {
        try (final Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(this::process);
        }
    }

    private void process(final String line) {
        logEventMapper.mapFrom(line).ifPresent(this::process);
    }

    private void process(final LogEvent logEvent) {
        if (logEvent.getType() == START_RENDERING) {
            handleStartRendering(logEvent.getLogLine());
            return;
        }
        if (logEvent.getType() == START_RENDERING_RETURNED) {
            handleStartRenderingReturned(logEvent.getLogLine());
        }
        if (logEvent.getType() == GET_RENDERING) {
            handleGetRendering(logEvent.getLogLine());
        }
    }

    private void handleStartRendering(final LogLine logLine) {
        threadStartRenderingMap.put(logLine.getThread(), logLine);
    }

    private void handleStartRenderingReturned(final LogLine logLine) {
        renderingMapper.map(threadStartRenderingMap.remove(logLine.getThread()), logLine)
                .ifPresent(repository::save);
    }

    private void handleGetRendering(final LogLine logLine) {
        // TODO retrieve Rendering from database using logLine UID, populate the GetRendering event and save again
        //repository.findById(logLine.getMessage())
    }
}