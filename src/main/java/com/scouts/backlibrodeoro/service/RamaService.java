package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.RamaRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Rama;

import java.util.List;

public interface RamaService {

    List<Rama> getAllRamas();

    Rama getRama(Integer id) throws NegocioException;

    Rama createRama(RamaRequestDTO ramaRequestDTO, Integer idGrupo) throws NegocioException;

    Rama updateRama(Integer idRama, RamaRequestDTO ramaRequestDTO) throws NegocioException;

    void deleteRama(Integer idRama) throws NegocioException;

}
