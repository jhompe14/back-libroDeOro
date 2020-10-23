package com.scouts.backlibrodeoro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AnecdotaDTO {

    private String nombre;

    @JsonFormat(pattern="dd/MM/yy")
    private Date fecha;

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
