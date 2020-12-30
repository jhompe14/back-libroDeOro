package com.scouts.backlibrodeoro.dto.request;

public class CatalogAnecdotaRequestDTO {

    private String usuario;

    private Integer page;

    public CatalogAnecdotaRequestDTO(String usuario, String page) {
        this.usuario = usuario;
        this.page = Integer.parseInt(page);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
