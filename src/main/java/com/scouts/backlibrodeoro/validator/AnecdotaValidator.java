package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AnecdotaValidator implements IValidator{

    @Override
    public <T> void validator(T anecdota) throws NegocioException {
        if(!validateDescripcion((Anecdota) anecdota)){
            throw new NegocioException(MessagesValidation.VALIDATION_ANECDOTA_DESCRIPCION, TypeException.VALIDATION);
        }
    }

    private boolean validateDescripcion(Anecdota anecdotaValidate){
        return Optional.ofNullable(anecdotaValidate).map(a ->
                GeneralValidates.validateStringNotIsEmpty(a.getDescripcion())).orElse(false);

    }
}
