package com.dn.service;

import com.dn.model.LogEvent;
import com.dn.model.LogLine;
import com.dn.model.Rendering;
import com.dn.repository.RenderingRepository;
import com.dn.service.helper.LogEventMapper;
import com.dn.service.helper.RegexRenderingHelper;
import com.dn.service.helper.RenderingMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import static com.dn.model.EventType.*;
import static java.util.Collections.singletonList;

@Service
public class LogService {

    private final RenderingRepository repository;
    private final LogEventMapper logEventMapper;
    private final RenderingMapper renderingMapper;
    private final Map<String, LogLine> threadStartRenderingMap;
    private final RegexRenderingHelper regexRenderingHelper;

    public LogService(final RenderingRepository repository, final LogEventMapper logEventMapper,
                      final RenderingMapper renderingMapper, final RegexRenderingHelper regexRenderingHelper) {
        this.repository = repository;
        this.logEventMapper = logEventMapper;
        this.renderingMapper = renderingMapper;
        this.regexRenderingHelper = regexRenderingHelper;
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
            System.out.println("start rendering");
            return;
        }
        if (logEvent.getType() == START_RENDERING_RETURNED) {
            handleStartRenderingReturned(logEvent.getLogLine());
            System.out.println("start rendering returned");
            return;
        }
        if (logEvent.getType() == GET_RENDERING) {
            handleGetRendering(logEvent.getLogLine());
            System.out.println("get rendering");
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
        Matcher matcherUID = regexRenderingHelper.getMatcherUID(logLine);
        if (matcherUID != null) {
            Rendering rendering;
            String uid = matcherUID.group(1);
            Optional<Rendering> optionalRendering = repository.findById(uid);
            if (optionalRendering.isPresent()) {
                rendering = optionalRendering.get();
                rendering.getGetRenderings().add(logLine.getTimestamp());
            } else {
                rendering = Rendering.builder()
                        .UID(uid)
                        .getRenderings(singletonList(logLine.getTimestamp()))
                        .build();
            }
            repository.save(rendering);
        }
    }
}