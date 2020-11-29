package com.scouts.backlibrodeoro.dto.response;

import com.scouts.backlibrodeoro.types.TypeEstadoAnecdota;
import com.scouts.backlibrodeoro.util.UtilLibroOro;

import java.util.Date;

public class AnecdotaGridResponseDTO implements java.io.Serializable{

    private Integer idAnecdota;

    private String nombreGrupo;

    private String nombreRama;

    private String nombreSeccion;

    private String nombreSuceso;

    private String fechaSuceso;

    private String usuarioRegistro;

    private String estado;

    private String usuarioGestion;

    public AnecdotaGridResponseDTO() {
    }

    public AnecdotaGridResponseDTO(Integer idAnecdota, String nombreGrupo, String nombreRama, String nombreSeccion,
                                   String nombreSuceso, Date fechaSuceso, String usuarioRegistro, String estado,
                                   String usuarioGestion) {
        this.idAnecdota = idAnecdota;
        this.nombreGrupo = nombreGrupo;
        this.nombreRama = nombreRama;
        this.nombreSeccion = nombreSeccion;
        this.nombreSuceso = nombreSuceso;
        this.fechaSuceso = UtilLibroOro.setFormatDate(fechaSuceso);
        this.usuarioRegistro = usuarioRegistro;
        this.estado = TypeEstadoAnecdota.valueOf(estado).getValue();
        this.usuarioGestion = usuarioGestion;
    }

    public Integer getIdAnecdota() {
        return idAnecdota;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public String getNombreRama() {
        return nombreRama;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public String getFechaSuceso() {
        return fechaSuceso;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public String getUsuarioGestion() {
        return usuarioGestion;
    }

    public String getNombreSuceso() {
        return nombreSuceso;
    }
}
