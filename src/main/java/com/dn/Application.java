package com.dn;

import com.dn.model.exception.ErrorTryingAnalyzeLogFileException;
import com.dn.service.AnalyzeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

import static java.lang.System.exit;
import static org.springframework.boot.Banner.Mode.OFF;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final AnalyzeService analyzeService;

    public Application(final AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(OFF);
        app.run(args);
    }

    @Override
    public void run(final String... args) {
        log.info("Please enter the filename: ");
        final Scanner scanner = new Scanner(System.in);

        try {
            analyzeService.process(scanner.nextLine());
        } catch (final RuntimeException ex) {
            throw new ErrorTryingAnalyzeLogFileException("Error on try analyse the log file ", ex);
        }

        log.info("Log analysed successfully. Resulted saved!");
        exit(0);
    }
}