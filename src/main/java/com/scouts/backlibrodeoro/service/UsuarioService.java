package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.AuthDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.repository.UsuarioRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthValidator authValidator;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, AuthValidator authValidator) {
        this.usuarioRepository = usuarioRepository;
        this.authValidator = authValidator;
    }

    @Transactional(readOnly = true)
    public boolean authIsSuccess(AuthDTO auth) throws NegocioException {
        authValidator.validator(auth);
        Optional<Usuario> usuarioOptional = Optional.ofNullable(
                usuarioRepository.findUsuarioByUsuarioAndContrasena(auth.getUsuario(), auth.getContrasena()));
        if(!usuarioOptional.isPresent()) {
            throw new NegocioException(MessagesValidation.NOT_FOUND_USUARIO_CONTRASENA, TypeException.NOTFOUND);
        }else{
            auth.setTipoUsuario(usuarioOptional.orElse(new Usuario()).getTipoUsuario());
        }
        return true;
    }


}
