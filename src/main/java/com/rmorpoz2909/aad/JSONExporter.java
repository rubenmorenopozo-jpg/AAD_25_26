package com.rmorpoz2909.aad;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONExporter {

    public static void exportarJSON(List<Student> students, File destino) throws IOException {
        //Creamos el objeto ObjectMapper, sirve para convertir objetos Java a JSON y viceversa.
        ObjectMapper mapper = new ObjectMapper();

        //Escribimos la lista de estudiantes en el archivo JSON con formato legible
        //El PrettPrinter hace que se vea más bonito y legible.
        mapper.writerWithDefaultPrettyPrinter().writeValue(destino, students);
    }
}
