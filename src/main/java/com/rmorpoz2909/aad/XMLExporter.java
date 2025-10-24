package com.rmorpoz2909.aad;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLExporter {

    public static void exportarXML(List<Student> students, File destino) throws IOException {
        //Creamos el objeto XmlMapper, sirve para convertir objetos Java a XML y viceversa.
        XmlMapper xmlMapper = new XmlMapper();
        //Escribimos la lista de estudiantes en el archivo XML con formato legible.
        //El PrettPrinter hace que se vea más bonito y legible.
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(destino, students);
    }
}
