package com.scouts.backlibrodeoro.util;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.*;
import com.scouts.backlibrodeoro.repository.*;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;

public class QueryUtil {

    public static <T> T getObjectById(JpaRepository<T, Integer> jpaRepository, Integer id) throws NegocioException {
        if(Optional.ofNullable(id).orElse(0) == 0){
            return null;
        }

        Function<JpaRepository<T, Integer>, String> defineMessage = (jpaRepositoryInterface) -> {
            if(jpaRepositoryInterface instanceof GrupoRepository)
                return MessagesValidation.ERROR_GRUPO_NO_EXISTE;
            if(jpaRepositoryInterface instanceof RamaRepository)
                return MessagesValidation.ERROR_RAMA_NO_EXISTE;
            if(jpaRepositoryInterface instanceof SeccionRepository)
                return MessagesValidation.ERROR_SECCION_NO_EXISTE;
            if(jpaRepositoryInterface instanceof CargoRepository)
                return MessagesValidation.ERROR_CARGO_NO_EXISTE;
            return "";
        };

        return jpaRepository.findById(id)
                .orElseThrow(() -> new NegocioException(defineMessage.apply(jpaRepository),
                        TypeException.VALIDATION));
    }

    public static Usuario getUsuarioByUsuario(UsuarioRepository usuarioRepository, String usuario) throws NegocioException {
        return Optional.ofNullable(usuarioRepository.findUsuarioByUsuario(usuario))
                .orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_USUARIO_NO_EXISTE,
                        TypeException.VALIDATION));
    }

}
