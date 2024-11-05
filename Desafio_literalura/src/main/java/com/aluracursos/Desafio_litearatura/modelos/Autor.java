package com.aluracursos.Desafio_litearatura.modelos;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    private Integer cumpleanos;

    private Integer fechaFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<Libro> libros;
    public Autor() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCumpleanos() {
        return cumpleanos;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Autor(com.aluracursos.Desafio_litearatura.modelos.records.Autor autor) {
        this.nombre = autor.nombre();
        this.cumpleanos = autor.cumpleanos();
        this.fechaFallecimiento = autor.fechaFallecimiento();
    }

    @Override
    public String toString() {
        return
                "nombre='" + nombre + '\'' +
                        ", cumpleaños=" + cumpleanos +
                        ", fechaFallecimiento=" + fechaFallecimiento;
    }
}




