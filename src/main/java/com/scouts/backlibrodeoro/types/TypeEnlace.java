package com.scouts.backlibrodeoro.types;

public enum TypeEnlace {

    IM("IMAGEN"),
    VI("VIDEO");

    private final String value;

    TypeEnlace(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
