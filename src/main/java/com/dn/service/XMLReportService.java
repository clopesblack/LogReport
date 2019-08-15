package com.dn.service;

import com.dn.model.Rendering;
import com.dn.repository.RenderingRepository;
import com.dn.service.helper.XMLHelper;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class XMLReportService {

    private final RenderingRepository repository;
    private final XMLHelper xmlHelper;

    public XMLReportService(final RenderingRepository repository, final XMLHelper xmlHelper) {
        this.repository = repository;
        this.xmlHelper = xmlHelper;
    }

    public void generateXMLReport(final Path path) {
        xmlHelper.createXMLReport(path);
        Boolean repositoryIsNotEmpty = TRUE;
        //TODO COUNT THE UNNECESSARY AND DUPLICATES INSIDE THE WHILE LOGIC

        while (repositoryIsNotEmpty) {
            final List<Rendering> top100Renderings = repository.findTop100ByUID();

            if (top100Renderings.size() < 100) {
                repositoryIsNotEmpty = FALSE;
            }

            top100Renderings.forEach(rendering -> {
                xmlHelper.saveInXMLReport(rendering, path);
                repository.delete(rendering);
            });
        }

        //TODO CALL SaveInXMLReport TO INSERT THE SUMMARY INTO THE XML
    }
}
