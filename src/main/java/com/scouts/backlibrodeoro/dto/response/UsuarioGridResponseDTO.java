package com.scouts.backlibrodeoro.dto.response;

import com.scouts.backlibrodeoro.types.TypeIntegrante;

public class UsuarioGridResponseDTO {

    private String nombres;

    private String apellidos;

    private String tipoIntegrante;

    private String correo;

    private String telefono;

    private String direccion;

    private String ciudad;

    private String usuario;

    public UsuarioGridResponseDTO(String nombres, String apellidos, String tipoIntegrante, String correo, String telefono, String direccion, String ciudad, String usuario) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoIntegrante = TypeIntegrante.valueOf(tipoIntegrante).getValue();
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
