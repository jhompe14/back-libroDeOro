package com.scouts.backlibrodeoro.types;

public enum TypeIntegrante {

    ACTIVO("ACTIVO"),
    EXINTEGR("EX-INTEGRANTE");

    private final String value;

    TypeIntegrante(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
