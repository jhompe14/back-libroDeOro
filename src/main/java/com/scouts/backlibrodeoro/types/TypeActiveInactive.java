package com.scouts.backlibrodeoro.types;

public enum  TypeActiveInactive {

    AC("ACTIVE"),
    IN("INACTIVE");

    private final String value;

    TypeActiveInactive(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
