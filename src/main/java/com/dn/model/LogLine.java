package com.dn.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
public class LogLine {

    @NonNull private final String timestamp;
    @NonNull private final String thread;
    @NonNull private final LogLevel logLevel;
    @NonNull private final String messageSenderClass;
    @NonNull private final String message;
}