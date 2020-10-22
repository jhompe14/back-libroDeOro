package com.scouts.backlibrodeoro.types;

public enum TypeIntegrante {

    AC("ACTIVO"),
    EX("EX-INTEGRANTE");

    private final String value;

    TypeIntegrante(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
