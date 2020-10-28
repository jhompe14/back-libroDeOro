package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.RamaDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.RamaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RamaService {

    private final RamaRepository ramaRepository;
    private final SeccionRepository seccionRepository;
    private final GrupoRepository grupoRepository;
    private final CargoRepository cargoRepository;
    private final RamaValidator ramaValidator;

    @Autowired
    public RamaService(RamaRepository ramaRepository, GrupoRepository grupoRepository,
                       SeccionRepository seccionRepository, CargoRepository cargoRepository,
                       RamaValidator ramaValidator) {
        this.ramaRepository = ramaRepository;
        this.grupoRepository = grupoRepository;
        this.seccionRepository = seccionRepository;
        this.cargoRepository = cargoRepository;
        this.ramaValidator = ramaValidator;
    }

    @Transactional(readOnly = true)
    public List<Rama> getAllRamas(){
        return ramaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Rama getRama(Integer id) throws NegocioException{
        return InspeccionService.getObjectById(ramaRepository, id);
    }

    @Transactional
    public Rama createRama(RamaDTO ramaDTO, Integer idGrupo) throws NegocioException {
        Rama rama = new Rama();
        rama.setNombre(ramaDTO.getNombre());
        rama.setDescripcion(ramaDTO.getDescripcion());
        rama.setEdadMinima(ramaDTO.getEdadMinima());
        rama.setEdadMaxima(ramaDTO.getEdadMaxima());

        this.ramaValidator.validator(rama);

        rama.setGrupo(InspeccionService.getObjectById(grupoRepository, idGrupo));
        return ramaRepository.save(rama);
    }

    @Transactional
    public Rama updateRama(Integer idRama, RamaDTO ramaDTO) throws NegocioException {
        Rama ramaEdit = InspeccionService.getObjectById(ramaRepository, idRama);

        ramaEdit.setNombre(ramaDTO.getNombre());
        ramaEdit.setEdadMinima(ramaDTO.getEdadMinima());
        ramaEdit.setEdadMaxima(ramaDTO.getEdadMaxima());
        ramaEdit.setDescripcion(ramaDTO.getDescripcion());
        this.ramaValidator.validator(ramaEdit);

        return ramaRepository.save(ramaEdit);
    }

    @Transactional
    public void deleteRama(Integer idRama) throws NegocioException {
        Rama rama = InspeccionService.getObjectById(ramaRepository, idRama);

        if(seccionRepository.countSeccionByRama(idRama)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_RAMA_SECCIONES_ACTIVAS, TypeException.VALIDATION);
        }

        if(cargoRepository.countCargoByTypeRama(idRama) > 0){
            throw new NegocioException(MessagesValidation.VALIDATION_RAMA_CARGOS_ACTIVOS, TypeException.VALIDATION);
        }

        ramaRepository.delete(rama);
    }
}
