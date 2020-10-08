package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RamaValidator implements IValidator{
    @Override
    public <T> void validator(T rama) throws NegocioException {
        Rama ramaValidation = (Rama) rama;
        if(!validateRequired(ramaValidation)){
            throw new NegocioException(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS, TypeException.VALIDATION);
        }
        if(!validateEdadMaximaMinima(ramaValidation)){
            throw new NegocioException(MessagesValidation.VALIDATION_EDAD_MINIMA_MAXIMA, TypeException.VALIDATION);
        }
        if(!validateEdadMaximaMajorToEdadMinima(ramaValidation)){
            throw new NegocioException(MessagesValidation.VALIDATION_EDAD_MAXIMA_MAJOR_EDAD_MINIMA, TypeException.VALIDATION);
        }
    }

    private boolean validateRequired(Rama ramaValidate){
        return Optional.ofNullable(ramaValidate).map(r ->{
                    return Optional.ofNullable(r.getNombre()).isPresent() &&
                            !r.getNombre().isEmpty() &&
                            Optional.ofNullable(r.getEdadMinima()).isPresent() &&
                            Optional.ofNullable(r.getEdadMaxima()).isPresent();
                }).orElse(false);
    }

    private boolean validateEdadMaximaMinima(Rama ramaValidate){
        return ramaValidate.getEdadMinima() > 0 && ramaValidate.getEdadMinima() < 200 &&
                ramaValidate.getEdadMaxima() > 0 && ramaValidate.getEdadMaxima() < 200;
    }

    private boolean validateEdadMaximaMajorToEdadMinima(Rama ramaValidate){
        return ramaValidate.getEdadMaxima() >= ramaValidate.getEdadMinima();
    }
}
