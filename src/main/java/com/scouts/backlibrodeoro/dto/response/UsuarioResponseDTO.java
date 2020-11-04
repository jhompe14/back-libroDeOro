package com.scouts.backlibrodeoro.dto.response;

import java.util.List;

public class UsuarioResponseDTO {

    private String usuario;

    private String nombres;

    private String apellidos;

    private String tipoIntegrante;

    private String correo;

    private String telefono;

    private String direccion;

    private String ciudad;

    private String tipoUsuario;

    private List<TrayectoriaResponseDTO> trayectoria;

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

    public String getTipoIntegrante() {
        return tipoIntegrante;
    }

    public void setTipoIntegrante(String tipoIntegrante) {
        this.tipoIntegrante = tipoIntegrante;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<TrayectoriaResponseDTO> getTrayectoria() {
        return trayectoria;
    }

    public void setTrayectoria(List<TrayectoriaResponseDTO> trayectoria) {
        this.trayectoria = trayectoria;
    }
}
