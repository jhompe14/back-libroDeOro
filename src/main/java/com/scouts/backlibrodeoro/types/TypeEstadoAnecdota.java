package com.scouts.backlibrodeoro.types;

public enum TypeEstadoAnecdota {

    PA("PENDIENTE APROBACIÓN"),
    AP("APROBADO"),
    RE("RECHAZADO"),
    PM("PENDIENTE MODIFICACIÓN");

    private final String value;

    TypeEstadoAnecdota(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
