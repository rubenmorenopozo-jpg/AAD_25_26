package com.rmorpoz2909.aad.repository;

import com.rmorpoz2909.aad.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNif(String nif);
}
