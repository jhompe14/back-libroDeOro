package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Trayectoria;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;

import java.util.Optional;

public class TrayectoriaValidator implements IValidator{
    @Override
    public <T> void validator(T trayectoria) throws NegocioException {
        Trayectoria trayectoriaValidation = (Trayectoria) trayectoria;
        if(!validateRequired(trayectoriaValidation)){
            throw new NegocioException(MessagesValidation.VALIDATION_TRAYECTORIA_FECHA_INGRESO, TypeException.VALIDATION);
        }
    }

    private Boolean validateRequired(Trayectoria trayectoriaValidation){
        return Optional.ofNullable(trayectoriaValidation).map(r ->
                    Optional.ofNullable(r.getAnioIngreso()).isPresent()
        ).orElse(false);
    }
}
