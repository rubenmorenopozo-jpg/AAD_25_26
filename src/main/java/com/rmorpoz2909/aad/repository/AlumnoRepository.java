package com.rmorpoz2909.aad.repository;

import com.rmorpoz2909.aad.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByNif(String nif);
}