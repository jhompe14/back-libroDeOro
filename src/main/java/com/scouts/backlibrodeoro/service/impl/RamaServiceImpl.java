package com.scouts.backlibrodeoro.service.impl;

import com.scouts.backlibrodeoro.service.RamaService;
import com.scouts.backlibrodeoro.util.QueryUtil;
import com.scouts.backlibrodeoro.dto.request.RamaRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.RamaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RamaServiceImpl implements RamaService {

    private final RamaRepository ramaRepository;
    private final SeccionRepository seccionRepository;
    private final GrupoRepository grupoRepository;
    private final CargoRepository cargoRepository;
    private final RamaValidator ramaValidator;

    @Autowired
    public RamaServiceImpl(RamaRepository ramaRepository, GrupoRepository grupoRepository,
                           SeccionRepository seccionRepository, CargoRepository cargoRepository,
                           RamaValidator ramaValidator) {
        this.ramaRepository = ramaRepository;
        this.grupoRepository = grupoRepository;
        this.seccionRepository = seccionRepository;
        this.cargoRepository = cargoRepository;
        this.ramaValidator = ramaValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rama> getAllRamas(){
        return ramaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Rama getRama(Integer id) throws NegocioException{
        return QueryUtil.getObjectById(ramaRepository, id);
    }

    @Override
    @Transactional
    public Rama createRama(RamaRequestDTO ramaRequestDTO, Integer idGrupo) throws NegocioException {
        Rama rama = new Rama();
        rama.setNombre(ramaRequestDTO.getNombre());
        rama.setDescripcion(ramaRequestDTO.getDescripcion());
        rama.setEdadMinima(ramaRequestDTO.getEdadMinima());
        rama.setEdadMaxima(ramaRequestDTO.getEdadMaxima());

        this.ramaValidator.validator(rama);

        rama.setGrupo(QueryUtil.getObjectById(grupoRepository, idGrupo));
        return ramaRepository.save(rama);
    }

    @Override
    @Transactional
    public Rama updateRama(Integer idRama, RamaRequestDTO ramaRequestDTO) throws NegocioException {
        Rama ramaEdit = QueryUtil.getObjectById(ramaRepository, idRama);

        ramaEdit.setNombre(ramaRequestDTO.getNombre());
        ramaEdit.setEdadMinima(ramaRequestDTO.getEdadMinima());
        ramaEdit.setEdadMaxima(ramaRequestDTO.getEdadMaxima());
        ramaEdit.setDescripcion(ramaRequestDTO.getDescripcion());
        this.ramaValidator.validator(ramaEdit);

        return ramaRepository.save(ramaEdit);
    }

    @Override
    @Transactional
    public void deleteRama(Integer idRama) throws NegocioException {
        Rama rama = QueryUtil.getObjectById(ramaRepository, idRama);

        if(seccionRepository.countSeccionByRama(idRama)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_RAMA_SECCIONES_ACTIVAS, TypeException.VALIDATION);
        }

        if(cargoRepository.countCargoByTypeRama(idRama) > 0){
            throw new NegocioException(MessagesValidation.VALIDATION_RAMA_CARGOS_ACTIVOS, TypeException.VALIDATION);
        }

        ramaRepository.delete(rama);
    }
}
