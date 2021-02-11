package com.scouts.backlibrodeoro.service.impl;

import com.scouts.backlibrodeoro.repository.*;
import com.scouts.backlibrodeoro.service.CargoService;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.util.QueryUtil;
import com.scouts.backlibrodeoro.dto.request.CargoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.types.TypeCargo;
import com.scouts.backlibrodeoro.validator.impl.CargoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CargoServiceImpl implements CargoService {

    private final CargoRepository cargoRepository;
    private final GrupoRepository grupoRepository;
    private final RamaRepository ramaRepository;
    private final SeccionRepository seccionRepository;
    private final TrayectoriaRepository trayectoriaRepository;
    private final CargoValidator cargoValidator;

    @Autowired
    public CargoServiceImpl(CargoRepository cargoRepository, GrupoRepository grupoRepository, RamaRepository ramaRepository,
                            SeccionRepository seccionRepository, TrayectoriaRepository trayectoriaRepository,
                            CargoValidator cargoValidator) {
        this.cargoRepository = cargoRepository;
        this.grupoRepository = grupoRepository;
        this.ramaRepository = ramaRepository;
        this.seccionRepository = seccionRepository;
        this.trayectoriaRepository = trayectoriaRepository;
        this.cargoValidator = cargoValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cargo> getAllCargos(){
        return cargoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cargo> getAllCargosByType(String typeCargo, Integer idType){
        List<Cargo> cargoList = new ArrayList<>();
        TypeCargo typeCargoEnum = TypeCargo.valueOf(typeCargo);
        switch (typeCargoEnum){
            case GR:
                cargoList = cargoRepository.findCargoByTypeGrupo(idType);
                break;
            case RA:
                cargoList = cargoRepository.findCargoByTypeRama(idType);
                break;
            case SE:
                cargoList = cargoRepository.findCargoByTypeSeccion(idType);
                break;
            default:
                break;
        }
        return cargoList;
    }

    @Override
    @Transactional(readOnly = true)
    public Cargo getCargo(Integer id) throws NegocioException {
        return QueryUtil.getObjectById(cargoRepository, id);
    }

    @Override
    @Transactional
    public Cargo createCargo(String typeCargo, Integer idType, CargoRequestDTO cargoRequestDTO) throws NegocioException {
        Cargo cargo = new Cargo();
        cargo.setNombre(cargoRequestDTO.getNombre());
        cargo.setDescripcion(cargoRequestDTO.getDescripcion());

        this.cargoValidator.validator(cargo);
        this.assignamentTypeCargo(typeCargo, idType, cargo);

        return cargoRepository.save(cargo);
    }

    @Override
    @Transactional
    public Cargo updateCargo(Integer idCargo, CargoRequestDTO cargoRequestDTO) throws NegocioException {
        Cargo cargoEdit = QueryUtil.getObjectById(cargoRepository, idCargo);

        cargoEdit.setNombre(cargoRequestDTO.getNombre());
        cargoEdit.setDescripcion(cargoRequestDTO.getDescripcion());
        this.cargoValidator.validator(cargoEdit);

        return cargoRepository.save(cargoEdit);
    }

    @Override
    @Transactional
    public void deleteCargo(Integer idCargo) throws NegocioException {
        if(trayectoriaRepository.countTrayectoriaByCargo(idCargo)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_CARGO_TRAYECTORIAS_ACTIVAS, TypeException.VALIDATION);
        }

        cargoRepository.delete(Objects.requireNonNull(QueryUtil.getObjectById(cargoRepository, idCargo)));
    }

    private void assignamentTypeCargo(String typeCargo, Integer idType, Cargo cargo) throws NegocioException {
        TypeCargo typeCargoEnum = TypeCargo.valueOf(typeCargo);
        switch (typeCargoEnum){
            case GR:
                cargo.setGrupo(QueryUtil.getObjectById(grupoRepository, idType));
                break;
            case RA:
                cargo.setRama(QueryUtil.getObjectById(ramaRepository, idType));
                break;
            case SE:
                cargo.setSeccion(QueryUtil.getObjectById(seccionRepository, idType));
                break;
            default:
                break;
        }
    }

}
