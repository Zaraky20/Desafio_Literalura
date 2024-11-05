package com.aluracursos.Desafio_litearatura;

import com.aluracursos.Desafio_litearatura.biblioteca.Biblioteca;
import com.aluracursos.Desafio_litearatura.repository.IAutorRepository;
import com.aluracursos.Desafio_litearatura.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioLitearaturaApplication implements CommandLineRunner {
	@Autowired
	private ILibroRepository libroRepositorio;
	@Autowired
	private IAutorRepository autorRepositorio;

	public static void main(String[] args) {

		SpringApplication.run(DesafioLitearaturaApplication.class, args);
	}

	public void run(String... args) throws Exception {

		Biblioteca biblioteca = new Biblioteca(libroRepositorio, autorRepositorio);
		biblioteca.muestraMenu();

	}
}
