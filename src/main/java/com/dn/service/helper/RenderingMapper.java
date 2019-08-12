package com.dn.service.helper;

import com.dn.model.Rendering;
import com.dn.model.Rendering.RenderingBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.valueOf;

@Component
public class RenderingMapper {

    public final String[] renderingInformation = {"startRendering", "getRendering", "uid:", "getPageCount", "Document:"};

    public Rendering mapFrom(final String line) {
        final RenderingBuilder renderingBuilder = Rendering.builder();
        final String[] split = line.substring(24).trim().split("\\s+");
        for (int i = 0; i < split.length; i++) {
            if (i > 0) {
                renderingBuilder.documentId(getDocumentOrPage(split[i - 1], split[i], 3));
                renderingBuilder.page(getDocumentOrPage(split[i - 1], split[i], 4));
            }
            renderingBuilder.starts(addRenderingTimeStamp(line, split[i], 0));
            renderingBuilder.getRenderings(addRenderingTimeStamp(line, split[i], 1));
            renderingBuilder.UID(getUID(line, split[i]));
        }
        return renderingBuilder.build();
    }

    private String getUID(final String line, final String word) {
        if (line.contains(renderingInformation[2]) && word.matches("\\d{13}-\\d{4}")) {
            return word;
        }
        return null;
    }

    private Integer getDocumentOrPage(final String previousWord, final String actualWord, final Integer positionRendering) {
        if (previousWord.equals(renderingInformation[positionRendering])) {
            return valueOf(actualWord);
        }
        return null;
    }

    private List<LocalDateTime> addRenderingTimeStamp(final String line, final String word, final Integer position) {
        final String timeStampLine = line.substring(0, 23);
        if (line.contains(renderingInformation[position])) {
            return Collections.singletonList(
                    LocalDateTime.parse(timeStampLine, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS")));
        }
        return Collections.emptyList();
    }
}