
package com.dn.service;

import com.dn.model.exception.ErrorTryingProcessLogFileException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class AnalyzeService {

    private final LogService logService;
    private final ReportService reportService;

    public AnalyzeService(final LogService logService, final ReportService reportService) {
        this.logService = logService;
        this.reportService = reportService;
    }

    public void process(final String fileName) {
        try {
            logService.saveRenderingsFrom(fileName);
            reportService.generateXMLReport(Paths.get(fileName));
        } catch (final IOException e) {
            throw new ErrorTryingProcessLogFileException("Error trying to process the log file.", e);
        }
    }
}