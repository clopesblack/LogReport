package com.dn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder(toBuilder = true)
@Getter
public class LogEvent {

    @NonNull private final EventType type;
    @NonNull private final LogLine logLine;
}
