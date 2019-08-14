package com.dn.service.helper;

import com.dn.model.LogLine;
import com.dn.model.Rendering;
import com.dn.model.exception.NotFoundDocumentAndPageException;
import com.dn.model.exception.NotFoundUIDException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.singletonList;

@Component
public class RenderingHelper {

    private static final Pattern DOCUMENT_AND_PAGE_PATTERN = Pattern.compile("\\[(\\d+),\\s(\\d+)]");
    private static final Pattern UID_PATTERN = Pattern.compile("(\\d+-\\d+)");

    public Matcher getMatcherDocumentAndPage(LogLine logLine) {
        final Matcher documentAndPageMatcher = DOCUMENT_AND_PAGE_PATTERN.matcher(logLine.getMessage());
        if (!documentAndPageMatcher.find()) {
            throw new NotFoundDocumentAndPageException();
        }
        return documentAndPageMatcher;
    }

    public Matcher getMatcherUID(LogLine logLine) {
        final Matcher uIDMatcher = UID_PATTERN.matcher(logLine.getMessage());
        if (!uIDMatcher.find()) {
            throw new NotFoundUIDException();
        }
        return uIDMatcher;
    }

    public Rendering updateGetRendering(String timestampLine, Rendering renderingSaved) {
        Rendering rendering = renderingSaved;
        if (rendering.getGetRenderings() == null) {
            rendering = rendering.toBuilder().getRenderings(singletonList(timestampLine)).build();
        } else {
            rendering.getGetRenderings().add(timestampLine);
        }
        return rendering;
    }

    public Rendering updateStartRendering(String timestampLine, Rendering startRendering) {
        Rendering rendering = startRendering;
        if (rendering.getStarts() == null) {
            rendering = rendering.toBuilder().starts(singletonList(timestampLine)).build();
        } else {
            rendering.getStarts().add(timestampLine);
        }
        return rendering;
    }
}