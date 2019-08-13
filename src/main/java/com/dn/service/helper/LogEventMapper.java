
package com.dn.service.helper;

import com.dn.model.LogEvent;
import com.dn.model.LogEvent.LogEventBuilder;
import com.dn.model.LogLine;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.dn.model.EventType.*;

@Component
public class LogEventMapper {

    private static final String START_RENDERING_MESSAGE = "Executing request startRendering";
    private static final String START_RENDERING_RETURNED_MESSAGE = "Service startRendering returned";
    private static final String GET_RENDERING_MESSAGE = "Executing request getRendering";

    private final LogLineFactory logLineFactory;

    public LogEventMapper(final LogLineFactory logLineFactory) {
        this.logLineFactory = logLineFactory;
    }

    public Optional<LogEvent> mapFrom(final String line) {
        return logLineFactory.buildFrom(line).flatMap(this::getLogEvent);
    }

    private Optional<LogEvent> getLogEvent(final LogLine logLine) {
        final LogEventBuilder logEventBuilder = LogEvent.builder()
                .logLine(logLine);

        if (logLine.getMessage().contains(START_RENDERING_MESSAGE)) {
            return Optional.of(logEventBuilder.type(START_RENDERING).build());
        }

        if (logLine.getMessage().contains(START_RENDERING_RETURNED_MESSAGE)) {
            return Optional.of(logEventBuilder.type(START_RENDERING_RETURNED).build());
        }

        if (logLine.getMessage().contains(GET_RENDERING_MESSAGE)) {
            return Optional.of(logEventBuilder.type(GET_RENDERING).build());
        }

        return Optional.empty();
    }
}