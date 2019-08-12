package com.dn.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.Boolean.TRUE;

@Service
public class AnalyzeService {

    private final LogService logService;

    public AnalyzeService(final LogService logService) {
        this.logService = logService;
    }

    public void process(final String fileName) {
        try {
            logService.saveRenderingsFrom(fileName);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}