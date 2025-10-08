package com.rmorpoz2909.aad;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
@SpringBootApplication
@Slf4j

public class ACT_1_2 implements CommandLineRunner {

    private static final String ARCHIVO = "alumnos.dat";
    private static final int NOMBRE = 20;
    private static final int REGISTRO = 4 + (NOMBRE * 2) + 8; // Cada alumno ocupará en el archivo 52 bytes.
    // Son 52 bytes porque: 4 bytes id + 40 bytes nombre + 8 bytes nota = 52 bytes
    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scan = new Scanner(System.in);
        String opcion = "";

        log.info("Gestión de Notas con Acceso Secuencial y Aleatorio");
        do {
            log.info("1.- Insertar alumno"); //Con acceso secuencial
            log.info("2.- Consultar alumno por Posición"); //Con acceso aleatorio
            log.info("3.- Modificar la nota"); //Sobreescribe la nota
            log.info("0.- Salir");
            System.out.print("Opción: ");
            opcion = scan.nextLine().trim();

            try {
                switch (opcion) {
                    case "1": insertar(scan); break;
                    case "2": consultar(scan); break;
                    case "3": modificarNota(scan); break;
                    case "0": log.info("Fin."); break;
                    default: log.warn("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                log.warn("Dato numérico inválido.");
            } catch (IOException e) {
                log.warn("Error de fichero: {}", e.getMessage()); // Si el fichero tiene error
            }
        } while (!"0".equals(opcion));

        scan.close();
    }

    // 1) Metodo para insertar alumnos
    private void insertar(Scanner scan) throws IOException {
        System.out.print("Id (int): ");
        int id = Integer.parseInt(scan.nextLine().trim());

        System.out.print("Nombre (máx 20): ");
        String nombre = ajustarNombre(scan.nextLine());

        System.out.print("Nota (double): ");
        double nota = Double.parseDouble(scan.nextLine().trim().replace(',', '.'));

        RandomAccessFile random = null; //Creamos el fichero
        try {
            random = new RandomAccessFile(ARCHIVO, "rw");
            random.seek(random.length()); //Vamos al final del fichero para crear al nuevo alumno.
            random.writeInt(id);
            for (int i = 0; i < NOMBRE; i++) random.writeChar(nombre.charAt(i));
            random.writeDouble(nota);
            log.info("Alumno insertado.");
        } finally {
            if (random != null) try { random.close(); } catch (IOException ignored) {} //Para cerrar el fichero.
        }
    }

    //Consultar por Posición
    private void consultar(Scanner scan) throws IOException {
        System.out.print("Posición a leer (empieza en 0): ");
        int posicion = Integer.parseInt(scan.nextLine().trim());

        RandomAccessFile random = null;
        try {
            random = new RandomAccessFile(ARCHIVO, "r");
            long total = random.length() / REGISTRO; //Para saber cuantos alumnos hay
            if (posicion < 0 || posicion >= total) {
                log.warn("Posición fuera de rango. Total registros: {}", total);
                return;
            }
            random.seek((long) posicion * REGISTRO); //Ir directamente al alumno que queremos
            int id = random.readInt();
            StringBuilder nombre = new StringBuilder(NOMBRE);
            for (int i = 0; i < NOMBRE; i++) nombre.append(random.readChar());
            double nota = random.readDouble();

            log.info("[{}] id={}, nombre='{}', nota={}", posicion, id, nombre.toString().trim(), nota);
        } finally {
            if (random != null) try { random.close(); } catch (IOException ignored) {} //Cerrar el fichero
        }
    }

    // Modificar la nota
    private void modificarNota(Scanner scan) throws IOException {
        System.out.print("Posición a modificar (empieza en 0): ");
        int posicion = Integer.parseInt(scan.nextLine().trim());
        System.out.print("Nueva nota: ");
        double nueva = Double.parseDouble(scan.nextLine().trim().replace(',', '.'));

        RandomAccessFile random = null;
        try {
            random = new RandomAccessFile(ARCHIVO, "rw");
            long total = random.length() / REGISTRO;
            if (posicion < 0 || posicion >= total) { //Si la posicion no existe salta este mensaje
                log.warn("Posición fuera de rango. Total registros: {}", total);
                return;
            }
            //Para calcular donde empieza el registro de la nota
            long offsetNota = (long) posicion * REGISTRO + 4L + (NOMBRE * 2L); // saltar id + nombre
            random.seek(offsetNota); //Para ir directamente a la nota del alumno.
            random.writeDouble(nueva); //Para sobreescribir la nota
            log.info("Nota actualizada.");
        } finally {
            if (random != null) try { random.close(); } catch (IOException ignored) {} //Cerrar el fichero
        }
    }

    // Metodo para hacer que el nombre tenga exáctamente 20 carácteres.
    private String ajustarNombre(String nombre) {
        //Si es nulo se queda vacío
        if (nombre == null) nombre = "";
        //Si tiene más de 20 carácteres se recorta.
        if (nombre.length() > NOMBRE) nombre = nombre.substring(0, NOMBRE);
        //Si tiene menos de 20 carácteres se rellena hasta 20 con espacios.
        StringBuilder sb = new StringBuilder(nombre);
        while (sb.length() < NOMBRE) sb.append(' ');
        //Devuelve el nombre ya ajustado
        return sb.toString();
    }
}
