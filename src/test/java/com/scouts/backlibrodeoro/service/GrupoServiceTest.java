package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.GrupoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.repository.CargoRepository;
import com.scouts.backlibrodeoro.repository.GrupoRepository;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.GrupoValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GrupoServiceTest {

    private GrupoRepository grupoRepository;
    private RamaRepository ramaRepository;
    private CargoRepository cargoRepository;
    private GrupoValidator grupoValidator;

    private GrupoService grupoService;

    @BeforeEach
    public void init(){
        grupoRepository = mock(GrupoRepository.class);
        ramaRepository = mock(RamaRepository.class);
        cargoRepository = mock(CargoRepository.class);
        grupoValidator = mock(GrupoValidator.class);
        grupoService = new GrupoService(grupoRepository, ramaRepository, cargoRepository, grupoValidator);
    }

    @Test
    public void getAllGruposWhenNotHaveGruposReturnArrayEmpty(){
        //Arrange
        List<Grupo> grupoList = new ArrayList<>();
        when(grupoRepository.findAll()).thenReturn(grupoList);

        //Act
        List<Grupo> result = grupoService.getAllGrupos();

        //Assert
        assertEquals(grupoList.size(), result.size());
    }

    @Test
    public void getAllGruposWhenNotHaveGruposReturnArrayNotIsEmpty(){
        //Arrange
        List<Grupo> grupoList = new ArrayList<>();
        grupoList.add(new Grupo());
        when(grupoRepository.findAll()).thenReturn(grupoList);

        //Act
        List<Grupo> result = grupoService.getAllGrupos();

        //Assert
        assertEquals(grupoList.size(), result.size());
    }

    @Test
    public void getGrupoWhenFindGrupo() throws NegocioException {
        //Arrange
        Grupo grupo = new Grupo();
        grupo.setId(1);
        Optional grupoOptional = Optional.of(grupo);
        Integer idGrupo = 1;
        when(grupoRepository.findById(idGrupo)).thenReturn(grupoOptional);

        //Act
        Grupo result = grupoService.getGrupo(idGrupo);

        //Assert
        assertEquals(grupo.getId(), result.getId());
    }

    @Test
    public void getGrupoWhenNotFindGrupo() throws NegocioException {
        //Arrange
        Optional grupoOptional = Optional.empty();
        Integer idGrupo = 1;
        when(grupoRepository.findById(idGrupo)).thenReturn(grupoOptional);

        //Act
        NegocioException exception= Assertions.assertThrows(NegocioException.class, () -> grupoService.getGrupo(idGrupo),
                MessagesValidation.ERROR_GRUPO_NO_EXISTE);

        //Assert
        assertEquals(MessagesValidation.ERROR_GRUPO_NO_EXISTE, exception.getMessage());
    }

    @Test
    public void createGrupoWhenThrowValidator() throws NegocioException {
        //Arrange
        GrupoRequestDTO grupoRequestDTO = new GrupoRequestDTO();
        doThrow(new NegocioException(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, TypeException.VALIDATION)).when(grupoValidator).validator(any());

        //Act
        try {
            grupoService.createGrupo(grupoRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, ex.getMessage());
        }
    }

    @Test
    public void createGrupoWhenCreateObject() throws NegocioException {
        //Arrange
        GrupoRequestDTO grupoRequestDTO = new GrupoRequestDTO();
        grupoRequestDTO.setNombre("nombre de prueba");
        grupoRequestDTO.setDescripcion("");

        Grupo grupo = new Grupo();
        grupo.setId(1);

        doNothing().when(grupoValidator).validator(any());
        when(grupoRepository.save(any())).thenReturn(grupo);

        //Act
        Grupo result = grupoService.createGrupo(grupoRequestDTO);

        //Assert
        assertEquals(grupo.getId(), result.getId());
    }

    @Test
    public void updateGrupoWhenGrupoNotExist() throws NegocioException{
        //Arrange
        Integer idGrupo= 1;
        GrupoRequestDTO grupoRequestDTO = new GrupoRequestDTO();
        when(grupoRepository.findById(idGrupo)).thenReturn(Optional.empty());

        //Act
        try {
            grupoService.updateGrupo(idGrupo, grupoRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_GRUPO_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void updateGrupoWhenThrowValidation() throws NegocioException{
        //Arrange
        Integer idGrupo= 1;
        GrupoRequestDTO grupoRequestDTO = new GrupoRequestDTO();
        when(grupoRepository.findById(idGrupo)).thenReturn(Optional.of(new Grupo()));
        doThrow(new NegocioException(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, TypeException.VALIDATION)).when(grupoValidator).validator(any());

        //Act
        try {
            grupoService.updateGrupo(idGrupo, grupoRequestDTO);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_NOMBRE_OBLIGATORIO, ex.getMessage());
        }
    }

    @Test
    public void updateGrupoWhenUpdateObject() throws NegocioException{
        //Arrange
        Integer idGrupo= 1;
        GrupoRequestDTO grupoRequestDTO = new GrupoRequestDTO();
        grupoRequestDTO.setNombre("nombre de prueba");
        grupoRequestDTO.setDescripcion("");

        Grupo grupo = new Grupo();
        grupo.setId(1);

        doNothing().when(grupoValidator).validator(any());
        when(grupoRepository.save(any())).thenReturn(grupo);
        when(grupoRepository.findById(idGrupo)).thenReturn(Optional.of(grupo));

        //Act
        Grupo result = grupoService.updateGrupo(idGrupo, grupoRequestDTO);

        //Assert
        assertEquals(grupo.getId(), result.getId());
    }

    @Test
    public void deleteGrupoWhenGrupoNotExist() throws NegocioException{
        //Arrange
        Integer idGrupo = 1;
        when(grupoRepository.findById(idGrupo)).thenReturn(Optional.empty());

        //Act
        //Act
        try {
            grupoService.deleteGrupo(idGrupo);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.ERROR_GRUPO_NO_EXISTE, ex.getMessage());
        }
    }

    @Test
    public void deleteGrupoWhenHaveRamasActive(){
        //Arrange
        Integer idGrupo = 1;
        when(grupoRepository.findById(idGrupo)).thenReturn(Optional.of(new Grupo()));
        when(ramaRepository.countRamaByGrupo(idGrupo)).thenReturn(1);

        //Act
        try {
            grupoService.deleteGrupo(idGrupo);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_GRUPO_RAMAS_ACTIVAS, ex.getMessage());
        }
    }

    @Test
    public void deleteGrupoWhenHaveCargosActive(){
        //Arrange
        Integer idGrupo = 1;
        when(grupoRepository.findById(idGrupo)).thenReturn(Optional.of(new Grupo()));
        when(ramaRepository.countRamaByGrupo(idGrupo)).thenReturn(0);
        when(cargoRepository.countCargoByTypeGrupo(idGrupo)).thenReturn(1);

        //Act
        try {
            grupoService.deleteGrupo(idGrupo);
        }catch (NegocioException ex){
            //Assert
            assertEquals(MessagesValidation.VALIDATION_GRUPO_CARGOS_ACTIVOS, ex.getMessage());
        }
    }

    @Test
    public void deteleGrupoWhenDeteleObject() throws NegocioException {
        //Arrange
        Integer idGrupo = 1;
        when(grupoRepository.findById(idGrupo)).thenReturn(Optional.of(new Grupo()));
        when(ramaRepository.countRamaByGrupo(idGrupo)).thenReturn(0);
        when(cargoRepository.countCargoByTypeGrupo(idGrupo)).thenReturn(0);
        doNothing().when(grupoRepository).delete(any());

        //Act
        grupoService.deleteGrupo(idGrupo);

        //Assert
        verify(grupoRepository).delete(any());
    }

}
