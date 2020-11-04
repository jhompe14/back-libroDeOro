package com.scouts.backlibrodeoro.dto.response;

import java.util.Optional;

public class TrayectoriaResponseDTO implements java.io.Serializable{

    private Integer id;

    private Integer grupo;

    private String nombreGrupo;

    private Integer rama;

    private String nombreRama;

    private Integer seccion;

    private String nombreSeccion;

    private Integer cargo;

    private String nombreCargo;

    private Integer anioIngreso;

    private Integer anioRetiro;

    public TrayectoriaResponseDTO() {
    }

    public TrayectoriaResponseDTO(Integer id, Integer grupo, String nombreGrupo, Integer rama, String nombreRama,
                                  Integer seccion, String nombreSeccion, Integer cargo, String nombreCargo,
                                  Integer anioIngreso, Integer anioRetiro) {
        this.id = id;
        this.grupo = grupo;
        this.nombreGrupo = nombreGrupo;
        this.rama = Optional.ofNullable(rama).orElse(0);
        this.nombreRama = Optional.ofNullable(nombreRama).orElse("");
        this.seccion = Optional.ofNullable(seccion).orElse(0);
        this.nombreSeccion = Optional.ofNullable(nombreSeccion).orElse("");
        this.cargo = Optional.ofNullable(cargo).orElse(0);
        this.nombreCargo = Optional.ofNullable(nombreCargo).orElse("");
        this.anioIngreso = anioIngreso;
        this.anioRetiro =  Optional.ofNullable(anioRetiro).orElse(0);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Integer getRama() {
        return rama;
    }

    public void setRama(Integer rama) {
        this.rama = rama;
    }

    public String getNombreRama() {
        return nombreRama;
    }

    public void setNombreRama(String nombreRama) {
        this.nombreRama = nombreRama;
    }

    public Integer getSeccion() {
        return seccion;
    }

    public void setSeccion(Integer seccion) {
        this.seccion = seccion;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public Integer getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Integer anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public Integer getAnioRetiro() {
        return anioRetiro;
    }

    public void setAnioRetiro(Integer anioRetiro) {
        this.anioRetiro = anioRetiro;
    }
}
