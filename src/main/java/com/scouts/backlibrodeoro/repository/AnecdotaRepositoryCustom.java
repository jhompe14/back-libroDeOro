package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;

import java.util.List;

public interface AnecdotaRepositoryCustom {
    public Integer countAnecdotasGrid(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO);
    public List<AnecdotaGridResponseDTO> getAnecdotasGrid(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO);
}
