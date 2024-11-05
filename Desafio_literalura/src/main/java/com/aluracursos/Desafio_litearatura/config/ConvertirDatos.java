package com.aluracursos.Desafio_litearatura.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertirDatos implements IConvierteDatos {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T convierteDatosJsonAJava(String json, Class<T> clase) {
        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("El JSON de entrada está vacío o es nulo.");
        }
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al procesar el JSON de entrada.", e);
        }
    }
}


