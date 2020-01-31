package com.dn.service;

import com.dn.repository.RenderingRepository;
import com.dn.service.helper.LogEventMapper;
import com.dn.service.helper.RenderingHelper;
import com.dn.service.helper.RenderingMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UncheckedIOException;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {

    @Mock
    private RenderingRepository repository;

    @Mock
    private LogEventMapper logEventMapper;

    @Mock
    private RenderingMapper renderingMapper;

    @Mock
    private RenderingHelper renderingHelper;

    @InjectMocks
    private LogService logService;

    @Test(expected = UncheckedIOException.class)
    public void Should_ThrowsException_WhenTrySaveRenderingsFromFile() throws IOException {
        String fileName = "server.log";
        RandomAccessFile randomAccessFile = lockFile(fileName);
        logService.saveRenderingsFrom(fileName);
        randomAccessFile.close();
    }

    private RandomAccessFile lockFile(String fileName) throws IOException {
        final RandomAccessFile raFile = new RandomAccessFile(fileName, "rw");
        raFile.getChannel().lock();
        return raFile;
    }
}