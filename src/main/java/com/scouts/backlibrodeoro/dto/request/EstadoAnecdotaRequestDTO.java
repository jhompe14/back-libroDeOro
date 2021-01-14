package com.scouts.backlibrodeoro.dto.request;

public class EstadoAnecdotaRequestDTO {

    private String estado;
    private String usuarioModificacion;
    private String visualizacion;
    private String usuario;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getVisualizacion() {
        return visualizacion;
    }

    public void setVisualizacion(String visualizacion) {
        this.visualizacion = visualizacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
