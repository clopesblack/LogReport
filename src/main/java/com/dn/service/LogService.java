package com.dn.service;

import com.dn.model.LogEvent;
import com.dn.model.LogLine;
import com.dn.model.Rendering;
import com.dn.repository.RenderingRepository;
import com.dn.service.helper.LogEventMapper;
import com.dn.service.helper.RenderingHelper;
import com.dn.service.helper.RenderingMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.dn.model.EventType.*;
import static java.util.Collections.singletonList;

@Service
public class LogService {

    private final RenderingRepository repository;
    private final LogEventMapper logEventMapper;
    private final RenderingMapper renderingMapper;
    private final Map<String, LogLine> threadStartRenderingMap;
    private final RenderingHelper renderingHelper;

    public LogService(final RenderingRepository repository, final LogEventMapper logEventMapper,
                      final RenderingMapper renderingMapper, final RenderingHelper renderingHelper) {
        this.repository = repository;
        this.logEventMapper = logEventMapper;
        this.renderingMapper = renderingMapper;
        this.renderingHelper = renderingHelper;
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
            return;
        }
        if (logEvent.getType() == GET_RENDERING) {
            handleGetRendering(logEvent.getLogLine());
        }
    }

    private void handleStartRendering(final LogLine logLine) {
        threadStartRenderingMap.put(logLine.getThread(), logLine);
    }

    private void handleStartRenderingReturned(final LogLine logLine) {
        String uid = renderingHelper.getMatcherUID(logLine).group(1);
        Optional<Rendering> renderingById = repository.findById(uid);
        Rendering rendering = null;
        if (renderingById.isPresent()) {
            rendering = renderingHelper.updateStartRendering(logLine.getTimestamp(), renderingById.get());
        } else {
            Optional<Rendering> optionalRendering = renderingMapper.map(threadStartRenderingMap.get(logLine.getThread()), logLine);
            if (optionalRendering.isPresent()) {
                rendering = optionalRendering.get();
                threadStartRenderingMap.remove(logLine.getThread());
            }
        }
        repository.save(rendering);
    }

    private void handleGetRendering(final LogLine logLine) {
        String uid = renderingHelper.getMatcherUID(logLine).group(1);
        Optional<Rendering> optionalRendering = repository.findById(uid);
        Rendering rendering = optionalRendering.map(value -> renderingHelper.updateGetRendering(logLine.getTimestamp(), value))
                .orElseGet(() -> Rendering.builder().UID(uid).commandGetRenderings(singletonList(logLine.getTimestamp())).build());
        repository.save(rendering);
    }
}