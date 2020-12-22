package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.SeccionRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.SeccionValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SeccionServiceTest {

    private SeccionRepository seccionRepository;
    private RamaRepository ramaRepository;
    private CargoRepository cargoRepository;
    private SeccionValidator seccionValidator;

    private SeccionService seccionService;

    @BeforeEach
    public void init(){
        seccionRepository= mock(SeccionRepository.class);
        ramaRepository= mock(RamaRepository.class);
        cargoRepository = mock(CargoRepository.class);
        seccionValidator = mock(SeccionValidator.class);
        seccionService = new SeccionService(seccionRepository, ramaRepository, cargoRepository, seccionValidator);
    }

    @Test
    public void getAllSeccionesWhenArrayIsEmpty(){
        //Arrange
        List<Seccion> seccionList = new ArrayList<>();
        when(seccionRepository.findAll()).thenReturn(seccionList);

        //Act
        List<Seccion> result = seccionService.getAllSecciones();

        //Assert
        assertEquals(seccionList.size(), result.size());
    }

    @Test
    public void getAllSeccionesWhenArrayIsNotEmpty(){
        //Arrange
        List<Seccion> seccionList = new ArrayList<>();
        seccionList.add(new Seccion());
        when(seccionRepository.findAll()).thenReturn(seccionList);

        //Act
        List<Seccion> result = seccionService.getAllSecciones();

        //Assert
        assertEquals(seccionList.size(), result.size());
    }

    @Test
    public void getSeccionWhenFindSeccion() throws NegocioException {
        //Arrange
        Seccion seccion = new Seccion();
        seccion.setId(1);
        Optional seccionOptional = Optional.of(seccion);
        Integer idSeccion = 1;
        when(seccionRepository.findById(idSeccion)).thenReturn(seccionOptional);

        //Act
        Seccion result = seccionService.getSeccion(idSeccion);

        //Assert
        assertEquals(seccion.getId(), result.getId());
    }

    @Test
    public void getSeccionWhenNotFindSeccionAndThrowNegocioException(){
        //Arrange
        Optional seccionOptional = Optional.empty();
        Integer idSeccion = 1;
        when(seccionRepository.findById(idSeccion)).thenReturn(seccionOptional);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                        seccionService.getSeccion(idSeccion), MessagesValidation.ERROR_RAMA_NO_EXISTE);

        //Assert
        assertEquals(MessagesValidation.ERROR_SECCION_NO_EXISTE, exception.getMessage());
    }

    @Test
    public void createSeccionWhenNotPassValidationAndThrowNegocioException() throws NegocioException {
        //Arrange
        SeccionRequestDTO seccionRequestDTO = new SeccionRequestDTO();
        seccionRequestDTO.setNombre("seccion prueba");
        Integer idRama= 1;
        doThrow(new NegocioException(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, TypeException.VALIDATION))
                .when(seccionValidator).validator(any());

        //Act
        try {
            seccionService.createSeccion(seccionRequestDTO, idRama);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, ex.getMessage());
        }
    }

    @Test
    public void createSeccionWhenRamaNotExistAndThrowNegocioException() throws NegocioException {
        //Arrange
        SeccionRequestDTO seccionRequestDTO = new SeccionRequestDTO();
        seccionRequestDTO.setNombre("rama prueba");
        Integer idRama= 1;
        doNothing().when(seccionValidator).validator(any());
        when(ramaRepository.findById(idRama)).thenReturn(Optional.empty());

        //Act
        try {
            seccionService.createSeccion(seccionRequestDTO, idRama);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_RAMA_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void createSeccionWhenCreateObject() throws NegocioException {
        //Arrange
        SeccionRequestDTO seccionRequestDTO = new SeccionRequestDTO();
        seccionRequestDTO.setNombre("nombre de prueba");
        seccionRequestDTO.setDescripcion("descripcion de prueba");

        Integer idRama = 1;

        Seccion seccion = new Seccion();
        seccion.setId(1);

        doNothing().when(seccionValidator).validator(any());
        when(ramaRepository.findById(idRama)).thenReturn(Optional.of(new Rama()));
        when(seccionRepository.save(any())).thenReturn(seccion);

        //Act
        Seccion result = seccionService.createSeccion(seccionRequestDTO, idRama);

        //Assert
        assertEquals(seccion.getId(), result.getId());
    }

    @Test
    public void updateSeccionWhenSeccionNotExistAndThrowNegocioException(){
        //Arrange
        Integer idSeccion= 1;
        SeccionRequestDTO seccionRequestDTO = new SeccionRequestDTO();
        seccionRequestDTO.setNombre("hola nuevo nombre");
        when(seccionRepository.findById(idSeccion)).thenReturn(Optional.empty());

        //Act
        try {
            seccionService.updateSeccion(idSeccion, seccionRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_SECCION_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void updateSeccionWhenValidateThrowNegocioException() throws NegocioException {
        //Arrange
        Integer idSeccion= 1;
        SeccionRequestDTO seccionRequestDTO = new SeccionRequestDTO();
        seccionRequestDTO.setIdRama(1);
        when(seccionRepository.findById(idSeccion)).thenReturn(Optional.of(new Seccion()));
        when(ramaRepository.findById(seccionRequestDTO.getIdRama())).thenReturn(Optional.of(new Rama()));
        doThrow(new NegocioException(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, TypeException.VALIDATION))
                .when(seccionValidator).validator(any());

        //Act
        try {
            seccionService.updateSeccion(idSeccion, seccionRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, ex.getMessage());
        }
    }

    @Test
    public void updateSeccionWhenUpdateObject() throws NegocioException {
        //Arrange
        Integer idSeccion= 1;
        SeccionRequestDTO seccionRequestDTO = new SeccionRequestDTO();
        seccionRequestDTO.setIdRama(1);
        when(seccionRepository.findById(idSeccion)).thenReturn(Optional.of(new Seccion()));
        when(ramaRepository.findById(seccionRequestDTO.getIdRama())).thenReturn(Optional.of(new Rama()));

        Seccion seccion = new Seccion();
        seccion.setId(1);

        doNothing().when(seccionValidator).validator(any());
        when(seccionRepository.save(any())).thenReturn(seccion);

        //Act
        Seccion result = seccionService.updateSeccion(idSeccion, seccionRequestDTO);

        //Assert
        assertEquals(seccion.getId(), result.getId());
    }

    @Test
    public void deleteSeccionWhenSeccionNotExistsAndThrowNegocioException() {
        //Arrange
        Integer idSeccion = 1;
        when(seccionRepository.findById(idSeccion)).thenReturn(Optional.empty());

        //Act
        try {
            seccionService.deleteSeccion(idSeccion);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_SECCION_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void deleteSeccionWhenHaveCargosActiveAndThrowNegocioException(){
        //Arrange
        Integer idSeccion= 1;
        when(seccionRepository.findById(idSeccion)).thenReturn(Optional.of(new Seccion()));
        when(cargoRepository.countCargoByTypeSeccion(idSeccion)).thenReturn(1);

        //Act
        try {
            seccionService.deleteSeccion(idSeccion);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_SECCION_CARGOS_ACTIVOS, ex.getMessage());
        }
    }

    @Test
    public void deleteSeccionWhenDeleteObject() throws NegocioException {
        //Arrange
        Integer idSeccion= 1;
        when(seccionRepository.findById(idSeccion)).thenReturn(Optional.of(new Seccion()));
        when(cargoRepository.countCargoByTypeSeccion(idSeccion)).thenReturn(0);
        doNothing().when(seccionRepository).delete(any());

        //Act
        seccionService.deleteSeccion(idSeccion);

        //Assert
        verify(seccionRepository).delete(any());
    }

}
