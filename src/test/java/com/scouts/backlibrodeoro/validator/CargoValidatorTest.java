package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class CargoValidatorTest {

    private CargoValidator cargoValidator;

    @BeforeEach
    public void init(){
        cargoValidator = new CargoValidator();
    }

    @Test
    public void cargoValidateWhenNameIsNullThrowNegocioException(){
        //Arrange
        Cargo cargo = new Cargo();
        cargo.setId(1);

        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                cargoValidator.validator(cargo));

        assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, exception.getMessage());
    }

    @Test
    public void cargoValidateWhenNameIsEmptyThrowNegocioException(){
        //Arrange
        Cargo cargo = new Cargo();
        cargo.setNombre("");
        cargo.setId(1);

        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                cargoValidator.validator(cargo));

        assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, exception.getMessage());
    }

}
