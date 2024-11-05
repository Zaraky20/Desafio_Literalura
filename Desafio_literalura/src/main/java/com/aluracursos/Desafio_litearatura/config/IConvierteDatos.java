package com.aluracursos.Desafio_litearatura.config;

public interface IConvierteDatos {
    <T> T convierteDatosJsonAJava(String json, Class <T> clase);
}
