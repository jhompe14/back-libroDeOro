package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.dto.response.TrayectoriaResponseDTO;

import java.util.List;

public interface TrayectoriaRepositoryCustom {
    public List<TrayectoriaResponseDTO> findTrayectoriaResponseDTOByUsuario(String usuario);
}
