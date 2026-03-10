package com.rmorpoz2909.aad.application;

import com.rmorpoz2909.aad.model.Enrollment;
import com.rmorpoz2909.aad.model.Student;
import com.rmorpoz2909.aad.repository.EnrollmentRepository;
import com.rmorpoz2909.aad.repository.ModuleRepository;
import com.rmorpoz2909.aad.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public com.rmorpoz2909.aad.model.Module createModule(com.rmorpoz2909.aad.model.Module module) {
        return moduleRepository.save(module); // Ahora sí coincidirán los tipos
    }

    @Transactional
    public Enrollment enrollStudentInModule(Long studentId, Long moduleId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        // Buscamos usando tu clase específica
        com.rmorpoz2909.aad.model.Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setModule(module);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setFinalGrade(0.0);

        return enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public int countEnrollments(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        return student.getEnrollments().size();
    }
}