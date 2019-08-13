package com.dn.service.helper;

import com.dn.model.LogLevel;
import com.dn.model.LogLine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LogLineFactory {

    private static final String TIME_STAMP_PATTERN = "^[\\d\\-\\s:,]{23}";
    private static final String THREAD_PATTERN = "\\[.+?]";
    private static final String LOG_LEVEL_PATTERN = "[A-Z]{4,5}";
    private static final String MESSAGE_SENDER_CLASS_PATTERN = "\\[.+?]";
    private static final String MESSAGE_PATTERN = ".*";
    private static final Pattern LOG_LINE_PATTERN = Pattern.compile("(" + TIME_STAMP_PATTERN + ")\\s(" + THREAD_PATTERN + ")\\s" +
            "(" + LOG_LEVEL_PATTERN + ")\\s\\s?(" + MESSAGE_SENDER_CLASS_PATTERN + "):\\s(" + MESSAGE_PATTERN + ")");

    public Optional<LogLine> buildFrom(final String line) {
        final Matcher matcher = LOG_LINE_PATTERN.matcher(line);

        if (matcher.find()) {
            return Optional.of(LogLine.builder()
                    .timestamp(matcher.group(1))
                    .thread(matcher.group(2))
                    .logLevel(LogLevel.valueOf(matcher.group(3)))
                    .messageSenderClass(matcher.group(4))
                    .message(matcher.group(5))
                    .build());
        }
        return Optional.empty();
    }
}