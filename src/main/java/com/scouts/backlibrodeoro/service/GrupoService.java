package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.GrupoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;

import java.util.List;

public interface GrupoService {

    List<Grupo> getAllGrupos();

    Grupo getGrupo(Integer id) throws NegocioException;

    Grupo createGrupo(GrupoRequestDTO grupoRequestDTO) throws NegocioException;

    Grupo updateGrupo(Integer idGrupo, GrupoRequestDTO grupoRequestDTO) throws NegocioException;

    void deleteGrupo(Integer idGrupo) throws NegocioException;
}
