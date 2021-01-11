package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.RamaRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.service.impl.RamaServiceImpl;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.RamaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RamaServiceImplTest {

    private RamaRepository ramaRepository;
    private SeccionRepository seccionRepository;
    private GrupoRepository grupoRepository;
    private CargoRepository cargoRepository;
    private RamaValidator ramaValidator;

    private RamaServiceImpl ramaServiceImpl;

    @BeforeEach
    public void init(){
        grupoRepository = mock(GrupoRepository.class);
        ramaRepository = mock(RamaRepository.class);
        cargoRepository = mock(CargoRepository.class);
        seccionRepository = mock(SeccionRepository.class);
        ramaValidator = mock(RamaValidator.class);
        ramaServiceImpl = new RamaServiceImpl(ramaRepository, grupoRepository, seccionRepository, cargoRepository, ramaValidator);
    }

    @Test
    public void getAllRamasWhenArrayIsEmpty(){
        //Arrange
        List<Rama> ramaList = new ArrayList<>();
        when(ramaRepository.findAll()).thenReturn(ramaList);

        //Act
        List<Rama> result = ramaServiceImpl.getAllRamas();

        //Assert
        assertEquals(ramaList.size(), result.size());
    }

    @Test
    public void getAllRamasWhenArrayNotIsEmpty(){
        //Arrange
        List<Rama> ramaList = new ArrayList<>();
        ramaList.add(new Rama());
        when(ramaRepository.findAll()).thenReturn(ramaList);

        //Act
        List<Rama> result = ramaServiceImpl.getAllRamas();

        //Assert
        assertEquals(ramaList.size(), result.size());
    }

    @Test
    public void getRamaWhenFindRama() throws NegocioException {
        //Arrange
        Rama rama = new Rama();
        rama.setId(1);
        Optional ramaOptional = Optional.of(rama);
        Integer idRama = 1;
        when(ramaRepository.findById(idRama)).thenReturn(ramaOptional);

        //Act
        Rama result = ramaServiceImpl.getRama(idRama);

        //Assert
        assertEquals(rama.getId(), result.getId());
    }

    @Test
    public void getRamaWhenNotFindRamaAndThrowNegocioException() {
        //Arrange
        Optional ramaOptional = Optional.empty();
        Integer idRama = 1;
        when(ramaRepository.findById(idRama)).thenReturn(ramaOptional);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () -> ramaServiceImpl.getRama(idRama),
                MessagesValidation.ERROR_RAMA_NO_EXISTE);

        //Assert
        assertEquals(MessagesValidation.ERROR_RAMA_NO_EXISTE, exception.getMessage());
    }

    @Test
    public void createRamaWhenNotPassValidationAndThrowNegocioException() throws NegocioException {
        //Arrange
        RamaRequestDTO ramaRequestDTO = new RamaRequestDTO();
        ramaRequestDTO.setNombre("rama prueba");
        Integer idGrupo= 1;
        doThrow(new NegocioException(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS, TypeException.VALIDATION)).when(ramaValidator).validator(any());

        //Act
        try {
            ramaServiceImpl.createRama(ramaRequestDTO, idGrupo);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS, ex.getMessage());
        }
    }

    @Test
    public void createRamaWhenGrupoNotExistAndThrowNegocioException() throws NegocioException {
        //Arrange
        RamaRequestDTO ramaRequestDTO = new RamaRequestDTO();
        ramaRequestDTO.setNombre("rama prueba");
        Integer idGrupo= 1;
        doNothing().when(ramaValidator).validator(any());
        when(grupoRepository.findById(idGrupo)).thenReturn(Optional.empty());

        //Act
        try {
            ramaServiceImpl.createRama(ramaRequestDTO, idGrupo);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_GRUPO_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void createRamaWhenCreateObject() throws NegocioException {
        //Arrange
        RamaRequestDTO ramaRequestDTO = new RamaRequestDTO();
        ramaRequestDTO.setNombre("nombre de prueba");
        ramaRequestDTO.setDescripcion("descripcion de prueba");
        ramaRequestDTO.setEdadMinima(3);
        ramaRequestDTO.setEdadMaxima(15);

        Integer idGrupo = 1;

        Rama rama = new Rama();
        rama.setId(1);

        doNothing().when(ramaValidator).validator(any());
        when(grupoRepository.findById(idGrupo)).thenReturn(Optional.of(new Grupo()));
        when(ramaRepository.save(any())).thenReturn(rama);

        //Act
        Rama result = ramaServiceImpl.createRama(ramaRequestDTO, idGrupo);

        //Assert
        assertEquals(rama.getId(), result.getId());
    }

    @Test
    public void updateRamaWhenRamaNotExistAndThrowNegocioException() {
        //Arrange
        Integer idRama= 1;
        RamaRequestDTO ramaRequestDTO = new RamaRequestDTO();
        ramaRequestDTO.setNombre("hola nuevo nombre");
        when(ramaRepository.findById(idRama)).thenReturn(Optional.empty());

        //Act
        try {
            ramaServiceImpl.updateRama(idRama, ramaRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_RAMA_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void updateRamaWhenValidateThrowNegocioException() throws NegocioException {
        //Arrange
        Integer idRama= 1;
        RamaRequestDTO ramaRequestDTO = new RamaRequestDTO();
        ramaRequestDTO.setIdGrupo(1);
        when(ramaRepository.findById(idRama)).thenReturn(Optional.of(new Rama()));
        when(grupoRepository.findById(ramaRequestDTO.getIdGrupo())).thenReturn(Optional.of(new Grupo()));
        doThrow(new NegocioException(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS, TypeException.VALIDATION)).when(ramaValidator).validator(any());

        //Act
        try {
            ramaServiceImpl.updateRama(idRama, ramaRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_TODOS_CAMPOS_OBLIGATORIOS, ex.getMessage());
        }
    }

    @Test
    public void updateRamaWhenUpdateObject() throws NegocioException {
        //Arrange
        Integer idRama= 1;
        RamaRequestDTO ramaRequestDTO = new RamaRequestDTO();
        ramaRequestDTO.setIdGrupo(1);
        when(ramaRepository.findById(idRama)).thenReturn(Optional.of(new Rama()));
        when(grupoRepository.findById(ramaRequestDTO.getIdGrupo())).thenReturn(Optional.of(new Grupo()));

        Rama rama = new Rama();
        rama.setId(1);

        doNothing().when(ramaValidator).validator(any());
        when(ramaRepository.save(any())).thenReturn(rama);

        //Act
        Rama result = ramaServiceImpl.updateRama(idRama, ramaRequestDTO);

        //Assert
        assertEquals(rama.getId(), result.getId());
    }

    @Test
    public void deleteRamaWhenRamaNotExistsAndThrowNegocioException(){
        //Arrange
        Integer idRama = 1;
        when(ramaRepository.findById(idRama)).thenReturn(Optional.empty());

        //Act
        try {
            ramaServiceImpl.deleteRama(idRama);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_RAMA_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void deleteRamaWhenRamaHaveSeccionesAndThrowNegocioException(){
        //Arrange
        Integer idRama = 1;
        when(ramaRepository.findById(idRama)).thenReturn(Optional.of(new Rama()));
        when(seccionRepository.countSeccionByRama(idRama)).thenReturn(0);

        //Act
        try {
            ramaServiceImpl.deleteRama(idRama);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_RAMA_SECCIONES_ACTIVAS, ex.getMessage());
        }
    }

    @Test
    public void deleteRamaWhenRamaHaveCargosAndThrowNegocioException(){
        //Arrange
        Integer idRama= 1;
        when(ramaRepository.findById(idRama)).thenReturn(Optional.of(new Rama()));
        when(seccionRepository.countSeccionByRama(idRama)).thenReturn(0);
        when(cargoRepository.countCargoByTypeRama(idRama)).thenReturn(1);

        //Act
        try {
            ramaServiceImpl.deleteRama(idRama);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_RAMA_CARGOS_ACTIVOS, ex.getMessage());
        }
    }

    @Test
    public void deleteRamaWhenDeleteObject() throws NegocioException {
        //Arrange
        Integer idRama= 1;
        when(ramaRepository.findById(idRama)).thenReturn(Optional.of(new Rama()));
        when(seccionRepository.countSeccionByRama(idRama)).thenReturn(0);
        when(cargoRepository.countCargoByTypeRama(idRama)).thenReturn(0);
        doNothing().when(ramaRepository).delete(any());

        //Act
        ramaServiceImpl.deleteRama(idRama);

        //Assert
        verify(ramaRepository).delete(any());
    }

}
