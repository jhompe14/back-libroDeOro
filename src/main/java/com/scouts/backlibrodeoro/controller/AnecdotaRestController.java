package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.AnecdotaDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.service.AnecdotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anecdota")
@CrossOrigin(origins = "*", methods= {RequestMethod.POST})
public class AnecdotaRestController {

    private final AnecdotaService anecdotaService;

    @Autowired
    public AnecdotaRestController(AnecdotaService anecdotaService) {
        this.anecdotaService = anecdotaService;
    }


    @GetMapping
    public ResponseEntity<List<Anecdota>> findByUsuario(@PathVariable("usuarioSession") String usuarioSession,
                                                        @PathVariable("usuarioFilter") String usuarioFilter,
                                                        @PathVariable("estadoAnecdota") String estadoAnecdota)  {
        return new ResponseEntity(this.anecdotaService.getFilterAnecdota(usuarioSession,
                usuarioFilter, estadoAnecdota), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Anecdota> createAnecdota(@RequestBody AnecdotaDTO anecdotaDTO) throws NegocioException {
        try{
            return new ResponseEntity(anecdotaService.createAnecdota(anecdotaDTO), HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
