package com.scouts.backlibrodeoro.types;

public enum TypeUsuario {

    AD("ADMINISTRADOR"),
    IN("INTEGRANTE");

    private final String value;

    TypeUsuario(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
