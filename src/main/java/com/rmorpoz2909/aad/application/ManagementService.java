package com.rmorpoz2909.aad.application;

import com.rmorpoz2909.aad.model.Enrollment;
import com.rmorpoz2909.aad.model.Alumno;
import com.rmorpoz2909.aad.repository.EnrollmentRepository;
import com.rmorpoz2909.aad.repository.ModuleRepository;
import com.rmorpoz2909.aad.repository.AlumnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final AlumnoRepository AlumnoRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public Alumno createAlumno(Alumno Alumno) {
        return AlumnoRepository.save(Alumno);
    }

    @Transactional
    public com.rmorpoz2909.aad.model.Module createModule(com.rmorpoz2909.aad.model.Module module) {
        return moduleRepository.save(module);
    }

    @Transactional
    public Enrollment enrollAlumnoInModule(Long AlumnoId, Long moduleId) {
        Alumno Alumno = AlumnoRepository.findById(AlumnoId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        com.rmorpoz2909.aad.model.Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        Enrollment enrollment = new Enrollment();
        enrollment.setAlumno(Alumno);
        enrollment.setModule(module);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setFinalGrade(0.0);

        return enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public int countEnrollments(Long AlumnoId) {
        Alumno Alumno = AlumnoRepository.findById(AlumnoId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        return Alumno.getEnrollments().size();
    }
}