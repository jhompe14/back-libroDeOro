package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeccionValidator implements IValidator{
    @Override
    public <T> void validator(T seccion) throws NegocioException {
        if(!validateNombre((Seccion) seccion)){
            throw new NegocioException(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, TypeException.VALIDATION);
        }
    }

    private boolean validateNombre(Seccion seccionValidate){
        return Optional.ofNullable(seccionValidate).map(s ->
                Optional.ofNullable(s.getNombre()).isPresent() &&
                        !s.getNombre().isEmpty()).orElse(false);

    }

}
