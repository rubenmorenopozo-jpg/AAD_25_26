package com.rmorpoz2909.aad.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
@Data
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "Alumno_id")
    private Alumno Alumno;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    private LocalDate enrollmentDate;
    private Double finalGrade;
}