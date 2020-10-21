package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.RamaDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Rama;
import com.scouts.backlibrodeoro.service.RamaService;
import com.scouts.backlibrodeoro.types.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rama")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE})
public class RamaController {

    private RamaService ramaService;

    @Autowired
    public RamaController(RamaService ramaService) {
        this.ramaService = ramaService;
    }

    @GetMapping
    public ResponseEntity<List<Rama>> findByAll()  {
        return new ResponseEntity(this.ramaService.getAllRamas(), HttpStatus.OK);
    }

    @GetMapping("/{idRama}")
    public ResponseEntity<Rama> findById(@PathVariable("idRama") Integer idRama) throws NegocioException {
        try {
            return new ResponseEntity(this.ramaService.getRama(idRama), HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<Rama> createRama(@PathVariable("idGrupo") Integer idGrupo, @RequestBody RamaDTO ramaDTO)
            throws NegocioException{
        try{
            return new ResponseEntity(this.ramaService.createRama(ramaDTO, idGrupo), HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idRama}")
    public ResponseEntity<Rama> updateRama(@PathVariable("idRama") Integer idRama, @RequestBody RamaDTO ramaDTO)
            throws NegocioException{
        try{
            return new ResponseEntity(this.ramaService.updateRama(idRama, ramaDTO), HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idRama}")
    public ResponseEntity deleteRama(@PathVariable("idRama") Integer idRama) throws NegocioException{
        try{
            ramaService.deleteRama(idRama);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.NOTFOUND)
                    ? HttpStatus.NOT_FOUND: HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
