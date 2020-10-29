package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.SeccionDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.SeccionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeccionService {

    private final SeccionRepository seccionRepository;
    private final RamaRepository ramaRepository;
    private final CargoRepository cargoRepository;
    private final SeccionValidator seccionValidator;

    @Autowired
    public SeccionService(SeccionRepository seccionRepository, RamaRepository ramaRepository,
                          CargoRepository cargoRepository, SeccionValidator seccionValidator) {
        this.seccionRepository = seccionRepository;
        this.ramaRepository = ramaRepository;
        this.cargoRepository = cargoRepository;
        this.seccionValidator = seccionValidator;
    }

    @Transactional(readOnly = true)
    public List<Seccion> getAllSecciones(){
        return seccionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Seccion getSeccion(Integer id) throws NegocioException {
        return InspeccionService.getObjectById(seccionRepository, id);
    }

    @Transactional
    public Seccion createSeccion(SeccionDTO seccionDTO, Integer idRama) throws NegocioException {
        Seccion seccion = new Seccion();
        seccion.setNombre(seccionDTO.getNombre());
        seccion.setDescripcion(seccionDTO.getDescripcion());
        this.seccionValidator.validator(seccion);

        seccion.setRama(InspeccionService.getObjectById(ramaRepository, idRama));
        return seccionRepository.save(seccion);
    }

    @Transactional
    public Seccion updateSeccion(Integer idSeccion, SeccionDTO seccionDTO) throws NegocioException {
        Seccion seccionEdit = InspeccionService.getObjectById(seccionRepository, idSeccion);

        seccionEdit.setNombre(seccionDTO.getNombre());
        seccionEdit.setDescripcion(seccionDTO.getDescripcion());
        this.seccionValidator.validator(seccionEdit);

        return seccionRepository.save(seccionEdit);
    }

    @Transactional
    public void deleteSeccion(Integer idSeccion) throws NegocioException {
        Seccion seccion = InspeccionService.getObjectById(seccionRepository, idSeccion);

        if(cargoRepository.countCargoByTypeSeccion(idSeccion)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_SECCION_CARGOS_ACTIVOS, TypeException.VALIDATION);
        }

        seccionRepository.delete(seccion);
    }
}
