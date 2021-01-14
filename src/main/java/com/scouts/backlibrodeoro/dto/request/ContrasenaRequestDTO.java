package com.scouts.backlibrodeoro.dto.request;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.types.TypeChangeContrasena;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;

import java.util.Optional;

public class ContrasenaRequestDTO {

    private String typeChangeContrasena;
    private String actualContrasena;
    private String newContrasena;
    private String confirmContrasena;

    public ContrasenaRequestDTO(String typeChangeContrasena, String actualContrasena, String newContrasena, String confirmContrasena) {
        this.typeChangeContrasena = validateTypeChangeContrasena(typeChangeContrasena);
        this.actualContrasena = actualContrasena;
        this.newContrasena = newContrasena;
        this.confirmContrasena = confirmContrasena;
    }

    public String getTypeChangeContrasena() {
        return typeChangeContrasena;
    }

    public String getActualContrasena() {
        return actualContrasena;
    }

    public String getNewContrasena() {
        return newContrasena;
    }

    public String getConfirmContrasena() {
        return confirmContrasena;
    }

    private String validateTypeChangeContrasena(String typeChangeContrasena){
        return Optional.ofNullable(typeChangeContrasena).map(e ->{
            try {
                TypeChangeContrasena.valueOf(e);
            }catch (IllegalArgumentException ex){
                try {
                    throw new NegocioException(MessagesValidation.VALIDATION_TYPE_CHANGE_CONTRASENA,
                            TypeException.VALIDATION);
                } catch (NegocioException negocioException) {
                    throw new RuntimeException(negocioException);
                }
            }
            return e;
        }).orElse(null);
    }
}
