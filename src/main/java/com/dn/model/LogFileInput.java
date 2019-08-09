package com.dn.model;

import java.time.LocalDateTime;
import java.util.List;

public class LogFileInput {

    private final LocalDateTime timeStampLine;
    private final String threadLine;
    private final LogLevel logLevel;
    private final String messageSenderClass;
    private final List<String> logMessage;

    public LogFileInput(final LocalDateTime timeStampLine, final String threadLine, final LogLevel logLevel,
                        final String messageSenderClass, final List<String> logMessage) {
        this.timeStampLine = timeStampLine;
        this.threadLine = threadLine;
        this.logLevel = logLevel;
        this.messageSenderClass = messageSenderClass;
        this.logMessage = logMessage;
    }

    public LocalDateTime getTimeStampLine() {
        return timeStampLine;
    }

    public String getThreadLine() {
        return threadLine;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getMessageSenderClass() {
        return messageSenderClass;
    }

    public List<String> getLogMessage() {
        return logMessage;
    }
}