package com.aluracursos.Desafio_litearatura.modelos.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Posters(
        @JsonAlias("image/jpeg") String imagen
) {
}
