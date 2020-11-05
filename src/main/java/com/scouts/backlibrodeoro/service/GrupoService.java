package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.GrupoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.GrupoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final RamaRepository ramaRepository;
    private final CargoRepository cargoRepository;
    private final GrupoValidator grupoValidator;

    @Autowired
    public GrupoService(GrupoRepository grupoRepository, RamaRepository ramaRepository, CargoRepository cargoRepository,
                        GrupoValidator grupoValidator){
        this.grupoRepository= grupoRepository;
        this.ramaRepository= ramaRepository;
        this.cargoRepository = cargoRepository;
        this.grupoValidator= grupoValidator;
    }

    @Transactional(readOnly = true)
    public List<Grupo> getAllGrupos(){
        return grupoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Grupo getGrupo(Integer id) throws NegocioException {
        return InspeccionService.getObjectById(grupoRepository, id);
    }

    @Transactional
    public Grupo createGrupo(GrupoRequestDTO grupoRequestDTO) throws NegocioException {
        Grupo grupo = new Grupo();
        grupo.setNombre(grupoRequestDTO.getNombre());
        grupo.setDescripcion(grupoRequestDTO.getDescripcion());

        this.grupoValidator.validator(grupo);
        return grupoRepository.save(grupo);
    }

    @Transactional
    public Grupo updateGrupo(Integer idGrupo, GrupoRequestDTO grupoRequestDTO) throws NegocioException {
        Grupo grupoEdit = InspeccionService.getObjectById(grupoRepository, idGrupo);

        grupoEdit.setNombre(grupoRequestDTO.getNombre());
        grupoEdit.setDescripcion(grupoRequestDTO.getDescripcion());

        this.grupoValidator.validator(grupoEdit);
        return grupoRepository.save(grupoEdit);
    }

    @Transactional
    public void deleteGrupo(Integer idGrupo) throws NegocioException {
        Grupo grupo = InspeccionService.getObjectById(grupoRepository, idGrupo);

        if(ramaRepository.countRamaByGrupo(idGrupo)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_GRUPO_RAMAS_ACTIVAS, TypeException.VALIDATION);
        }

        if(cargoRepository.countCargoByTypeGrupo(idGrupo)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_GRUPO_CARGOS_ACTIVOS, TypeException.VALIDATION);
        }

        grupoRepository.delete(grupo);
    }

}
