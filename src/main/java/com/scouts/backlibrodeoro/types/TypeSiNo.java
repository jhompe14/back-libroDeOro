package com.scouts.backlibrodeoro.types;

public enum TypeSiNo {

    SI("SI"),
    NO("NO");

    private final String value;

    TypeSiNo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
