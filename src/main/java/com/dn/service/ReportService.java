package com.dn.service;

import com.dn.repository.RenderingRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final RenderingRepository repository;

    public ReportService(final RenderingRepository repository) {
        this.repository = repository;
    }
}
