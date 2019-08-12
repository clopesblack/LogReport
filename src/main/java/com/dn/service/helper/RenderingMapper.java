package com.dn.service.helper;

import com.dn.model.LogLine;
import com.dn.model.Rendering;
import com.dn.model.Rendering.RenderingBuilder;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenderingMapper {

    private static final Pattern DOCUMENT_AND_PAGE_PATTERN = Pattern.compile("\\[(\\d+)\\,\\s(\\d+)\\]");
    private static final Pattern UID_PATTERN = Pattern.compile("(\\d+\\-\\d+)");

    public Optional<Rendering> map(final LogLine startRenderingLogLine, final LogLine startRenderingReturnLogLine) {

        final Matcher documentAndPageMatcher = DOCUMENT_AND_PAGE_PATTERN.matcher(startRenderingLogLine.getMessage());
        if (!documentAndPageMatcher.find()) {
            return Optional.empty();
        }

        final RenderingBuilder renderingBuilder = Rendering.builder()
                .documentId(documentAndPageMatcher.group(0))
                .page(documentAndPageMatcher.group(1));

        final Matcher uIDMatcher = UID_PATTERN.matcher(startRenderingReturnLogLine.getMessage());
        if (!uIDMatcher.find()) {
            return Optional.empty();
        }

        return Optional.of(renderingBuilder
                .UID(uIDMatcher.group(0))
                .build());
    }
}
