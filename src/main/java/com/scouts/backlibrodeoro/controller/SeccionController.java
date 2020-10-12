package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.SeccionDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.service.SeccionService;
import com.scouts.backlibrodeoro.types.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seccion")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE})
public class SeccionController {

    private SeccionService seccionService;

    @Autowired
    public SeccionController(SeccionService seccionService) {
        this.seccionService = seccionService;
    }

    @GetMapping
    public ResponseEntity<Seccion> findByAll()  {
        return new ResponseEntity(this.seccionService.getAllSecciones(), HttpStatus.OK);
    }

    @GetMapping("/{idSeccion}")
    public ResponseEntity<Seccion> findById(@PathVariable("idSeccion") Integer idSeccion) throws NegocioException {
        try {
            return new ResponseEntity(this.seccionService.getSeccion(idSeccion), HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/rama/{idRama}")
    public ResponseEntity<Seccion> createSeccion(@PathVariable("idRama") Integer idRama, @RequestBody SeccionDTO seccionDTO)
            throws NegocioException{
        try{
            return new ResponseEntity(this.seccionService.createSeccion(seccionDTO, idRama), HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idSeccion}")
    public ResponseEntity<Seccion> updateSeccion(@PathVariable("idSeccion") Integer idSeccion, @RequestBody SeccionDTO seccionDTO)
            throws NegocioException{
        try{
            return new ResponseEntity(this.seccionService.updateSeccion(idSeccion, seccionDTO), HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idSeccion}")
    public ResponseEntity deleteSeccion(@PathVariable("idSeccion") Integer idSeccion) throws NegocioException{
        try{
            seccionService.deleteSeccion(idSeccion);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.NOTFOUND)
                    ? HttpStatus.NOT_FOUND: HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
