package com.scouts.backlibrodeoro.controller;


import com.scouts.backlibrodeoro.dto.request.GrupoRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.service.GrupoService;
import com.scouts.backlibrodeoro.types.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupo")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE})
public class GrupoRestController {

    private GrupoService grupoService;

    @Autowired
    public GrupoRestController(GrupoService grupoService){
        this.grupoService = grupoService;
    }

    @GetMapping
    public ResponseEntity<List<Grupo>> findByAll()  {
        return new ResponseEntity(this.grupoService.getAllGrupos(), HttpStatus.OK);
    }

    @GetMapping("/{idGrupo}")
    public ResponseEntity<Grupo> findById(@PathVariable("idGrupo") Integer idGrupo) {
        try {
            return new ResponseEntity(this.grupoService.getGrupo(idGrupo), HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Grupo> createGrupo(@RequestBody GrupoRequestDTO grupoRequestDTO) {
        try{
            return new ResponseEntity(this.grupoService.createGrupo(grupoRequestDTO), HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idGrupo}")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable("idGrupo") Integer idGrupo,
                                             @RequestBody GrupoRequestDTO grupoRequestDTO) {
        try{
           return new ResponseEntity(this.grupoService.updateGrupo(idGrupo, grupoRequestDTO), HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idGrupo}")
    public ResponseEntity deleteGrupo(@PathVariable("idGrupo") Integer idGrupo) {
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
