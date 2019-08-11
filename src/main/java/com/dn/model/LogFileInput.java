package com.dn.model;

import java.time.LocalDateTime;
import java.util.List;

public class LogFileInput {

    private final LocalDateTime timeStampLine;
    private final List<String> logMessage;

    public LogFileInput(final LocalDateTime timeStampLine, final List<String> logMessage) {
        this.timeStampLine = timeStampLine;
        this.logMessage = logMessage;
    }

    public LocalDateTime getTimeStampLine() {
        return timeStampLine;
    }

    public List<String> getLogMessage() {
        return logMessage;
    }
}