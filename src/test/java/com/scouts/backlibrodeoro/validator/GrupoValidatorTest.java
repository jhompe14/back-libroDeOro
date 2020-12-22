package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.validator.impl.GrupoValidator;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class GrupoValidatorTest {

    private GrupoValidator grupoValidator;

    @BeforeEach
    public void init(){
        grupoValidator = new GrupoValidator();
    }

    @Test
    public void grupoValidateWhenNameIsNullThrowNegocioException(){
        //Arrange
        Grupo grupo = new Grupo();
        grupo.setId(1);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                grupoValidator.validator(grupo));

        //Assert
        assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, exception.getMessage());
    }

    @Test
    public void grupoValidateWhenNameIsEmptyThrowNegocioException(){
        //Arrange
        Grupo grupo = new Grupo();
        grupo.setNombre("");
        grupo.setId(1);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                grupoValidator.validator(grupo));

        //Assert
        assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, exception.getMessage());
    }

}
