package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.*;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.dto.response.UsuarioGridResponseDTO;
import com.scouts.backlibrodeoro.dto.response.UsuarioResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.RecuperoContrasena;
import com.scouts.backlibrodeoro.model.Usuario;

import javax.mail.MessagingException;
import java.util.List;

public interface UsuarioService {

    boolean authIsSuccess(AuthRequestDTO auth) throws NegocioException;

    Integer countFilterUsuario(FilterUsuarioGridRequestDTO filterUsuarioGridRequestDTO);

    List<UsuarioGridResponseDTO> getFilterUsuario(FilterUsuarioGridRequestDTO filterUsuarioGridRequestDTO);

    UsuarioResponseDTO getUsuario(String usuario) throws NegocioException;

    Boolean validateUsuario(UsuarioRequestDTO usuarioRequestDTO) throws NegocioException;

    Boolean validateTrayectoria(TrayectoriaRequestDTO trayectoriaRequestDTO) throws NegocioException;

    Usuario createUsuario(UsuarioRequestDTO usuarioRequestDTO) throws NegocioException;

    Usuario updateUsuario(UsuarioRequestDTO usuarioRequestDTO) throws NegocioException;

    Usuario getUsuarioByRecovered(String idRecovered) throws NegocioException;

    void updateContrasena(String usuario, ContrasenaRequestDTO contrasenaRequestDTO) throws NegocioException;

    RecuperoContrasena setRecoveredContrasena(String usuario) throws NegocioException, MessagingException;

}
