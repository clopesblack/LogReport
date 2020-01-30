package com.dn.service.helper;

import com.dn.model.Rendering;
import com.dn.model.Report;
import com.dn.model.Summary;
import com.dn.model.exception.ErrorTryingCreateFileException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class XMLHelperTest {

    @InjectMocks
    private XMLHelper xmlHelper;

    @Test
    public void Should_CreateXMLReport_WithSuccess() {
        xmlHelper.createXMLReport(Paths.get("C:\\Users\\Public\\Downloads"), buildReport());
        File file = new File("C:\\Users\\Public\\Downloads\\report.xml");
        Assert.assertNotNull(file);
    }

    @Test(expected = ErrorTryingCreateFileException.class)
    public void Should_ThrowException_WhenTryCreatingFile() {
        xmlHelper.createXMLReport(Paths.get("\\path"), buildReport());
    }

    private Report buildReport() {
        Rendering rendering1 = Rendering.builder()
                .documentId("114400")
                .page("0")
                .commandStarts(Collections.singletonList("2010-10-06 09:02:13,631"))
                .commandGetRenderings(Collections.singletonList("2010-10-06 09:02:14,825")).build();
        Rendering rendering2 = Rendering.builder()
                .documentId("22555")
                .page("1")
                .commandStarts(Collections.singletonList("2010-10-06 09:03:26,774"))
                .commandGetRenderings(Arrays.asList("2010-10-06 09:03:27,985", "2010-10-06 09:03:59,790")).build();
        return new Report(Arrays.asList(rendering1, rendering2),
                Summary.builder().count(2).duplicates(0).unnecessary(0).build());
    }
}