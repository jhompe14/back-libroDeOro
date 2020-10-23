package com.scouts.backlibrodeoro.types;

public enum TypeVisualizacion {

    PL("PUBLICO"),
    PR("PRIVADO");

    private final String value;

    TypeVisualizacion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
