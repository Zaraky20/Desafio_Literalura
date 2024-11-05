package com.aluracursos.Desafio_litearatura.dto;

public enum GeneroDTO {
    ACCION ("Action"),
    ROMANCE ("Romance"),
    CRIMEN ("Crime"),
    COMEDIA ("Comedy"),
    DRAMA ("Drama"),
    AVENTURA ("Adventure"),
    FICCION ("Fiction"),
    DESCONOCIDO("Desconocido");

    private String genero;

    GeneroDTO(String generoGutendex) {
        this.genero = generoGutendex;
    }

    public static GeneroDTO fromString(String text){
        for (GeneroDTO generoEnum: GeneroDTO.values()){
            if (generoEnum.genero.equals(text)){
                return generoEnum;
            }
        }
        return GeneroDTO.DESCONOCIDO;
    }


}
