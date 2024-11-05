package com.aluracursos.Desafio_litearatura.repository;

import com.aluracursos.Desafio_litearatura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAutorRepository extends JpaRepository<Autor, Long> {
    @Override
    List<Autor> findAll();
    List<Autor> findByCumpleanosLessThanOrFechaFallecimientoGreaterThanEqual(int anoBuscado, int anoBuscado1);
    Optional<Autor> findFirstByNombreContainsIgnoreCase(String escritor);
}
