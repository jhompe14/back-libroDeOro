package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.SeccionDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Rama;
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
import java.util.Optional;

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
        return seccionRepository.findById(id).orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_SECCION_NO_EXISTE,
                TypeException.NOTFOUND));
    }

    @Transactional
    public Seccion createSeccion(SeccionDTO seccionDTO, Integer idRama) throws NegocioException {
        Seccion seccion = new Seccion();
        seccion.setNombre(seccionDTO.getNombre());
        seccion.setDescripcion(seccionDTO.getDescripcion());

        this.seccionValidator.validator(seccion);
        Optional<Rama> rama = InspeccionService.getRama(ramaRepository, idRama);

        seccion.setRama(rama.orElse(new Rama()));
        return seccionRepository.save(seccion);
    }

    @Transactional
    public Seccion updateSeccion(Integer idSeccion, SeccionDTO seccionDTO) throws NegocioException {
        Optional<Seccion> seccion = InspeccionService.getSeccion(seccionRepository, idSeccion);

        Seccion seccionEdit = new Seccion();
        seccionEdit.setId(idSeccion);
        seccionEdit.setNombre(seccionDTO.getNombre());
        seccionEdit.setDescripcion(seccionDTO.getDescripcion());
        seccionEdit.setRama(seccion.orElse(new Seccion()).getRama());

        this.seccionValidator.validator(seccionEdit);

        return seccionRepository.save(seccionEdit);
    }

    @Transactional
    public void deleteSeccion(Integer idSeccion) throws NegocioException {
        Optional<Seccion> seccion = InspeccionService.getSeccion(seccionRepository, idSeccion);

        if(cargoRepository.countCargoByTypeSeccion(idSeccion)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_SECCION_CARGOS_ACTIVOS, TypeException.VALIDATION);
        }

        seccionRepository.delete(seccion.orElse(new Seccion()));
    }
}
