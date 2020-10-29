package com.scouts.backlibrodeoro.dto.request;


public class TrayectoriaDTO {

    private Integer grupo;

    private Integer rama;

    private Integer seccion;

    private Integer cargo;

    private Integer anioIngreso;

    private Integer anioRetiro;

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
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

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
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
