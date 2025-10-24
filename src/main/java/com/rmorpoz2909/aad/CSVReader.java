package com.rmorpoz2909.aad;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<Student> leer(Path ruta) throws IOException {
        //Creamos la lista vacía de estudiantes
        List<Student> lista = new ArrayList<>();
        //Abrimos el archivo CSV para leerlo línea a línea
        try (BufferedReader br = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String linea;
            boolean primera = true; //El boolean es para saltar la primera línea
            //Bucle para leer cada línea del archivo
            while ((linea = br.readLine()) != null) {
                if (linea.isEmpty()) continue;
                //Aquí saltamos la primera línea si es el encabezado con el boolean primera
                if (primera && linea.toLowerCase().startsWith("id,")) {
                    primera = false;
                    continue;
                }
                primera = false; //Indica que ya no estamos en la primera línea
                //Dividimos la línea por comas
                String[] partes = linea.trim().split(",", -1);
                //Si no hay 3 partes, seguimos con la siguiente línea
                if (partes.length < 3) continue;

                //Convertimos los datos de texto a sus tipos correspondientes
                int id = Integer.parseInt(partes[0].trim());
                String nombre = partes[1].trim();
                double nota = Double.parseDouble(partes[2].trim());

                //Cruamos un nuevo objeto Student y lo añadimos a la lista
                lista.add(new Student(id, nombre, nota));
            }
        }
        return lista;
    }
}

