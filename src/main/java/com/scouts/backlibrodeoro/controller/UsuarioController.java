package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.UsuarioDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE})
public class UsuarioController {

    @PostMapping
    public ResponseEntity<String> createUsuario(@RequestBody UsuarioDTO usuarioDTO) throws NegocioException {
        try{
            System.out.println("hola");
            return new ResponseEntity("", HttpStatus.CREATED);
        }/*catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }*/catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
