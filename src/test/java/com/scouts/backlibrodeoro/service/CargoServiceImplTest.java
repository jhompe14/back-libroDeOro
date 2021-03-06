package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.CargoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.service.impl.CargoServiceImpl;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.CargoValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CargoServiceImplTest {

    private CargoRepository cargoRepository;
    private GrupoRepository grupoRepository;
    private RamaRepository ramaRepository;
    private SeccionRepository seccionRepository;
    private CargoValidator cargoValidator;

    private CargoServiceImpl cargoServiceImpl;

    @BeforeEach
    public void init(){
        cargoRepository = mock(CargoRepository.class);
        grupoRepository = mock(GrupoRepository.class);
        ramaRepository = mock(RamaRepository.class);
        seccionRepository = mock(SeccionRepository.class);
        cargoValidator = mock(CargoValidator.class);
        cargoServiceImpl = new CargoServiceImpl(cargoRepository, grupoRepository, ramaRepository,
                seccionRepository, cargoValidator);
    }

    @Test
    public void getAllCargosByTypeWhenTypeIsGR(){
        //Arrange
        String typeCargo = "GR";
        Integer idType = 1;
        List<Cargo> cargoList = new ArrayList<>();
        cargoList.add(new Cargo());
        when(cargoRepository.findCargoByTypeGrupo(idType)).thenReturn(cargoList);

        //Act
        List<Cargo> result= cargoServiceImpl.getAllCargosByType(typeCargo, idType);

        //Assert
        verify(cargoRepository).findCargoByTypeGrupo(idType);
        assertEquals(cargoList.size(), result.size());
    }

    @Test
    public void getAllCargosByTypeWhenTypeIsRA(){
        //Arrange
        String typeCargo = "RA";
        Integer idType = 1;
        List<Cargo> cargoList = new ArrayList<>();
        cargoList.add(new Cargo());
        when(cargoRepository.findCargoByTypeRama(idType)).thenReturn(cargoList);

        //Act
        List<Cargo> result= cargoServiceImpl.getAllCargosByType(typeCargo, idType);

        //Assert
        verify(cargoRepository).findCargoByTypeRama(idType);
        assertEquals(cargoList.size(), result.size());
    }

    @Test
    public void getAllCargosByTypeWhenTypeIsSE(){
        //Arrange
        String typeCargo = "SE";
        Integer idType = 1;
        List<Cargo> cargoList = new ArrayList<>();
        cargoList.add(new Cargo());
        when(cargoRepository.findCargoByTypeSeccion(idType)).thenReturn(cargoList);

        //Act
        List<Cargo> result= cargoServiceImpl.getAllCargosByType(typeCargo, idType);

        //Assert
        verify(cargoRepository).findCargoByTypeSeccion(idType);
        assertEquals(cargoList.size(), result.size());
    }

    @Test
    public void getCargoWhenFindCargo() throws NegocioException {
        //Arrange
        Cargo cargo = new Cargo();
        cargo.setId(1);
        Optional cargoOptional = Optional.of(cargo);
        Integer idCargo = 1;
        when(cargoRepository.findById(idCargo)).thenReturn(cargoOptional);

        //Act
        Cargo result = cargoServiceImpl.getCargo(idCargo);

        //Assert
        assertEquals(cargo.getId(), result.getId());
    }

    @Test
    public void getCargoWhenNotFindCargoAndThrowNegocioException(){
        //Arrange
        Optional cargoOptional = Optional.empty();
        Integer idCargo = 1;
        when(cargoRepository.findById(idCargo)).thenReturn(cargoOptional);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () ->
                cargoServiceImpl.getCargo(idCargo), MessagesValidation.ERROR_CARGO_NO_EXISTE);

        //Assert
        assertEquals(MessagesValidation.ERROR_CARGO_NO_EXISTE, exception.getMessage());
    }

    @Test
    public void createCargoWhenValidateThrowNegocioException() throws NegocioException {
        //Arrange
        CargoRequestDTO cargoRequestDTO = new CargoRequestDTO();
        cargoRequestDTO.setNombre("seccion prueba");
        Integer idType= 1;
        doThrow(new NegocioException(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, TypeException.VALIDATION))
                .when(cargoValidator).validator(any());

        //Act
        try {
            cargoServiceImpl.createCargo("", idType, cargoRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, ex.getMessage());
        }
    }

    @Test
    public void createCargoWhenTypeCargoIsGR() throws NegocioException {
        //Arrange
        CargoRequestDTO cargoRequestDTO = new CargoRequestDTO();
        cargoRequestDTO.setNombre("seccion prueba");
        doNothing().when(cargoValidator).validator(any());
        Integer idType= 1;
        String typeCargo = "GR";
        when(grupoRepository.findById(idType)).thenReturn(Optional.of(new Grupo()));
        Cargo cargo = new Cargo();
        cargo.setId(1);
        when(cargoRepository.save(any())).thenReturn(cargo);

        //Act
        Cargo result= cargoServiceImpl.createCargo(typeCargo, idType, cargoRequestDTO);

        //Assert
        verify(grupoRepository).findById(idType);
        verify(cargoRepository).save(any());
        assertEquals(cargo.getId(), result.getId());
    }

    @Test
    public void createCargoWhenTypeCargoIsRA() throws NegocioException {
        //Arrange
        CargoRequestDTO cargoRequestDTO = new CargoRequestDTO();
        cargoRequestDTO.setNombre("seccion prueba");
        doNothing().when(cargoValidator).validator(any());
        Integer idType= 1;
        String typeCargo = "RA";
        when(ramaRepository.findById(idType)).thenReturn(Optional.of(new Rama()));
        Cargo cargo = new Cargo();
        cargo.setId(1);
        when(cargoRepository.save(any())).thenReturn(cargo);

        //Act
        Cargo result= cargoServiceImpl.createCargo(typeCargo, idType, cargoRequestDTO);

        //Assert
        verify(ramaRepository).findById(idType);
        verify(cargoRepository).save(any());
        assertEquals(cargo.getId(), result.getId());
    }

    @Test
    public void createCargoWhenTypeCargoIsSE() throws NegocioException {
        //Arrange
        CargoRequestDTO cargoRequestDTO = new CargoRequestDTO();
        cargoRequestDTO.setNombre("seccion prueba");
        doNothing().when(cargoValidator).validator(any());
        Integer idType= 1;
        String typeCargo = "SE";
        when(seccionRepository.findById(idType)).thenReturn(Optional.of(new Seccion()));
        Cargo cargo = new Cargo();
        cargo.setId(1);
        when(cargoRepository.save(any())).thenReturn(cargo);

        //Act
        Cargo result= cargoServiceImpl.createCargo(typeCargo, idType, cargoRequestDTO);

        //Assert
        verify(seccionRepository).findById(idType);
        verify(cargoRepository).save(any());
        assertEquals(cargo.getId(), result.getId());
    }

    @Test
    public void updateCargoWhenCargoNotExistAndThrowNegocioException(){
        //Arrange
        Integer idCargo= 1;
        CargoRequestDTO cargoRequestDTO = new CargoRequestDTO();
        cargoRequestDTO.setNombre("hola nuevo nombre");
        when(cargoRepository.findById(idCargo)).thenReturn(Optional.empty());

        //Act
        try {
            cargoServiceImpl.updateCargo(idCargo, cargoRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_CARGO_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void updateCargoWhenValidateThrowNegocioException() throws NegocioException {
        //Arrange
        Integer idCargo= 1;
        CargoRequestDTO cargoRequestDTO = new CargoRequestDTO();
        when(cargoRepository.findById(idCargo)).thenReturn(Optional.of(new Cargo()));
        doThrow(new NegocioException(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, TypeException.VALIDATION))
                .when(cargoValidator).validator(any());

        //Act
        try {
            cargoServiceImpl.updateCargo(idCargo, cargoRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, ex.getMessage());
        }
    }

    @Test
    public void updateCargoWhenUpdateObject() throws NegocioException {
        //Arrange
        Integer idCargo= 1;
        CargoRequestDTO cargoRequestDTO = new CargoRequestDTO();
        when(cargoRepository.findById(idCargo)).thenReturn(Optional.of(new Cargo()));

        Cargo cargo = new Cargo();
        cargo.setId(1);

        doNothing().when(cargoValidator).validator(any());
        when(cargoRepository.save(any())).thenReturn(cargo);

        //Act
        Cargo result = cargoServiceImpl.updateCargo(idCargo, cargoRequestDTO);

        //Assert
        assertEquals(cargo.getId(), result.getId());
    }

    @Test
    public void deleteCargoWhenCargoNotExistAndThrowNegocioException(){
        //Arrange
        Integer idCargo = 1;
        when(cargoRepository.findById(idCargo)).thenReturn(Optional.empty());

        //Act
        try {
            cargoServiceImpl.deleteCargo(idCargo);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_CARGO_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void deleteCargoWhenDeleteObject() throws NegocioException {
        //Arrange
        Integer idCargo= 1;
        when(cargoRepository.findById(idCargo)).thenReturn(Optional.of(new Cargo()));
        doNothing().when(cargoRepository).delete(any());

        //Act
        cargoServiceImpl.deleteCargo(idCargo);

        //Assert
        verify(cargoRepository).delete(any());
    }


}
