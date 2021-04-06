package com.scouts.backlibrodeoro.dto.request;

import java.util.Optional;

public class FilterUsuarioGridRequestDTO {

    private String usuario;

    private String nombres;

    private String apellidos;

    private Integer page;

    public FilterUsuarioGridRequestDTO(String usuario, String nombres, String apellidos, String page) {
        this.usuario = Optional.ofNullable(usuario).orElse("");
        this.nombres = Optional.ofNullable(nombres).orElse("");
        this.apellidos = Optional.ofNullable(apellidos).orElse("");
        this.page = Integer.parseInt(page);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
