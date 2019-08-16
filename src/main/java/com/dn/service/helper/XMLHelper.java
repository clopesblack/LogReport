package com.dn.service.helper;

import com.dn.model.Report;
import com.dn.model.exception.ErrorTryingConvertXMLException;
import com.dn.model.exception.ErrorTryingCreateFileException;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

@Component
public class XMLHelper {

    public void createXMLReport(final Path path, final Report report) {
        try {
            JAXBContext context = JAXBContext.newInstance(Report.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(path.getParent() + path.getFileSystem().getSeparator() + "report.xml"));
            m.marshal(report, writer);
            writer.close();
        } catch (JAXBException e) {
            throw new ErrorTryingConvertXMLException("Unable to transform this report into XML.", e);
        } catch (IOException e) {
            throw new ErrorTryingCreateFileException("Error trying to create the report file.", e);
        }
    }
}
