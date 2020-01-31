package com.dn.service.helper;

import com.dn.model.LogLevel;
import com.dn.model.LogLine;
import com.dn.model.Rendering;
import com.dn.model.exception.NotFoundDocumentAndPageException;
import com.dn.model.exception.NotFoundUIDException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RenderingMapperTest {

    @Mock
    private RenderingHelper renderingHelper;

    @InjectMocks
    private RenderingMapper renderingMapper;

    private static final Pattern DOCUMENT_AND_PAGE_PATTERN = Pattern.compile("\\[(\\d+),\\s(\\d+)]");
    private static final Pattern UID_PATTERN = Pattern.compile("(\\d+-\\d+)");

    @Test
    public void Should_ReturnRendering_WhenMapFromStartAndReturnedLine() {
        LogLine startLogLine = buildLogLine("Executing request startRendering with arguments [1010, 1]");
        LogLine startReturnedLogLine = buildLogLine("Service startRendering returned 1286373733634-5423");

        when(renderingHelper.getMatcherDocumentAndPage(startLogLine)).thenReturn(getMatcherDocumentAndPage(startLogLine));
        when(renderingHelper.getMatcherUID(startReturnedLogLine)).thenReturn(getMatcherUID(startReturnedLogLine));

        Optional<Rendering> optionalRendering = renderingMapper.map(startLogLine, startReturnedLogLine);

        assertTrue(optionalRendering.isPresent());

        Rendering rendering = buildRendering();
        Rendering renderingFromMap = optionalRendering.get();

        Assert.assertEquals(rendering.getDocumentId(), renderingFromMap.getDocumentId());
        Assert.assertEquals(rendering.getUID(), renderingFromMap.getUID());
        Assert.assertEquals(rendering.getPage(), renderingFromMap.getPage());
    }

    private Matcher getMatcherDocumentAndPage(final LogLine logLine) {
        Matcher matcherDocumentAndPage = DOCUMENT_AND_PAGE_PATTERN.matcher(logLine.getMessage());
        matcherDocumentAndPage.find();
        return matcherDocumentAndPage;
    }

    private Matcher getMatcherUID(final LogLine logLine) {
        Matcher matcherUid = UID_PATTERN.matcher(logLine.getMessage());
        matcherUid.find();
        return matcherUid;
    }

    @Test(expected = NotFoundDocumentAndPageException.class)
    public void Should_ThrowsException_WhenMapFromStartAndReturnedLineNotMatchedDocumentAndPage() {
        LogLine logLine = buildLogLine("");
        when(renderingHelper.getMatcherDocumentAndPage(any()))
                .thenThrow(NotFoundDocumentAndPageException.class);
        renderingMapper.map(logLine, logLine);
    }

    @Test(expected = NotFoundUIDException.class)
    public void Should_ThrowsException_WhenMapFromStartAndReturnedLineNotMatchedUID() {
        LogLine logLine = buildLogLine("");
        when(renderingHelper.getMatcherUID(any())).thenThrow(NotFoundUIDException.class);
        renderingMapper.map(logLine, logLine);
    }

    private LogLine buildLogLine(final String message) {
        return LogLine.builder().timestamp("2010-10-06 09:02:50,269")
                .logLevel(LogLevel.WARN)
                .thread("[Thread1]")
                .message(message)
                .messageSenderClass("[Task]")
                .build();
    }

    private Rendering buildRendering() {
        return Rendering.builder()
                .commandGetRenderings(Collections.emptyList())
                .commandStarts(Collections.emptyList())
                .documentId("1010")
                .page("1")
                .UID("1286373733634-5423")
                .build();
    }
}