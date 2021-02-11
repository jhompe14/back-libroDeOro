package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.validator.impl.RamaValidator;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class RamaValidatorTest {

    private RamaValidator ramaValidator;

    @BeforeEach
    public void init(){
        ramaValidator = new RamaValidator();
    }

    @Test
    public void ramaValidatorWhenNameIsNullAndThrowNegocioException(){
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                ramaValidator.validator(rama));

        //Assert
        //assertEquals(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS, exception.getMessage());
    }

    @Test
    public void ramaValidatorWhenNameIsEmptyAndThrowNegocioException(){
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);
        rama.setNombre("");

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                ramaValidator.validator(rama));

        //Assert
        //assertEquals(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS, exception.getMessage());
    }

    @Test
    public void ramaValidatorWhenEdadMinimaIsNullAndThrowNegocioException(){
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);
        rama.setNombre("nombre prueba");

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                ramaValidator.validator(rama));

        //Assert
        //assertEquals(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS, exception.getMessage());
    }

    @Test
    public void ramaValidatorWhenEdadMaximaIsNullAndThrowNegocioException(){
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);
        rama.setNombre("nombre prueba");
        rama.setEdadMinima(5);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                ramaValidator.validator(rama));

        //Assert
        //assertEquals(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS, exception.getMessage());
    }

    @Test
    public void ramaValidatorWhenEdadMinimaIsNegativeAndThrowNegocioException(){
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);
        rama.setNombre("nombre prueba");
        rama.setEdadMinima(-5);
        rama.setEdadMaxima(15);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                ramaValidator.validator(rama));

        //Assert
        assertEquals(MessagesValidation.VALIDATION_EDAD_MINIMA_MAXIMA, exception.getMessage());
    }

    @Test
    public void ramaValidatorWhenEdadMinimaIsMajorTo200AndThrowNegocioException(){
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);
        rama.setNombre("nombre prueba");
        rama.setEdadMinima(201);
        rama.setEdadMaxima(15);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                ramaValidator.validator(rama));

        //Assert
        assertEquals(MessagesValidation.VALIDATION_EDAD_MINIMA_MAXIMA, exception.getMessage());
    }

    @Test
    public void ramaValidatorWhenEdadMaximaIsNegativeAndThrowNegocioException(){
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);
        rama.setNombre("nombre prueba");
        rama.setEdadMinima(15);
        rama.setEdadMaxima(-5);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                ramaValidator.validator(rama));

        //Assert
        assertEquals(MessagesValidation.VALIDATION_EDAD_MINIMA_MAXIMA, exception.getMessage());
    }

    @Test
    public void ramaValidatorWhenEdadMaximaIsMajorTo200AndThrowNegocioException(){
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);
        rama.setNombre("nombre prueba");
        rama.setEdadMinima(15);
        rama.setEdadMaxima(201);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                ramaValidator.validator(rama));

        //Assert
        assertEquals(MessagesValidation.VALIDATION_EDAD_MINIMA_MAXIMA, exception.getMessage());
    }

    @Test
    public void ramaValidatorWhenEdadMinimaIsMajorToEdadMaximaAndThrowNegocioException(){
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);
        rama.setNombre("nombre prueba");
        rama.setEdadMinima(25);
        rama.setEdadMaxima(15);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                ramaValidator.validator(rama));

        //Assert
        assertEquals(MessagesValidation.VALIDATION_EDAD_MAXIMA_MAJOR_EDAD_MINIMA, exception.getMessage());
    }


}
