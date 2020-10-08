package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class SeccionValidatorTest {

    private SeccionValidator seccionValidator;

    @BeforeEach
    public void init(){
        seccionValidator = new SeccionValidator();
    }

    @Test
    public void seccionValidateWhenNameIsNullThrowNegocioException(){
        //Arrange
        Seccion seccion = new Seccion();
        seccion.setId(1);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                seccionValidator.validator(seccion));

        //Assert
        assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, exception.getMessage());
    }

    @Test
    public void seccionValidateWhenNameIsEmptyThrowNegocioException(){
        //Arrange
        Seccion seccion = new Seccion();
        seccion.setDescripcion("");
        seccion.setId(1);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                seccionValidator.validator(seccion));

        //Assert
        assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, exception.getMessage());
    }

}
