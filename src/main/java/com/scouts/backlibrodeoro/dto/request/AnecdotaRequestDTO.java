package com.scouts.backlibrodeoro.dto.request;

public class AnecdotaRequestDTO {

    private String nombre;

    private String fecha;

    private String descripcion;

    private String usuario;

    private Integer idRama;

    private Integer idSeccion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getIdRama() {
        return idRama;
    }

    public void setIdRama(Integer idRama) {
        this.idRama = idRama;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }
}
