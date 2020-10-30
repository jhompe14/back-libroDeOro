package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridDTO;

import java.util.List;

public interface AnecdotaRepositoryCustom {
    public List<AnecdotaGridDTO> getAnecdotasGrid(FilterAnecdotaDTO filterAnecdotaDTO);
}
