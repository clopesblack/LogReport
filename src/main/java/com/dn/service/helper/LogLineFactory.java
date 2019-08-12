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
            "(" + LOG_LEVEL_PATTERN + ")\\s(" + MESSAGE_SENDER_CLASS_PATTERN + "):\\s(" + MESSAGE_PATTERN + ")");

    public Optional<LogLine> buildFrom(final String line) {

        final Matcher matcher = LOG_LINE_PATTERN.matcher(line);

        if (matcher.find()) {
            return Optional.of(LogLine.builder()
                    .timestamp(matcher.group(0))
                    .thread(matcher.group(1))
                    .logLevel(LogLevel.valueOf(matcher.group(2)))
                    .messageSenderClass(matcher.group(3))
                    .message(matcher.group(4))
                    .build());
        }
        return Optional.empty();
    }
}



/*

The input for the test tool is a logfile that can be downloaded from
http://debreuck.neirynck.com/opdracht/server.zip. This is a plain text file (log file),
where each line has a fixed format: <timestamp> [<thread>]<loglevel> [<message
sender class>]: <log message>
The timestamp format is:
YYYY-MM-DD HH:mm:ss,SSS
(e.g. 2010-10-06 09:02:10,357)

2010-10-06 09:02:13,634 [WorkerThread-2] DEBUG [RenderingQueue]: Adding command to queue: { RenderingCommand - uid: 1286373733634-5423 }
2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423


 */