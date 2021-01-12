package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.AnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.CatalogAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.EstadoAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaResponseDTO;
import com.scouts.backlibrodeoro.dto.response.CatalogAnecdotaResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.model.Enlace;

import javax.mail.MessagingException;
import java.util.List;

public interface AnecdotaService {

    Integer countFilterAnecdota(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO);

    List<AnecdotaGridResponseDTO> getFilterAnecdota(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO);

    Integer countCatalogAnecdota(CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO);

    List<CatalogAnecdotaResponseDTO> getCatalogAnecdota(CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO);

    AnecdotaResponseDTO getAnecdota(Integer id) throws NegocioException;

    Anecdota createAnecdota(AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException, MessagingException;

    Anecdota updateAnecdota(Integer idAnecdota, AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException,
            MessagingException;

    Anecdota updateEstadoVisualizacionAnecdota(Integer idAnecdota, EstadoAnecdotaRequestDTO estadoAnecdotaRequestDTO)
            throws NegocioException, MessagingException;

    void sendNotificationEstadoAnecdotaPM(Integer idAnecdota, String usuario);

    void deleteEnlaceAnecdota(Integer idEnlace) throws NegocioException;

    List<Enlace> getEnlacesByIdAnecdota(Integer idAnecdota);
}
