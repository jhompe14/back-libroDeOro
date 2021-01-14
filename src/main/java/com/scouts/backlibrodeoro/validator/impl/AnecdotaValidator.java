package com.scouts.backlibrodeoro.validator.impl;

import com.scouts.backlibrodeoro.validator.IValidator;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class AnecdotaValidator implements IValidator {

    @Override
    public <T> void validator(T anecdota) throws NegocioException {
        Anecdota anecdotaValidate = (Anecdota) anecdota;
        if(!validateDescripcion(anecdotaValidate))
            throw new NegocioException(MessagesValidation.VALIDATION_ANECDOTA_DESCRIPCION,
                    TypeException.VALIDATION);
        if(!validateFecha(anecdotaValidate))
            throw new NegocioException(MessagesValidation.VALIDATION_ANECDOTA_FECHA,
                    TypeException.VALIDATION);
        if(!validateRama(anecdotaValidate))
            throw new NegocioException(MessagesValidation.VALIDATION_RAMA_ANECDOTA,
                    TypeException.VALIDATION);
    }

    private boolean validateDescripcion(Anecdota anecdotaValidate){
        return Optional.ofNullable(anecdotaValidate).map(a ->
                GeneralValidates.validateStringNotIsEmpty(a.getDescripcion())).orElse(false);

    }

    private boolean validateRama(Anecdota anecdotaValidate){
        return Optional.ofNullable(anecdotaValidate).map(a -> Optional.ofNullable(a.getRama()).isPresent())
                .orElse(false);
    }

    private boolean validateFecha(Anecdota anecdotaValidate) {
        return Optional.ofNullable(anecdotaValidate).map(a ->
                Optional.ofNullable(a.getFecha())
                        .map(fecha-> fecha.before(new Date()) ||
                                        fecha.equals(new Date())).orElse(true))
                .orElse(false);
    }
}
