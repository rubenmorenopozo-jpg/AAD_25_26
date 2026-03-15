package com.rmorpoz2909.aad.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nif;
    private String nombre;
    private String email;
    private String course;

    @OneToMany(mappedBy = "Alumno", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;
}
