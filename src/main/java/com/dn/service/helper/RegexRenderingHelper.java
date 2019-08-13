package com.dn.service.helper;

import com.dn.model.LogLine;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexRenderingHelper {

    private static final Pattern DOCUMENT_AND_PAGE_PATTERN = Pattern.compile("\\[(\\d+),\\s(\\d+)]");
    private static final Pattern UID_PATTERN = Pattern.compile("(\\d+-\\d+)");

    public Matcher getMatcherDocumentAndPage(LogLine logLine) {
        final Matcher documentAndPageMatcher = DOCUMENT_AND_PAGE_PATTERN.matcher(logLine.getMessage());
        if (!documentAndPageMatcher.find()) {
            return null;
        }
        return documentAndPageMatcher;
    }

    public Matcher getMatcherUID(LogLine logLine) {
        final Matcher uIDMatcher = UID_PATTERN.matcher(logLine.getMessage());
        if (!uIDMatcher.find()) {
            return null;
        }
        return uIDMatcher;
    }
}