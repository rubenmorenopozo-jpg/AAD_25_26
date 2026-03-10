package com.rmorpoz2909.aad;

import com.rmorpoz2909.aad.application.ManagementService;
import com.rmorpoz2909.aad.model.Student;
import com.rmorpoz2909.aad.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

	private final ManagementService managementService;
	private final StudentRepository studentRepository;

	// Inyectamos el servicio y el repositorio por constructor
	public Application(ManagementService managementService, StudentRepository studentRepository) {
		this.managementService = managementService;
		this.studentRepository = studentRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		log.info("--- INICIANDO PRUEBAS DE LA ACTIVIDAD 3.1 ---");

		// 1. Crear un Alumno
		Student miriam = new Student();
		miriam.setNif("66280457T");
		miriam.setName("Miriam");
		miriam.setEmail("miriam@g.educaand.es");
		miriam.setCourse("DAW");

		miriam = managementService.createStudent(miriam);
		log.info("Alumno guardado: {}", miriam);

		// 2. Crear un Módulo (Usando la ruta completa para evitar el error de nombres)
		com.rmorpoz2909.aad.model.Module prog = new com.rmorpoz2909.aad.model.Module();
		prog.setCode("0485");
		prog.setName("Programación");
		prog.setHours(250);

		prog = managementService.createModule(prog);
		log.info("Módulo guardado: {}", prog);

		// 3. Realizar Matrícula
		managementService.enrollStudentInModule(miriam.getId(), prog.getId());
		log.info("Matrícula creada correctamente.");

		// 4. Verificación de Rollback
		// Según el PDF, debemos lanzar una excepción para ver cómo Spring deshace todo
		log.warn("Lanzando error forzado para probar @Transactional...");
		throw new RuntimeException("Forzando rollback de la transacción");
	}
}