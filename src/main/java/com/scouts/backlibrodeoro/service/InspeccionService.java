package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;

import java.util.Optional;

public class InspeccionService {

    public static Optional<Grupo> getGrupo(GrupoRepository grupoRepository, Integer idGrupo) throws NegocioException {
        Optional<Grupo> grupo = grupoRepository.findById(idGrupo);
        if(!grupo.isPresent()){
            throw new NegocioException(MessagesValidation.ERROR_GRUPO_NO_EXISTE, TypeException.NOTFOUND);
        }

        return grupo;
    }

    public static Optional<Rama> getRama(RamaRepository ramaRepository, Integer idRama) throws NegocioException{
        Optional<Rama> rama = ramaRepository.findById(idRama);
        if(!rama.isPresent()){
            throw new NegocioException(MessagesValidation.ERROR_RAMA_NO_EXISTE, TypeException.NOTFOUND);
        }

        return rama;
    }

    public static Optional<Seccion> getSeccion(SeccionRepository seccionRepository, Integer idSeccion)
            throws NegocioException{
        Optional<Seccion> seccion= seccionRepository.findById(idSeccion);
        if(!seccion.isPresent()){
            throw new NegocioException(MessagesValidation.ERROR_SECCION_NO_EXISTE, TypeException.NOTFOUND);
        }

        return seccion;
    }

    public static Optional<Cargo> getCargo(CargoRepository cargoRepository, Integer idCargo) throws NegocioException{
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        if(!cargo.isPresent()){
            throw new NegocioException(MessagesValidation.ERROR_CARGO_NO_EXISTE, TypeException.NOTFOUND);
        }

        return cargo;
    }

}
