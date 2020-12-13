package com.scouts.backlibrodeoro.types;

public enum TypeChangeContrasena {

    RC("Recupero"),
    MO("Modificacion");

    private final String value;

    TypeChangeContrasena(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
