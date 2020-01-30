package com.dn.service.helper;

import com.dn.model.EventType;
import com.dn.model.LogEvent;
import com.dn.model.LogLevel;
import com.dn.model.LogLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogEventMapperTest {

    @Mock
    private LogLineFactory logLineFactory;

    @InjectMocks
    private LogEventMapper logEventMapper;

    @Test
    public void Should_ReturnLogEventWithTypeStartRendering_WhenMapFromLine() {
        String message = "Executing request startRendering with arguments [114466, 0] on service object";

        when(logLineFactory.buildFrom(anyString())).thenReturn(Optional.of(buildLogLine(message)));

        Optional<LogEvent> logEvent = logEventMapper.mapFrom(anyString());

        assertTrue(logEvent.isPresent());
        assertEquals(buildLogEvent(EventType.START_RENDERING, message), logEvent.get());
    }

    @Test
    public void Should_ReturnLogEventWithTypeStartRenderingReturned_WhenMapFromLine() {
        String message = "Service startRendering returned 1286373785873-3536";

        when(logLineFactory.buildFrom(anyString())).thenReturn(Optional.of(buildLogLine(message)));

        Optional<LogEvent> logEvent = logEventMapper.mapFrom(anyString());

        assertTrue(logEvent.isPresent());
        assertEquals(buildLogEvent(EventType.START_RENDERING_RETURNED, message), logEvent.get());
    }

    @Test
    public void Should_ReturnLogEventWithTypeGetRendering_WhenMapFromLine() {
        String message = "Executing request getRendering with arguments [1286373785873-3536] on service object";

        when(logLineFactory.buildFrom(anyString())).thenReturn(Optional.of(buildLogLine(message)));

        Optional<LogEvent> logEvent = logEventMapper.mapFrom(anyString());

        assertTrue(logEvent.isPresent());
        assertEquals(buildLogEvent(EventType.GET_RENDERING, message), logEvent.get());
    }

    @Test
    public void Should_ReturnOptionalEmpty_WhenMapFromLineWithMessageNotExpected() {
        Optional<LogEvent> logEvent = logEventMapper.mapFrom(anyString());
        assertTrue(logEvent.isEmpty());
    }

    private LogEvent buildLogEvent(final EventType type, final String message) {
        return LogEvent.builder()
                .logLine(buildLogLine(message))
                .type(type)
                .build();
    }

    private LogLine buildLogLine(final String message) {
        return LogLine.builder().timestamp("2010-10-06 09:02:50,269")
                .logLevel(LogLevel.WARN)
                .thread("[Thread1]")
                .message(message)
                .messageSenderClass("[Task]")
                .build();
    }
}