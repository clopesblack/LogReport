package com.dn.service.helper;

import com.dn.model.LogLevel;
import com.dn.model.LogLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class LogLineFactoryTest {

    @InjectMocks
    private LogLineFactory factory;

    @Test
    public void Should_ReturnLogLine_WhenBuildFromStringLine() {
        String line = "2010-10-06 09:02:50,269 [AthenaTimer] WARN  [ReleaseLockedDocumentsTask]: Found 1 locked documents";
        Optional<LogLine> logLine = factory.buildFrom(line);
        assertTrue(logLine.isPresent());
        assertEquals(buildValidLogLine(), logLine.get());
    }

    @Test
    public void Should_ReturnOptionalEmpty_WhenBuildFromStringLineWithNotExpectedPattern() {
        Optional<LogLine> logLine = factory.buildFrom("2010-10-06 09:02:50,461 INFO Posting event: Document");
        assertTrue(logLine.isEmpty());
    }

    private LogLine buildValidLogLine() {
        return LogLine.builder().timestamp("2010-10-06 09:02:50,269")
                .logLevel(LogLevel.WARN)
                .thread("[AthenaTimer]")
                .message("Found 1 locked documents")
                .messageSenderClass("[ReleaseLockedDocumentsTask]")
                .build();
    }
}