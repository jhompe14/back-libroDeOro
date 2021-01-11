package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.AuthRequestDTO;
import com.scouts.backlibrodeoro.dto.request.ContrasenaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.TrayectoriaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.UsuarioRequestDTO;
import com.scouts.backlibrodeoro.dto.response.UsuarioResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.RecuperoContrasena;
import com.scouts.backlibrodeoro.model.Usuario;

import javax.mail.MessagingException;

public interface UsuarioService {

    boolean authIsSuccess(AuthRequestDTO auth) throws NegocioException;

    UsuarioResponseDTO getUsuario(String usuario) throws NegocioException;

    Boolean validateUsuario(UsuarioRequestDTO usuarioRequestDTO) throws NegocioException;

    Boolean validateTrayectoria(TrayectoriaRequestDTO trayectoriaRequestDTO) throws NegocioException;

    Usuario createUsuario(UsuarioRequestDTO usuarioRequestDTO) throws NegocioException;

    Usuario updateUsuario(UsuarioRequestDTO usuarioRequestDTO) throws NegocioException;

    Usuario getUsuarioByRecovered(String idRecovered) throws NegocioException;

    void updateContrasena(String usuario, ContrasenaRequestDTO contrasenaRequestDTO) throws NegocioException;

    RecuperoContrasena setRecoveredContrasena(String usuario) throws NegocioException, MessagingException;

}
