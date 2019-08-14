package com.dn.service;

import com.dn.model.Report;
import com.dn.repository.RenderingRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final RenderingRepository repository;

    public ReportService(final RenderingRepository repository) {
        this.repository = repository;
    }

    public Report mountReport() {
        int initialIndex = 0;
        int finalIndex = 100;
        repository.findAll(PageRequest.of(initialIndex, finalIndex));
        return null;
    }
}
