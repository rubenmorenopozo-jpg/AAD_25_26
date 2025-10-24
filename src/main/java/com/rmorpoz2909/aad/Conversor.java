package com.rmorpoz2909.aad;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class Conversor {

    public static void main(String[] args) {
        Path csv = Paths.get("alumnos.csv");
        File json = new File("alumnos.json");
        File xml = new File("alumnos.xml");

        try {
            // Crear CSV de ejemplo si no existe
            if (!Files.exists(csv)) {
                String contenido = "id,nombre,nota\n1,Ana,8.5\n2,Juan,6.7\n3,Luis,9\n";
                Files.writeString(csv, contenido, StandardCharsets.UTF_8);
                log.info("CSV de ejemplo creado: {}", csv.toAbsolutePath());
            }

            // Llama a CSVReader para convertir las lineas del archivo CSV a lista de Student
            List<Student> students = CSVReader.leer(csv);
            log.info("Leídos {} alumnos desde {}", students.size(), csv.getFileName());

            // Exporta el CSV a JSON usando la librería Jackson
            JSONExporter.exportarJSON(students, json);
            log.info("Generado JSON en {}", json.getAbsolutePath());

            // Exporta el CSV a XML usando la librería Jackson
            XMLExporter.exportarXML(students, xml);
            log.info("Generado XML en {}", xml.getAbsolutePath());

        } catch (IOException | NumberFormatException e) {
            log.error("Error procesando archivos: {}", e.getMessage());
        }
    }
}
