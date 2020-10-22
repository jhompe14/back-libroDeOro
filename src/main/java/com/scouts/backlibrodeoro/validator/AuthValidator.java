package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.dto.AuthDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthValidator implements IValidator{
    @Override
    public <T> void validator(T authDTO) throws NegocioException {
        AuthDTO auth = (AuthDTO) authDTO;
        if(!validateUsuario(auth) || !validateContrasena(auth)){
            throw new NegocioException(MessagesValidation.DILIGENCIAR_USUARIO_CONTRASENA, TypeException.VALIDATION);
        }
    }

    private boolean validateUsuario(AuthDTO auth){
        return Optional.ofNullable(auth).map(c ->
                GeneralValidates.validateStringNotIsEmpty(c.getUsuario())).orElse(false);

    }

    private boolean validateContrasena(AuthDTO auth){
        return Optional.ofNullable(auth).map(c ->
                GeneralValidates.validateStringNotIsEmpty(c.getContrasena())).orElse(false);
    }
}
