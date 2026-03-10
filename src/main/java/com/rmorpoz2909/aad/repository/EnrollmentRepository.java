package com.rmorpoz2909.aad.repository;

import com.rmorpoz2909.aad.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Consulta JPQL para filtrar por nota mínima
    @Query("SELECT e FROM Enrollment e WHERE e.finalGrade >= :minGrade")
    List<Enrollment> findByMinFinalGrade(@Param("minGrade") Double minGrade);
}
