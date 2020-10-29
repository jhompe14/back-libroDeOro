package com.scouts.backlibrodeoro.dto.request;

import com.scouts.backlibrodeoro.util.GeneralValidates;

import java.util.Date;
import java.util.Optional;

public class FilterAnecdotaDTO {

    private Integer idGrupo;

    private Integer idRama;

    private Integer idSeccion;

    private Date fechaInicioAnecdota;

    private Date fechaFinAnecdota;

    private String estado;

    private String usuarioFilter;

    private String usuarioOwner;

    public FilterAnecdotaDTO(String idGrupo, String idRama, String idSeccion, String fechaInicioAnecdota,
                             String fechaFinAnecdota, String estado, String usuarioFilter, String usuarioOwner) {
        this.idGrupo = Optional.ofNullable(idGrupo).map(Integer::parseInt).orElse(null);
        this.idRama = Optional.ofNullable(idRama).map(Integer::parseInt).orElse(null);
        this.idSeccion = Optional.ofNullable(idSeccion).map(Integer::parseInt).orElse(null);
        this.fechaInicioAnecdota = GeneralValidates.validateFormatDate(fechaInicioAnecdota);
        this.fechaFinAnecdota = GeneralValidates.validateFormatDate(fechaFinAnecdota);
        this.estado = estado;
        this.usuarioFilter = usuarioFilter;
        this.usuarioOwner = usuarioOwner;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public Integer getIdRama() {
        return idRama;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public Date getFechaInicioAnecdota() {
        return fechaInicioAnecdota;
    }

    public Date getFechaFinAnecdota() {
        return fechaFinAnecdota;
    }

    public String getEstado() {
        return estado;
    }

    public String getUsuarioFilter() {
        return usuarioFilter;
    }

    public String getUsuarioOwner() {
        return usuarioOwner;
    }
}
