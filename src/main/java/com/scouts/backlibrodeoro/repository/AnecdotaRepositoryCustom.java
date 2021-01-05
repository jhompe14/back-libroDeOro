package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.dto.request.CatalogAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaResponseDTO;
import com.scouts.backlibrodeoro.dto.response.CatalogAnecdotaResponseDTO;
import com.scouts.backlibrodeoro.model.Anecdota;

import java.util.List;
import java.util.Optional;

public interface AnecdotaRepositoryCustom {
    Optional<AnecdotaResponseDTO> getAnecdotaById(Integer idAnecdota);
    Integer countAnecdotasGrid(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO);
    List<AnecdotaGridResponseDTO> getAnecdotasGrid(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO);
    Integer countCatalogAnecdota(CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO);
    List<CatalogAnecdotaResponseDTO> getCatalogAnecdota(CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO);
}
