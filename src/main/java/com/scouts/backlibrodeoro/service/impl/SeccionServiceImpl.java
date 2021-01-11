package com.scouts.backlibrodeoro.service.impl;

import com.scouts.backlibrodeoro.service.SeccionService;
import com.scouts.backlibrodeoro.util.QueryUtil;
import com.scouts.backlibrodeoro.dto.request.SeccionRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.SeccionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeccionServiceImpl implements SeccionService {

    private final SeccionRepository seccionRepository;
    private final RamaRepository ramaRepository;
    private final CargoRepository cargoRepository;
    private final SeccionValidator seccionValidator;

    @Autowired
    public SeccionServiceImpl(SeccionRepository seccionRepository, RamaRepository ramaRepository,
                              CargoRepository cargoRepository, SeccionValidator seccionValidator) {
        this.seccionRepository = seccionRepository;
        this.ramaRepository = ramaRepository;
        this.cargoRepository = cargoRepository;
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

        seccionRepository.delete(seccion);
    }
}
