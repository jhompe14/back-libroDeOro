package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.request.CargoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.service.CargoService;
import com.scouts.backlibrodeoro.types.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargo")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE})
public class CargoRestController {

    private final CargoService cargoService;

    @Autowired
    public CargoRestController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> findByAll(){
        return new ResponseEntity(this.cargoService.getAllCargos(), HttpStatus.OK);
    }

    @GetMapping("/type/{typeCargo}/id/{idType}")
    public ResponseEntity<List<Cargo>> findAllCargosByType(@PathVariable("typeCargo") String typeCargo,
                                                          @PathVariable("idType") Integer idType)  {
        return new ResponseEntity(this.cargoService.getAllCargosByType(typeCargo, idType), HttpStatus.OK);
    }

    @GetMapping("/{idCargo}")
    public ResponseEntity<Cargo> findById(@PathVariable("idCargo") Integer idCargo) throws NegocioException {
        try {
            return new ResponseEntity(this.cargoService.getCargo(idCargo), HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/type/{typeCargo}/id/{idType}")
    public ResponseEntity<Cargo> createCargo(@PathVariable("typeCargo") String typeCargo,
                                               @PathVariable("idType") Integer idType,
                                               @RequestBody CargoRequestDTO cargoRequestDTO) throws NegocioException{
        try{
            return new ResponseEntity(this.cargoService.createCargo(typeCargo, idType, cargoRequestDTO), HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idCargo}")
    public ResponseEntity<Cargo> updateCargo(@PathVariable("idCargo") Integer idCargo,
                                                 @RequestBody CargoRequestDTO cargoRequestDTO)
            throws NegocioException{
        try{
            return new ResponseEntity(this.cargoService.updateCargo(idCargo, cargoRequestDTO), HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idCargo}")
    public ResponseEntity deleteCargo(@PathVariable("idCargo") Integer idCargo) throws NegocioException{
        try{
            cargoService.deleteCargo(idCargo);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.NOTFOUND)
                    ? HttpStatus.NOT_FOUND: HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
