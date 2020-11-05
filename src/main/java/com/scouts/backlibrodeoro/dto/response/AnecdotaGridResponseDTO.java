package com.scouts.backlibrodeoro.dto.response;

import java.util.Date;

public class AnecdotaGridResponseDTO implements java.io.Serializable{

    private Integer idAnecdota;

    private String nombreGrupo;

    private String nombreRama;

    private String nombreSeccion;

    private Date fechaSuceso;

    private String usuarioRegistro;

    private String estado;

    private String usuarioGestion;

    public AnecdotaGridResponseDTO() {
    }

    public AnecdotaGridResponseDTO(Integer idAnecdota, String nombreGrupo, String nombreRama, String nombreSeccion,
                                   Date fechaSuceso, String usuarioRegistro, String estado, String usuarioGestion) {
        this.idAnecdota = idAnecdota;
        this.nombreGrupo = nombreGrupo;
        this.nombreRama = nombreRama;
        this.nombreSeccion = nombreSeccion;
        this.fechaSuceso = fechaSuceso;
        this.usuarioRegistro = usuarioRegistro;
        this.estado = estado;
        this.usuarioGestion = usuarioGestion;
    }

    public Integer getIdAnecdota() {
        return idAnecdota;
    }

    public void setIdAnecdota(Integer idAnecdota) {
        this.idAnecdota = idAnecdota;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreRama() {
        return nombreRama;
    }

    public void setNombreRama(String nombreRama) {
        this.nombreRama = nombreRama;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public Date getFechaSuceso() {
        return fechaSuceso;
    }

    public void setFechaSuceso(Date fechaSuceso) {
        this.fechaSuceso = fechaSuceso;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioGestion() {
        return usuarioGestion;
    }

    public void setUsuarioGestion(String usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }
}
