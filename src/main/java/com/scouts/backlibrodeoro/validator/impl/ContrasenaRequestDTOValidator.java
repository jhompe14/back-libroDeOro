package com.scouts.backlibrodeoro.validator.impl;

import com.scouts.backlibrodeoro.validator.IValidator;
import com.scouts.backlibrodeoro.dto.request.ContrasenaRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.types.TypeChangeContrasena;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContrasenaRequestDTOValidator implements IValidator {
    @Override
    public <T> void validator(T contrasenaRequestDTO) throws NegocioException {
        ContrasenaRequestDTO contrasenaRequestDTOValidate = (ContrasenaRequestDTO) contrasenaRequestDTO;
        if(contrasenaRequestDTOValidate.getTypeChangeContrasena().equals(TypeChangeContrasena.MO.toString())
                && !validateModificationContrasena(contrasenaRequestDTOValidate)){
            throw new NegocioException(MessagesValidation.VALIDATION_TYPE_CHANGE_CONTRASENA_MODIFICATION_REQUIRED,
                    TypeException.VALIDATION);
        }else if(contrasenaRequestDTOValidate.getTypeChangeContrasena().equals(TypeChangeContrasena.RC.toString())
                    && !validateRecoveredContrasena(contrasenaRequestDTOValidate)){
            throw new NegocioException(MessagesValidation.VALIDATION_TYPE_CHANGE_CONTRASENA_RECOVERED_REQUIRED,
                    TypeException.VALIDATION);
        }
    }

    private boolean validateModificationContrasena(ContrasenaRequestDTO contrasenaRequestDTO){
        return Optional.ofNullable(contrasenaRequestDTO).map(contrasenaRequest ->
                GeneralValidates.validateStringNotIsEmpty(contrasenaRequest.getActualContrasena())
                && GeneralValidates.validateStringNotIsEmpty(contrasenaRequest.getNewContrasena())
                && GeneralValidates.validateStringNotIsEmpty(contrasenaRequest.getConfirmContrasena()))
                .orElse(false);
    }

    private boolean validateRecoveredContrasena(ContrasenaRequestDTO contrasenaRequestDTO){
        return Optional.ofNullable(contrasenaRequestDTO).map(contrasenaRequest ->
                GeneralValidates.validateStringNotIsEmpty(contrasenaRequest.getNewContrasena())
                 && GeneralValidates.validateStringNotIsEmpty(contrasenaRequest.getConfirmContrasena()))
                .orElse(false);
    }
}
