package com.dn.service.helper;

import com.dn.model.LogLevel;
import com.dn.model.LogLine;
import com.dn.model.Rendering;
import com.dn.model.exception.NotFoundDocumentAndPageException;
import com.dn.model.exception.NotFoundUIDException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class RenderingHelperTest {

    @InjectMocks
    private RenderingHelper helper;

    @Test
    public void Should_GetMatcherDocumentAndPage_WithSuccess() {
        LogLine logLine = buildLogLine("Executing request startRendering with arguments [1010, 1]");
        Matcher matcherDocumentAndPage = helper.getMatcherDocumentAndPage(logLine);
        assertNotNull(matcherDocumentAndPage);
    }

    @Test(expected = NotFoundDocumentAndPageException.class)
    public void Should_ThrowException_WhenGetMatcherDocumentAndPage() {
        helper.getMatcherDocumentAndPage(buildLogLine(""));
    }

    @Test
    public void Should_GetMatcherUID_WithSuccess() {
        LogLine logLine = buildLogLine("Service startRendering returned 1286373733634-5423");
        Matcher matcherUID = helper.getMatcherUID(logLine);
        assertNotNull(matcherUID);
    }

    @Test(expected = NotFoundUIDException.class)
    public void Should_ThrowException_WhenGetMatcherUID() {
        helper.getMatcherUID(buildLogLine(""));
    }

    @Test
    public void Should_CreateListGetRendering_WhenIsNull() {
        Rendering rendering = helper.updateGetRendering("2010-10-06 09:02:50,269",
                buildRendering(null, null));
        assertEquals(1, rendering.getCommandGetRenderings().size());
    }

    @Test
    public void Should_UpdateListGetRendering_WithSuccess() {
        List<String> getRendering = new ArrayList<>();
        getRendering.add("2010-10-06 09:02:50,269");

        Rendering rendering = helper.updateGetRendering("2010-10-06 09:02:50,272",
                buildRendering(null, getRendering));
        assertEquals(2, rendering.getCommandGetRenderings().size());
    }

    @Test
    public void Should_CreateListStartsRendering_WhenIsNull() {
        Rendering rendering = helper.updateStartRendering("2010-10-06 09:02:50,269",
                buildRendering(null, null));
        assertEquals(1, rendering.getCommandStarts().size());
    }

    @Test
    public void Should_UpdateListStartsRendering_WithSuccess() {
        List<String> getStart = new ArrayList<>();
        getStart.add("2010-10-06 09:02:50,269");

        Rendering rendering = helper.updateStartRendering("2010-10-06 09:02:50,272",
                buildRendering(getStart, null));
        assertEquals(2, rendering.getCommandStarts().size());
    }

    private Rendering buildRendering(final List<String> commandStarts, final List<String> commandGetRenderings) {
        return Rendering.builder()
                .commandGetRenderings(commandGetRenderings)
                .commandStarts(commandStarts)
                .documentId("110")
                .page("10")
                .UID("1286373733634-5423")
                .build();
    }

    private LogLine buildLogLine(final String message) {
        return LogLine.builder()
                .logLevel(LogLevel.INFO)
                .thread("[Thread-2]")
                .messageSenderClass("[ServiceClass]")
                .timestamp("2010-10-06 09:02:13,631")
                .message(message).build();
    }
}