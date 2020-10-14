package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.CargoDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Cargo;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.service.CargoService;
import com.scouts.backlibrodeoro.types.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cargo")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE})
public class CargoController {

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/type/{typeCargo}/id/{idType}")
    public ResponseEntity<Cargo> findAllCargosByType(@PathVariable("typeCargo") String typeCargo,
                                                     @PathVariable("idType") Integer idType)  {
        return new ResponseEntity(this.cargoService.getAllCargosByType(typeCargo, idType), HttpStatus.OK);
    }

    @GetMapping("/{idCargo}")
    public ResponseEntity<Seccion> findById(@PathVariable("idCargo") Integer idCargo) throws NegocioException {
        try {
            return new ResponseEntity(this.cargoService.getCargo(idCargo), HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/type/{typeCargo}/id/{idType}")
    public ResponseEntity<Seccion> createCargo(@PathVariable("typeCargo") String typeCargo,
                                               @PathVariable("idType") Integer idType,
                                               @RequestBody CargoDTO cargoDTO) throws NegocioException{
        try{
            return new ResponseEntity(this.cargoService.createCargo(typeCargo, idType, cargoDTO), HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idCargo}")
    public ResponseEntity<Seccion> updateCargo(@PathVariable("idCargo") Integer idCargo,
                                                 @RequestBody CargoDTO cargoDTO)
            throws NegocioException{
        try{
            return new ResponseEntity(this.cargoService.updateCargo(idCargo, cargoDTO), HttpStatus.ACCEPTED);
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
