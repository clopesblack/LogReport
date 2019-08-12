package com.dn;

import com.dn.service.AnalyzeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

import static java.lang.System.exit;
import static org.springframework.boot.Banner.Mode.OFF;

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
        // TODO use lombok log instead Sout
        System.out.println("Please enter the filename: ");
        final Scanner scanner = new Scanner(System.in);

        try {
            analyzeService.process(scanner.nextLine());
        } catch (final RuntimeException ex) {
            System.out.println("Error on try analyse the log file " + ex);
        }

        System.out.println("Log analysed successfully. Resulted saved!");
        //TODO change after the exit0
        exit(0);
    }
}