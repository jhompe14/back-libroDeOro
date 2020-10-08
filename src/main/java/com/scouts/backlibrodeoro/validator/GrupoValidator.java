package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GrupoValidator implements IValidator{

    @Override
    public <T> void validator(T grupo) throws NegocioException {
        if(!validateNombre((Grupo) grupo)){
            throw new NegocioException(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, TypeException.VALIDATION);
        }
    }

    private boolean validateNombre(Grupo grupoValidate){
        return Optional.ofNullable(grupoValidate).map(g ->
                        Optional.ofNullable(g.getNombre()).isPresent() &&
                            !g.getNombre().isEmpty()).orElse(false);

    }
}
