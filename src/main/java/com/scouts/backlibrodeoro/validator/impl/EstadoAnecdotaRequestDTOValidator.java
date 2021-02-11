package com.scouts.backlibrodeoro.validator.impl;

import com.scouts.backlibrodeoro.types.TypeVisualizacion;
import com.scouts.backlibrodeoro.validator.IValidator;
import com.scouts.backlibrodeoro.dto.request.EstadoAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.types.TypeEstadoAnecdota;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EstadoAnecdotaRequestDTOValidator implements IValidator {

    @Override
    public <T> void validator(T estadoAnecdotaRequestDTO) throws NegocioException {
        EstadoAnecdotaRequestDTO estadoAnecdotaRequestDTOValidate = (EstadoAnecdotaRequestDTO) estadoAnecdotaRequestDTO;
        if(!validateRequeried(estadoAnecdotaRequestDTOValidate))
            throw new NegocioException(MessagesValidation.VALIDATION_ESTADO_VISUALIZACION_ANECDOTA,
                    TypeException.VALIDATION);
        if(GeneralValidates.validateStringNotIsEmpty(estadoAnecdotaRequestDTOValidate.getEstado())
                && !validateUserModify(estadoAnecdotaRequestDTOValidate)){
            throw new NegocioException(MessagesValidation.VALIDATION_USUARIO_MODIFICACION_ANECDOTA,
                    TypeException.VALIDATION);
        }

    }

    private boolean validateRequeried(EstadoAnecdotaRequestDTO estadoAnecdotaRequestDTO){
        return Optional.ofNullable(estadoAnecdotaRequestDTO).map(ear ->
                GeneralValidates.validateStringNotIsEmpty(ear.getVisualizacion()) &&
                        (ear.getVisualizacion().equals(TypeVisualizacion.PL.toString()))
                            || ear.getVisualizacion().equals(TypeVisualizacion.PR.toString())).orElse(false);
    }

    private boolean validateUserModify(EstadoAnecdotaRequestDTO estadoAnecdotaRequestDTO) {
        return Optional.ofNullable(estadoAnecdotaRequestDTO).map(ear ->
                !ear.getEstado().equals(TypeEstadoAnecdota.PM.toString())
                    || (ear.getEstado().equals(TypeEstadoAnecdota.PM.toString())
                            && GeneralValidates.validateStringNotIsEmpty(ear.getUsuarioModificacion()))
                ).orElse(false);
    }
}
