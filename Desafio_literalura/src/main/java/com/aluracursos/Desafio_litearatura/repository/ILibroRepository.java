package com.aluracursos.Desafio_litearatura.repository;

import com.aluracursos.Desafio_litearatura.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);

    Libro findByTituloContainsIgnoreCase(String titulo);

    List<Libro> findByIdioma(String idioma);



//   @Query("SELECT l FROM libros l ORDER BY l.cantidadDescargas DESC LIMIT 10")
  // List<Libro> findTop10ByTituloByCantidadDeDescargas();
}
