package com.dn.service;

import com.dn.model.Rendering;
import com.dn.model.Report;
import com.dn.model.Summary;
import com.dn.model.exception.ErrorTryingConvertXMLException;
import com.dn.model.exception.ErrorTryingCreateFileException;
import com.dn.repository.RenderingRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final RenderingRepository repository;

    public ReportService(final RenderingRepository repository) {
        this.repository = repository;
    }

    public void generateXMLReport(final Path path) {
        reportToXML(mountReport(), path);
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
        return Report.builder().rendering(renderings).summary(summary).build();
    }

    private void reportToXML(final Report report, final Path path) {
        try {
            JAXBContext context = JAXBContext.newInstance(Report.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.getParent() + "report.xml"));
            m.marshal(report, writer);
            writer.close();
        } catch (JAXBException e) {
            throw new ErrorTryingConvertXMLException("Unable to transform this report into XML.", e);
        } catch (IOException e) {
            throw new ErrorTryingCreateFileException("Error trying to create the report file.", e);
        }
    }
}
