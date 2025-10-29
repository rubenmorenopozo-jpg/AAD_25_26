package com.rmorpoz2909.aad;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner {
    private static Scanner sc = new Scanner(System.in);
    //Codificación por defecto UTF-8
    private static Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    public static void addEvent() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/resources/app.log", true), charset))) {
            // Obtener fecha y hora actuales
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dateFormate = sdf.format(now);

            //Pedir al usuario el mensaje del evento
            log.info("Write the event message:");
            String event = sc.nextLine();

            //Muestra la fecha y hora actuales
            log.info("Date automatically set: {}", dateFormate);
            writer.write("[" + dateFormate + "] Usuario: " + event);
            writer.newLine();
        } catch (IOException e) {
            log.error("Error" + e.getMessage());
        }
    }

    public static void filterEvent() {
        log.info("Enter a date of the event for filter");
        String date = sc.nextLine();

        //Encontrar eventos en el archivo de log que coincidan con la fecha introducida.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/app.log"), charset))) {

            String line = "";
            boolean found = false; // Variable para ver si se encontraron eventos
            while ((line = reader.readLine()) != null) {
                // Si la línea contiene la fecha buscada, se muestra
                if (line.contains("[" + date)) {
                    log.info("Event found : {}", line);
                    found = true;
                }
            }

            //Si no se encuentra ningun evento, mostrar mensaje
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
                charset = StandardCharsets.UTF_8; //Cambiar a UTF-8
                log.info("Select a new charset: {}", charset);
                break;
            case 2:
                charset = StandardCharsets.ISO_8859_1; //Cambiar a ISO-8859-1
                log.info("Select a new charset: {}", charset);
                break;
            default:
                log.info("Invalid option");
                log.info("Select a new charset: {}", charset);
                break;
        }
    }

    @Override
    public void run(String... args) throws Exception {
        //Menu principal
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
