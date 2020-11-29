package com.scouts.backlibrodeoro.dto.response;

import com.scouts.backlibrodeoro.util.UtilLibroOro;

import java.util.Date;

public class AnecdotaResponseDTO {

    private Integer id;

    private Integer idGrupo;

    private String nombreGrupo;

    private Integer idRama;

    private String nombreRama;

    private Integer idSeccion;

    private String nombreSeccion;

    private String nombre;

    private String fecha;

    private String usuario;

    private String estado;

    public AnecdotaResponseDTO(Integer id, Integer idGrupo, String nombreGrupo, Integer idRama, String nombreRama,
                               Integer idSeccion, String nombreSeccion, String nombre, Date fecha, String usuario,
                               String estado) {
        this.id = id;
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.idRama = idRama;
        this.nombreRama = nombreRama;
        this.idSeccion = idSeccion;
        this.nombreSeccion = nombreSeccion;
        this.nombre = nombre;
        this.fecha =  UtilLibroOro.setFormatDate(fecha);
        this.usuario = usuario;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public Integer getIdRama() {
        return idRama;
    }

    public String getNombreRama() {
        return nombreRama;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEstado() {
        return estado;
    }
}