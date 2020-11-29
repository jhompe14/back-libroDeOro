package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaResponseDTO;
import com.scouts.backlibrodeoro.model.Anecdota;

import java.util.List;
import java.util.Optional;

public interface AnecdotaRepositoryCustom {
    public Optional<AnecdotaResponseDTO> getAnecdotaById(Integer idAnecdota);
    public Integer countAnecdotasGrid(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO);
    public List<AnecdotaGridResponseDTO> getAnecdotasGrid(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO);
}
