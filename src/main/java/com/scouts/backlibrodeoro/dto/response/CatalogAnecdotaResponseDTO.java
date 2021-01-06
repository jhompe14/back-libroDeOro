package com.scouts.backlibrodeoro.dto.response;

import com.scouts.backlibrodeoro.util.LibroOroUtil;

import java.util.Date;

public class CatalogAnecdotaResponseDTO {

    private Integer idAnecdota;

    private String nombreSuceso;

    private String descripcionSuceso;

    private String fechaSuceso;

    private String nombreUsuario;

    private String usuario;

    public CatalogAnecdotaResponseDTO(Integer idAnecdota, String nombreSuceso, String descripcionSuceso, Date fechaSuceso,
                                      String nombreUsuario, String apellidoUsuario, String usuario) {
        this.idAnecdota = idAnecdota;
        this.nombreSuceso = nombreSuceso;
        this.descripcionSuceso = descripcionSuceso;
        this.fechaSuceso = LibroOroUtil.setFormatDate(fechaSuceso);
        this.nombreUsuario = nombreUsuario+" "+apellidoUsuario;
        this.usuario = usuario;
    }

    public Integer getIdAnecdota() {
        return idAnecdota;
    }

    public String getNombreSuceso() {
        return nombreSuceso;
    }

    public String getDescripcionSuceso() {
        return descripcionSuceso;
    }

    public String getFechaSuceso() {
        return fechaSuceso;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getUsuario() {
        return usuario;
    }
}
