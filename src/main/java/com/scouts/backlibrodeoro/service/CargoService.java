package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.CargoDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.types.TypeCargo;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.CargoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return cargoRepository.findById(id).orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_CARGO_NO_EXISTE,
                TypeException.NOTFOUND));
    }

    @Transactional
    public Cargo createCargo(String typeCargo, Integer idType, CargoDTO cargoDTO) throws NegocioException {
        Cargo cargo = new Cargo();
        cargo.setNombre(cargoDTO.getNombre());
        cargo.setDescripcion(cargoDTO.getDescripcion());

        this.cargoValidator.validator(cargo);
        this.assignamentTypeCargo(typeCargo, idType, cargo);

        return cargoRepository.save(cargo);
    }

    private void assignamentTypeCargo(String typeCargo, Integer idType, Cargo cargo) throws NegocioException {
        TypeCargo typeCargoEnum = TypeCargo.valueOf(typeCargo);
        switch (typeCargoEnum){
            case GR:
                cargo.setGrupo(InspeccionService.getGrupo(grupoRepository, idType).orElse(new Grupo()));
                break;
            case RA:
                cargo.setRama(InspeccionService.getRama(ramaRepository, idType).orElse(new Rama()));
                break;
            case SE:
                cargo.setSeccion(InspeccionService.getSeccion(seccionRepository, idType).orElse(new Seccion()));
                break;
            default:
                break;
        }
    }

    @Transactional
    public Cargo updateCargo(Integer idCargo, CargoDTO cargoDTO) throws NegocioException {
        Optional<Cargo> cargoOptional = InspeccionService.getCargo(cargoRepository, idCargo);

        Cargo cargo = cargoOptional.orElse(new Cargo());

        Cargo cargoEdit = new Cargo();
        cargoEdit.setId(idCargo);
        cargoEdit.setNombre(cargoDTO.getNombre());
        cargoEdit.setDescripcion(cargoDTO.getDescripcion());
        cargoEdit.setGrupo(cargo.getGrupo());
        cargoEdit.setRama(cargo.getRama());
        cargoEdit.setSeccion(cargo.getSeccion());

        this.cargoValidator.validator(cargoEdit);

        return cargoRepository.save(cargoEdit);
    }

    @Transactional
    public void deleteCargo(Integer idCargo) throws NegocioException {
        Optional<Cargo> cargo = InspeccionService.getCargo(cargoRepository, idCargo);
        cargoRepository.delete(cargo.orElse(new Cargo()));
    }

}
