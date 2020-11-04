package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;

import java.util.List;

public interface AnecdotaRepositoryCustom {
    public List<AnecdotaGridResponseDTO> getAnecdotasGrid(FilterAnecdotaRequestDTO filterAnecdotaRequestDTO);
}
