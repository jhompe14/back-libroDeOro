package com.scouts.backlibrodeoro.dto.request;

public class SeccionRequestDTO {
    private String nombre;
    private String descripcion;
    private Integer idRama;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdRama() {
        return idRama;
    }

    public void setIdRama(Integer idRama) {
        this.idRama = idRama;
    }
}
