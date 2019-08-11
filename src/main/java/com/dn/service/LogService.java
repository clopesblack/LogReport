package com.dn.service;

import com.dn.model.Rendering;
import com.dn.repository.RenderingRepository;
import com.dn.service.helper.RenderingMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.lang.Boolean.TRUE;

@Service
public class LogService {

    private final RenderingRepository repository;
    private final RenderingMapper renderingMapper;

    public LogService(final RenderingRepository repository, final RenderingMapper renderingMapper) {
        this.repository = repository;
        this.renderingMapper = renderingMapper;
    }

    public void saveRenderingsFrom(final String fileName) throws IOException {
        try (final Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(this::process);
        }
    }

    private void process(final String line) {
        if (isNotARenderingOrOtherImportantLine(line)) {
            return;
        }

        final Rendering rendering = renderingMapper.mapFrom(line);
        repository.save(rendering);
    }

    private Boolean isNotARenderingOrOtherImportantLine(final String line) {
        // TODO check if line is important or not
        return TRUE;
    }
}
