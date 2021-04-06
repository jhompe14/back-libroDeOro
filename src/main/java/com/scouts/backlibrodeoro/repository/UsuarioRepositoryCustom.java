package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.dto.request.FilterUsuarioGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.UsuarioGridResponseDTO;

import java.util.List;

public interface UsuarioRepositoryCustom {
    Integer countFilterUsuario(FilterUsuarioGridRequestDTO filterUsuarioGridRequestDTO);
    List<UsuarioGridResponseDTO> getFilterUsuario(FilterUsuarioGridRequestDTO filterUsuarioGridRequestDTO);
}
