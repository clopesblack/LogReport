package com.dn.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
public class LogEvent {

    @NonNull private final EventType type;
    @NonNull private final LogLine logLine;
}
