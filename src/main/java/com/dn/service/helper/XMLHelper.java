package com.dn.service.helper;

import com.dn.model.Rendering;
import com.dn.model.Report;
import com.dn.model.exception.ErrorTryingConvertXMLException;
import com.dn.model.exception.ErrorTryingCreateFileException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static java.lang.Boolean.TRUE;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

@Component
public class XMLHelper {

    public void createXMLReport(final Path path) {
        try {
            final JAXBContext context = JAXBContext.newInstance(Report.class);
            final Marshaller m = context.createMarshaller();
            m.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
            final BufferedWriter writer = new BufferedWriter(new FileWriter(path.getParent() + "report.xml"));
            m.marshal(Report.builder().build(), writer);
            writer.close();
        } catch (JAXBException e) {
            throw new ErrorTryingConvertXMLException("Unable to transform this report into XML.", e);
        } catch (IOException e) {
            throw new ErrorTryingCreateFileException("Error trying to create the report file.", e);
        }
    }

    public void saveInXMLReport(final Rendering rendering, final Path path) {
        //TODO fix the problem with default constructor
        // AND INSERT THE 'TOP 100' INTO THE XML FILE ALREADY CREATED
    }
}
