package com.scouts.backlibrodeoro.service.impl;

import com.scouts.backlibrodeoro.model.Trayectoria;
import com.scouts.backlibrodeoro.repository.TrayectoriaRepository;
import com.scouts.backlibrodeoro.service.GrupoService;
import com.scouts.backlibrodeoro.util.QueryUtil;
import com.scouts.backlibrodeoro.dto.request.GrupoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.GrupoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final RamaRepository ramaRepository;
    private final CargoRepository cargoRepository;
    private final TrayectoriaRepository trayectoriaRepository;
    private final GrupoValidator grupoValidator;

    @Autowired
    public GrupoServiceImpl(GrupoRepository grupoRepository, RamaRepository ramaRepository, CargoRepository cargoRepository,
                            TrayectoriaRepository trayectoriaRepository, GrupoValidator grupoValidator){
        this.grupoRepository= grupoRepository;
        this.ramaRepository= ramaRepository;
        this.cargoRepository = cargoRepository;
        this.trayectoriaRepository = trayectoriaRepository;
        this.grupoValidator= grupoValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grupo> getAllGrupos(){
        return grupoRepository.findAllGrupos();
    }

    @Override
    @Transactional(readOnly = true)
    public Grupo getGrupo(Integer id) throws NegocioException {
        return QueryUtil.getObjectById(grupoRepository, id);
    }

    @Override
    @Transactional
    public Grupo createGrupo(GrupoRequestDTO grupoRequestDTO) throws NegocioException {
        Grupo grupo = new Grupo();
        grupo.setNombre(grupoRequestDTO.getNombre());
        grupo.setDescripcion(grupoRequestDTO.getDescripcion());

        this.grupoValidator.validator(grupo);
        return grupoRepository.save(grupo);
    }

    @Override
    @Transactional
    public Grupo updateGrupo(Integer idGrupo, GrupoRequestDTO grupoRequestDTO) throws NegocioException {
        Grupo grupoEdit = QueryUtil.getObjectById(grupoRepository, idGrupo);

        grupoEdit.setNombre(grupoRequestDTO.getNombre());
        grupoEdit.setDescripcion(grupoRequestDTO.getDescripcion());

        this.grupoValidator.validator(grupoEdit);
        return grupoRepository.save(grupoEdit);
    }

    @Override
    @Transactional
    public void deleteGrupo(Integer idGrupo) throws NegocioException {
        Grupo grupo = QueryUtil.getObjectById(grupoRepository, idGrupo);

        if(ramaRepository.countRamaByGrupo(idGrupo)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_GRUPO_RAMAS_ACTIVAS, TypeException.VALIDATION);
        }

        if(cargoRepository.countCargoByTypeGrupo(idGrupo)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_GRUPO_CARGOS_ACTIVOS, TypeException.VALIDATION);
        }

        if(trayectoriaRepository.countTrayectoriaByGrupo(idGrupo)>0){
            throw new NegocioException(MessagesValidation.VALIDATION_GRUPO_TRAYECTORIAS_ACTIVAS, TypeException.VALIDATION);
        }

        grupoRepository.delete(grupo);
    }

}
