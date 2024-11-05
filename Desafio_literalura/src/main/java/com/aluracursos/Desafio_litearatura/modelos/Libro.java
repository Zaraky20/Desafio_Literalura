package com.aluracursos.Desafio_litearatura.modelos;

import com.aluracursos.Desafio_litearatura.dto.GeneroDTO;
import com.aluracursos.Desafio_litearatura.modelos.records.DatosLibro;
import com.aluracursos.Desafio_litearatura.modelos.records.Posters;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private Long libroId;

@Column(unique = true)
private String titulo;

@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
@JoinColumn(name = "autor_id")

private Autor autor;
@Enumerated(EnumType.STRING)
private GeneroDTO genero;
private String idioma;
private String imagen;
private Long cantidadDeDescargas;

public Libro() {
}

    public Libro(DatosLibro datosLibro) {
        this.libroId = datosLibro.libroId();
        this.titulo = datosLibro.titulo();

        if (datosLibro.autor() != null && !datosLibro.autor().isEmpty()) {
            this.autor = new Autor(datosLibro.autor().get(0));
        } else {
            this.autor = null;
        }
        this.genero = generoModificado(datosLibro.genero());
        this.idioma = idiomaModificado(datosLibro.idioma());
        this.imagen = imagenModificada(datosLibro.imagen());


        this.cantidadDeDescargas = Optional.ofNullable(datosLibro.cantidadDeDescargas()).orElse(0L);
    }

public Libro(Libro libro) {
}

private GeneroDTO generoModificado(List<String> generos) {
    if (generos == null || generos.isEmpty()) {
        return GeneroDTO.DESCONOCIDO;
    }
    Optional<String> firstGenero = generos.stream()
            .map(g -> {
                int index = g.indexOf("--");
                return index != -1 ? g.substring(index + 2).trim() : null;
            })
            .filter(Objects::nonNull)
            .findFirst();
    return firstGenero.map(GeneroDTO::fromString).orElse(GeneroDTO.DESCONOCIDO);
}

private String idiomaModificado(List<String> idiomas) {
    if (idiomas == null || idiomas.isEmpty()) {
        return "Desconocido";
    }
    return idiomas.get(0);
}

private String imagenModificada(Posters posters) {
    if (posters == null || posters.imagen().isEmpty()) {
        return "Sin imagen";
    }
    return posters.imagen();
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public GeneroDTO getGenero() {
    return genero;
}

public void setGenero(GeneroDTO genero) {
    this.genero = genero;
}

public String getImagen() {
    return imagen;
}

public void setImagen(String imagen) {
    this.imagen = imagen;
}

public Long getLibroId() {
    return libroId;
}

public void setLibroId(Long libroId) {
    this.libroId = libroId;
}

public String getTitulo() {
    return titulo;
}

public void setTitulo(String titulo) {
    this.titulo = titulo;
}

public Autor getAutores() {
    return autor;
}

public void setAutores(Autor autores) {
    this.autor = autores;
}

public String getIdioma() {
    return idioma;
}

public void setIdioma(String idioma) {
    this.idioma = idioma;
}

public Long getCantidadDeDescargas() {
    return cantidadDeDescargas;
}

public void setCantidadDeDescargas(Long cantidadDeDescargas) {
    this.cantidadDeDescargas = cantidadDeDescargas;
}

@Override
public String toString() {
    return "  \nid=" + id +
            "  \nLibro id=" + libroId +
            ", \ntitulo='" + titulo + '\'' +
            ", \nauthors=" + (autor != null ? autor.getNombre() : "N/A") +
            ", \ngenero=" + genero +
            ", \nidioma=" + idioma +
            ", \nimagen=" + imagen +
            ", \ncantidadDescargas=" + cantidadDeDescargas;
}
}