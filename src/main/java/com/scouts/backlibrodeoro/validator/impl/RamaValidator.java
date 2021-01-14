package com.scouts.backlibrodeoro.validator.impl;

import com.scouts.backlibrodeoro.validator.IValidator;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RamaValidator implements IValidator {
    @Override
    public <T> void validator(T rama) throws NegocioException {
        Rama ramaValidation = (Rama) rama;
        StringBuilder strValidation = new StringBuilder();

        validateRequired(strValidation, ramaValidation);
        if(!strValidation.toString().isEmpty()){
            throw new NegocioException(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS+"</br>"+
                    strValidation.toString(),
                    TypeException.VALIDATION);
        }
        if(!validateEdadMaximaMinima(ramaValidation)){
            throw new NegocioException(MessagesValidation.VALIDATION_EDAD_MINIMA_MAXIMA, TypeException.VALIDATION);
        }
        if(!validateEdadMaximaMajorToEdadMinima(ramaValidation)){
            throw new NegocioException(MessagesValidation.VALIDATION_EDAD_MAXIMA_MAJOR_EDAD_MINIMA, TypeException.VALIDATION);
        }
    }

    private void validateRequired(StringBuilder strValidation, Rama ramaValidate){
        Optional.ofNullable(ramaValidate).map(r ->{
            if(!GeneralValidates.validateStringNotIsEmpty(r.getNombre())){
                strValidation.append(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO).append(" </br>");
            }
            if(!Optional.ofNullable(r.getEdadMinima()).isPresent()){
                strValidation.append(MessagesValidation.VALIDATION_RAMA_EDAD_MINIMA).append(" </br>");
            }
            if(!Optional.ofNullable(r.getEdadMaxima()).isPresent()){
                strValidation.append(MessagesValidation.VALIDATION_RAMA_EDAD_MAXIMA).append(" </br>");
            }
            return r;
        });
    }

    private boolean validateEdadMaximaMinima(Rama ramaValidate){
        return ramaValidate.getEdadMinima() > 0 && ramaValidate.getEdadMinima() < 200 &&
                ramaValidate.getEdadMaxima() > 0 && ramaValidate.getEdadMaxima() < 200;
    }

    private boolean validateEdadMaximaMajorToEdadMinima(Rama ramaValidate){
        return ramaValidate.getEdadMaxima() >= ramaValidate.getEdadMinima();
    }
}
