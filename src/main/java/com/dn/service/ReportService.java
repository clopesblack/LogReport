package com.dn.service;

import com.dn.model.Rendering;
import com.dn.model.Report;
import com.dn.model.Summary;
import com.dn.repository.RenderingRepository;
import com.dn.service.helper.XMLHelper;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final RenderingRepository repository;
    private final XMLHelper xmlHelper;

    public ReportService(final RenderingRepository repository, final XMLHelper xmlHelper) {
        this.repository = repository;
        this.xmlHelper = xmlHelper;
    }

    public void generateXMLReport(final Path path) {
        xmlHelper.createXMLReport(path, mountReport());
    }

    private Report mountReport() {
        List<Rendering> renderings = new ArrayList<>();
        Integer unnecessary = 0;
        Integer duplicates = 0;

        for (Rendering rendering : repository.findAll()) {
            if (rendering.getCommandStarts() != null) {
                if (rendering.getCommandGetRenderings() == null) {
                    unnecessary++;
                    continue;
                }
                if (rendering.getCommandStarts().size() > 1) {
                    duplicates++;
                }
            }
            renderings.add(rendering);
        }

        Summary summary = Summary.builder()
                .count(renderings.size() + unnecessary)
                .duplicates(duplicates)
                .unnecessary(unnecessary).build();
        return new Report(renderings, summary);
    }
}
