package com.scouts.backlibrodeoro.types;

public enum TypeEstadoAnecdota {

    PA("PENDIENTE APROBACION"),
    AP("APROBADO"),
    RE("RECHAZADO"),
    PM("PENDIENTE DE MODIFICACION");

    private final String value;

    TypeEstadoAnecdota(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
