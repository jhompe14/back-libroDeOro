package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.CargoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.types.TypeCargo;
import com.scouts.backlibrodeoro.validator.CargoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;
    private final GrupoRepository grupoRepository;
    private final RamaRepository ramaRepository;
    private final SeccionRepository seccionRepository;
    private final CargoValidator cargoValidator;

    @Autowired
    public CargoService(CargoRepository cargoRepository, GrupoRepository grupoRepository, RamaRepository ramaRepository,
                        SeccionRepository seccionRepository, CargoValidator cargoValidator) {
        this.cargoRepository = cargoRepository;
        this.grupoRepository = grupoRepository;
        this.ramaRepository = ramaRepository;
        this.seccionRepository = seccionRepository;
        this.cargoValidator = cargoValidator;
    }

    @Transactional(readOnly = true)
    public List<Cargo> getAllCargos(){
        return cargoRepository.findAll();
    }

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

    @Transactional(readOnly = true)
    public Cargo getCargo(Integer id) throws NegocioException {
        return InspeccionService.getObjectById(cargoRepository, id);
    }

    @Transactional
    public Cargo createCargo(String typeCargo, Integer idType, CargoRequestDTO cargoRequestDTO) throws NegocioException {
        Cargo cargo = new Cargo();
        cargo.setNombre(cargoRequestDTO.getNombre());
        cargo.setDescripcion(cargoRequestDTO.getDescripcion());

        this.cargoValidator.validator(cargo);
        this.assignamentTypeCargo(typeCargo, idType, cargo);

        return cargoRepository.save(cargo);
    }

    private void assignamentTypeCargo(String typeCargo, Integer idType, Cargo cargo) throws NegocioException {
        TypeCargo typeCargoEnum = TypeCargo.valueOf(typeCargo);
        switch (typeCargoEnum){
            case GR:
                cargo.setGrupo(InspeccionService.getObjectById(grupoRepository, idType));
                break;
            case RA:
                cargo.setRama(InspeccionService.getObjectById(ramaRepository, idType));
                break;
            case SE:
                cargo.setSeccion(InspeccionService.getObjectById(seccionRepository, idType));
                break;
            default:
                break;
        }
    }

    @Transactional
    public Cargo updateCargo(Integer idCargo, CargoRequestDTO cargoRequestDTO) throws NegocioException {
        Cargo cargoEdit = InspeccionService.getObjectById(cargoRepository, idCargo);

        cargoEdit.setNombre(cargoRequestDTO.getNombre());
        cargoEdit.setDescripcion(cargoRequestDTO.getDescripcion());
        this.cargoValidator.validator(cargoEdit);

        return cargoRepository.save(cargoEdit);
    }

    @Transactional
    public void deleteCargo(Integer idCargo) throws NegocioException {
        cargoRepository.delete(Objects.requireNonNull(InspeccionService.getObjectById(cargoRepository, idCargo)));
    }

}
