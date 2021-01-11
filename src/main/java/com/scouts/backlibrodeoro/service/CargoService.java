package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.CargoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;

import java.util.List;

public interface CargoService {

    List<Cargo> getAllCargos();

    List<Cargo> getAllCargosByType(String typeCargo, Integer idType);

    Cargo getCargo(Integer id) throws NegocioException;

    Cargo createCargo(String typeCargo, Integer idType, CargoRequestDTO cargoRequestDTO) throws NegocioException;

    Cargo updateCargo(Integer idCargo, CargoRequestDTO cargoRequestDTO) throws NegocioException;

    void deleteCargo(Integer idCargo) throws NegocioException;

}
