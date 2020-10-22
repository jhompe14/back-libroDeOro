package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CargoValidator implements IValidator{
    @Override
    public <T> void validator(T cargo) throws NegocioException {
        if(!validateNombre((Cargo) cargo)){
            throw new NegocioException(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, TypeException.VALIDATION);
        }
    }

    private boolean validateNombre(Cargo cargoValidate){
        return Optional.ofNullable(cargoValidate).map(c ->
                GeneralValidates.validateStringNotIsEmpty(c.getNombre())).orElse(false);

    }
}
