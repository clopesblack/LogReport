
package com.dn.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class AnalyzeService {

    private final LogService logService;
    private final XMLReportService xmlReportService;

    public AnalyzeService(final LogService logService, final XMLReportService xmlReportService) {
        this.logService = logService;
        this.xmlReportService = xmlReportService;
    }

    public void process(final String fileName) {
        try {
            logService.saveRenderingsFrom(fileName);
            xmlReportService.generateXMLReport(Paths.get(fileName));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}