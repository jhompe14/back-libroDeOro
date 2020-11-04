package com.scouts.backlibrodeoro.dto.request;

public class AnecdotaRequestDTO {

    private String nombre;

    private String fecha;

    private String descripcion;

    private String usuario;

    private Integer rama;

    private Integer seccion;

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

    public Integer getRama() {
        return rama;
    }

    public void setRama(Integer rama) {
        this.rama = rama;
    }

    public Integer getSeccion() {
        return seccion;
    }

    public void setSeccion(Integer seccion) {
        this.seccion = seccion;
    }
}
