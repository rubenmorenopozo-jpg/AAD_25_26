package com.rmorpoz2909.aad;

import com.rmorpoz2909.aad.application.ManagementService;
import com.rmorpoz2909.aad.model.Alumno;
import com.rmorpoz2909.aad.repository.AlumnoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

	private final ManagementService managementService;
	private final AlumnoRepository AlumnoRepository;

	public Application(ManagementService managementService, AlumnoRepository AlumnoRepository) {
		this.managementService = managementService;
		this.AlumnoRepository = AlumnoRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		log.info("--- INICIANDO PRUEBAS DE LA ACTIVIDAD 3.1 ---");

		Alumno carlitos = new Alumno();
		carlitos.setNif("66280457T");
		carlitos.setNombre("Carlitos cani");
		carlitos.setEmail("carlitos@g.educaand.es");
		carlitos.setCourse("DAWN");

		carlitos = managementService.createAlumno(carlitos);
		log.info("Alumno guardado: {}", carlitos);

		com.rmorpoz2909.aad.model.Module prog = new com.rmorpoz2909.aad.model.Module();
		prog.setCode("0485");
		prog.setName("Programación");
		prog.setHours(250);

		prog = managementService.createModule(prog);
		log.info("Módulo guardado: {}", prog);

		managementService.enrollAlumnoInModule(carlitos.getId(), prog.getId());
		log.info("Matrícula creada correctamente.");

		log.warn("Lanzando error forzado para probar @Transactional...");
		throw new RuntimeException("Forzando rollback de la transacción");
	}
}