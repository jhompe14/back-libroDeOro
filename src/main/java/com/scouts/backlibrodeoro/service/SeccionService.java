package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.SeccionRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Seccion;

import java.util.List;

public interface SeccionService {

    List<Seccion> getAllSecciones();

    Seccion getSeccion(Integer id) throws NegocioException;

    Seccion createSeccion(SeccionRequestDTO seccionRequestDTO, Integer idRama) throws NegocioException;

    Seccion updateSeccion(Integer idSeccion, SeccionRequestDTO seccionRequestDTO) throws NegocioException;

    void deleteSeccion(Integer idSeccion) throws NegocioException;

}
