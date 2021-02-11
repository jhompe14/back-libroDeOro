package com.scouts.backlibrodeoro.service.impl;

import com.scouts.backlibrodeoro.repository.*;
import com.scouts.backlibrodeoro.service.SeccionService;
import com.scouts.backlibrodeoro.util.QueryUtil;
import com.scouts.backlibrodeoro.dto.request.SeccionRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.SeccionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SeccionServiceImpl implements SeccionService {

    private final SeccionRepository seccionRepository;
    private final RamaRepository ramaRepository;
    private final CargoRepository cargoRepository;
    private final TrayectoriaRepository trayectoriaRepository;
    private final AnecdotaRepository anecdotaRepository;
    private final SeccionValidator seccionValidator;

    @Autowired
    public SeccionServiceImpl(SeccionRepository seccionRepository, RamaRepository ramaRepository,
                              CargoRepository cargoRepository, TrayectoriaRepository trayectoriaRepository,
                              AnecdotaRepository anecdotaRepository, SeccionValidator seccionValidator) {
        this.seccionRepository = seccionRepository;
        this.ramaRepository = ramaRepository;
        this.cargoRepository = cargoRepository;
        this.trayectoriaRepository = trayectoriaRepository;
        this.anecdotaRepository = anecdotaRepository;
        this.seccionValidator = seccionValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seccion> getAllSecciones(){
        return seccionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Seccion getSeccion(Integer id) throws NegocioException {
        return QueryUtil.getObjectById(seccionRepository, id);
    }

    @Override
    @Transactional
    public Seccion createSeccion(SeccionRequestDTO seccionRequestDTO, Integer idRama) throws NegocioException {
        Seccion seccion = new Seccion();
        seccion.setNombre(seccionRequestDTO.getNombre());
        seccion.setDescripcion(seccionRequestDTO.getDescripcion());
        this.seccionValidator.validator(seccion);

        seccion.setRama(QueryUtil.getObjectById(ramaRepository, idRama));

        if(!Optional.ofNullable(seccion.getRama()).isPresent()){
            throw new NegocioException(MessagesValidation.ERROR_RAMA_NO_EXISTE, TypeException.VALIDATION);
        }

        return seccionRepository.save(seccion);
    }

    @Override
    @Transactional
    public Seccion updateSeccion(Integer idSeccion, SeccionRequestDTO seccionRequestDTO) throws NegocioException {
        Seccion seccionEdit = QueryUtil.getObjectById(seccionRepository, idSeccion);

        seccionEdit.setNombre(seccionRequestDTO.getNombre());
        seccionEdit.setDescripcion(seccionRequestDTO.getDescripcion());
        this.seccionValidator.validator(seccionEdit);

        return seccionRepository.save(seccionEdit);
    }

    @Override
    @Transactional
    public void deleteSeccion(Integer idSeccion) throws NegocioException {
        Seccion seccion = QueryUtil.getObjectById(seccionRepository, idSeccion);

        if(cargoRepository.countCargoByTypeSeccion(idSeccion)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_SECCION_CARGOS_ACTIVOS, TypeException.VALIDATION);
        }

        if(trayectoriaRepository.countTrayectoriaBySeccion(idSeccion)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_SECCION_TRAYECTORIAS_ACTIVAS, TypeException.VALIDATION);
        }

        if(anecdotaRepository.countAnecdotaBySeccion(idSeccion)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_SECCION_ANECDOTAS_ACTIVAS, TypeException.VALIDATION);
        }

        seccionRepository.delete(seccion);
    }
}
