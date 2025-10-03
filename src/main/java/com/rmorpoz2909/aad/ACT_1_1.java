package com.rmorpoz2909.aad;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class ACT_1_1 implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);
        int opcion = 0;

        log.info("Ingresa un directorio");
        Path ruta = Paths.get(scan.nextLine());
        File ruta2 = new File(ruta.toString());

        if (Files.exists(ruta) && Files.isDirectory(ruta)) {
            listar(ruta2);
        } else {
            log.warn("Directorio no válido");
            return;
        }

        while (opcion <= 4) {
            log.info("1.- Crear nuevo fichero");
            log.info("2.- Mover fichero de ubicación");
            log.info("3.- Borrar un fichero");
            log.info("4.- Salir");

            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion) {
                case 1:
                    log.info("Ponle nombre a tu fichero:");
                    String nombre = scan.nextLine();

                    log.info("¿En que directorio lo creamos?");
                    ruta = Paths.get(scan.nextLine());
                    Path nuevoFichero = ruta.resolve(nombre);
                    try {
                        if (!Files.exists(nuevoFichero)) {
                            Files.createFile(nuevoFichero);
                            log.info("Fichero creado");
                        } else {
                            log.warn("El fichero ya existe.");
                        }
                    } catch (IOException e) {
                        log.warn("Error al crear fichero: " + e.getMessage());
                    }
                    listar(ruta2);
                    break;
                case 2:
                    log.info("¿Qué archivo quieres mover? (Ruta completa del archivo)");
                    ruta = Paths.get(scan.nextLine());
                    log.info("¿A que directorio movemos el archivo?");
                    Path ruta3 = Paths.get(scan.nextLine());

                    if (Files.isDirectory(ruta3)) {
                        ruta3 = ruta3.resolve(ruta.getFileName());
                    }
                    try {
                        Files.move(ruta, ruta3);
                        log.info("Fichero movido.");
                    } catch (IOException e) {
                        log.warn("Error al mover fichero: " + e.getMessage());
                    }
                    listar(ruta2);
                    break;
                case 3:
                    log.info("¿Qué archivo deseas eliminar?");
                    ruta = Paths.get(scan.nextLine());
                    try {
                        Files.deleteIfExists(ruta);
                        log.info("Fichero eliminado.");
                    } catch (IOException e) {
                        log.warn("Error al eliminar fichero: " + e.getMessage());
                    }
                    listar(ruta2);
                    break;
            }
        }
    }

    private void listar(File directorio) {
        log.info("Contenido de: {}", directorio.getAbsolutePath());
        File[] archivos = directorio.listFiles();
        if (archivos == null) {
            log.warn("No se pudo listar el directorio.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (File archivo : archivos) {
            if (archivo.isDirectory()) {
                System.out.println("[DIR]  " + archivo.getName());
            } else {
                long espacio = archivo.length();
                String fecha = sdf.format(new Date(archivo.lastModified()));
                log.info("[FILE] " + archivo.getName() + " | " + espacio + " bytes | modificado: " + fecha);
            }
        }
    }
}
