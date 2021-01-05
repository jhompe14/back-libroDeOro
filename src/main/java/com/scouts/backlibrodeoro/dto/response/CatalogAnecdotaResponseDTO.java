package com.scouts.backlibrodeoro.dto.response;

public class CatalogAnecdotaResponseDTO {

    private String nombre;

    public CatalogAnecdotaResponseDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
