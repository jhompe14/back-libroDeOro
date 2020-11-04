package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.dto.request.AuthRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthValidator implements IValidator{
    @Override
    public <T> void validator(T authDTO) throws NegocioException {
        AuthRequestDTO auth = (AuthRequestDTO) authDTO;
        if(!validateUsuario(auth) || !validateContrasena(auth)){
            throw new NegocioException(MessagesValidation.DILIGENCIAR_USUARIO_CONTRASENA, TypeException.VALIDATION);
        }
    }

    private boolean validateUsuario(AuthRequestDTO auth){
        return Optional.ofNullable(auth).map(c ->
                GeneralValidates.validateStringNotIsEmpty(c.getUsuario())).orElse(false);

    }

    private boolean validateContrasena(AuthRequestDTO auth){
        return Optional.ofNullable(auth).map(c ->
                GeneralValidates.validateStringNotIsEmpty(c.getContrasena())).orElse(false);
    }
}
