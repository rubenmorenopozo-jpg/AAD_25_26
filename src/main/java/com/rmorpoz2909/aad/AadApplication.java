package com.rmorpoz2909.aad;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner {
    private static Scanner sc = new Scanner(System.in);
    private static Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    public static void addEvent() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/resources/app.log", true), charset))) {
            log.info("Add time and date of the event (dd/MM/yyyy HH:mm:ss):");
            String date = sc.nextLine();

            log.info("Message event");
            String event = sc.nextLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.parse(date, dtf);

            log.info("Date: {}", now);
            writer.write("[" + now.format(dtf) + "] Usuario: " + event);
            writer.newLine();
        } catch (IOException e) {
            log.error("Error" + e.getMessage());
        }
    }

    public static void filterEvent() {
        log.info("Enter a date of the event for filter");
        String date = sc.nextLine();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/app.log"), charset))) {
            String line = "";
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("[" + date)) {
                    log.info("Event found : {}", line);
                    found = true;
                }
            }

            if (!found) {
                log.info("No events found for the given date: {}", date);
            }
        } catch (IOException e) {
            log.error("Error" + e.getMessage());
        }
    }

    public static void configurateCodification() {

        log.info("Configuration default is: {} ", charset);
        log.info("Select a new configuration");
        log.info("1) UTF-8");
        log.info("2) ISO-8859-1");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                charset = StandardCharsets.UTF_8;
                log.info("Select a new charset: {}", charset);
                break;
            case 2:
                charset = StandardCharsets.ISO_8859_1;
                break;
            default:
                log.info("Invalid option");
                log.info("Select a new charset: {}", charset);
                break;
        }
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            int option;
            log.info("Select an option");
            log.info("1.Add event");
            log.info("2.Filter envents");
            log.info("3.Configurate codificate");
            log.info("4.Exit");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    log.info("Option 1 selected");
                    addEvent();
                    break;
                case 2:
                    log.info("Option 2 selected");
                    filterEvent();
                    break;
                case 3:
                    log.info("Option 3 selected");
                    configurateCodification();
                    break;
                case 4:
                    log.info("Bye bye");
                    System.exit(0);
                default:
                    log.info("Invalid option");
                    break;
            }
        }
    }
}
