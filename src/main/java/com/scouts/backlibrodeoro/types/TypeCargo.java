package com.scouts.backlibrodeoro.types;

public enum TypeCargo {

    GR("GRUPO"),
    RA("RAMA"),
    SE("SECCION");

    private final String value;

    TypeCargo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
