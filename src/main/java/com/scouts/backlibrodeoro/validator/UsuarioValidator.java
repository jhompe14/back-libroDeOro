package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.types.TypeIntegrante;
import com.scouts.backlibrodeoro.types.TypeUsuario;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioValidator implements IValidator{

    @Override
    public <T> void validator(T usuario) throws NegocioException {
        Optional<Usuario> usuarioOptionalValidation = Optional.ofNullable((Usuario) usuario);
        StringBuilder strValidation = new StringBuilder();
        validateRequired(strValidation, usuarioOptionalValidation);
        validateTelefonoValid(strValidation, usuarioOptionalValidation);
        validateCorreoValid(strValidation, usuarioOptionalValidation);
        validateTipoIntegrante(strValidation, usuarioOptionalValidation);
        validateTipoUsuario(strValidation, usuarioOptionalValidation);
        lanzarExceptionValidacion(strValidation);
    }

    private void validateRequired(StringBuilder strValidation, Optional<Usuario> usuarioOptionalValidation) throws NegocioException {
        usuarioOptionalValidation.map(usuario -> {
            if(!GeneralValidates.validateStringNotIsEmpty(usuario.getUsuario())){
                strValidation.append(MessagesValidation.VALIDATION_USUARIO_REQUIRED).append(" </br>");
            }
            if(!GeneralValidates.validateStringNotIsEmpty(usuario.getContrasena())){
                strValidation.append(MessagesValidation.VALIDATION_USUARIO_CONTRASENA).append(" </br>");
            }
            if(!GeneralValidates.validateStringNotIsEmpty(usuario.getNombres())){
                strValidation.append(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO).append(" </br>");
            }
            if(!GeneralValidates.validateStringNotIsEmpty(usuario.getApellidos())){
                strValidation.append(MessagesValidation.VALIDATION_APELLIDO_REQUIRED).append(" </br>");
            }
            if(!GeneralValidates.validateStringNotIsEmpty(usuario.getDireccion())){
                strValidation.append(MessagesValidation.VALIDATION_DIRECCION_REQUIRED).append(" </br>");
            }
            if(!GeneralValidates.validateStringNotIsEmpty(usuario.getCiudad())){
                strValidation.append(MessagesValidation.VALIDATION_CIUDAD_REQUIRED).append(" </br>");
            }
            return usuario;
        });
        lanzarExceptionValidacion(strValidation);
    }

    private void validateTelefonoValid(StringBuilder strValidation, Optional<Usuario> usuarioOptionalValidation){
        usuarioOptionalValidation.map(usuario -> {
            if(!GeneralValidates.validateStringNotIsEmpty(usuario.getTelefono()) ||
                    usuario.getTelefono().length() < 7 ||
                    usuario.getTelefono().length() > 12){
                strValidation.append(MessagesValidation.VALIDATION_TELEFONO_REQUIRED).append(" </br>");
            }
            return usuario;
        });
    }

    private void validateCorreoValid(StringBuilder strValidation, Optional<Usuario> usuarioOptionalValidation){
        usuarioOptionalValidation.map(usuario -> {
            if(!GeneralValidates.validateStringNotIsEmpty(usuario.getCorreo())
                    || !GeneralValidates.validateCorreoIsCorrect(usuario.getCorreo())){
                strValidation.append(MessagesValidation.VALIDATION_CORREO_REQUIRED).append(" </br>");
            }
            return usuario;
        });
    }

    private void validateTipoIntegrante(StringBuilder strValidation, Optional<Usuario> usuarioOptionalValidation){
        usuarioOptionalValidation.map(usuario -> {
            try {
                TypeIntegrante.valueOf(usuario.getTipoIntegrante());
            }catch (IllegalArgumentException ex){
                strValidation.append(MessagesValidation.VALIDATION_TIPO_INTEGRANTE_REQUIRED).append(" </br>");
            }
            return usuario;
        });
    }

    private void validateTipoUsuario(StringBuilder strValidation, Optional<Usuario> usuarioOptionalValidation){
        usuarioOptionalValidation.map(usuario -> {
            try {
                TypeUsuario.valueOf(usuario.getTipoUsuario());
            }catch (IllegalArgumentException ex){
                strValidation.append(MessagesValidation.VALIDATION_TIPO_USUARIO_REQUIRED).append(" </br>");
            }
            return usuario;
        });
    }

    private void lanzarExceptionValidacion(StringBuilder strValidation) throws NegocioException {
        if(!strValidation.toString().isEmpty()){
            throw new NegocioException(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS+"</br>"
                    +strValidation.toString(),
                    TypeException.VALIDATION);
        }
    }
}
