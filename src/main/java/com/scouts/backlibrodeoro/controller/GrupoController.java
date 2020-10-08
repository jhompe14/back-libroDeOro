package com.scouts.backlibrodeoro.controller;


import com.scouts.backlibrodeoro.dto.GrupoDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.service.GrupoService;
import com.scouts.backlibrodeoro.types.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grupo")
public class GrupoController {

    private GrupoService grupoService;

    @Autowired
    public GrupoController(GrupoService grupoService){
        this.grupoService = grupoService;
    }

    @GetMapping
    public ResponseEntity<Grupo> findByAll()  {
        return new ResponseEntity(this.grupoService.getAllGrupos(), HttpStatus.OK);
    }

    @GetMapping("/{idGrupo}")
    public ResponseEntity<Grupo> findById(@PathVariable("idGrupo") Integer idGrupo) throws NegocioException {
        try {
            return new ResponseEntity(this.grupoService.getGrupo(idGrupo), HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Grupo> createGrupo(@RequestBody GrupoDTO grupoDTO) throws NegocioException{
        try{
            return new ResponseEntity(this.grupoService.createGrupo(grupoDTO), HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idGrupo}")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable("idGrupo") Integer idGrupo, @RequestBody GrupoDTO grupoDTO)
            throws NegocioException{
        try{
           return new ResponseEntity(this.grupoService.updateGrupo(idGrupo, grupoDTO), HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idGrupo}")
    public ResponseEntity deleteGrupo(@PathVariable("idGrupo") Integer idGrupo) throws NegocioException{
        try{
            grupoService.deleteGrupo(idGrupo);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.NOTFOUND)
                    ? HttpStatus.NOT_FOUND: HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
