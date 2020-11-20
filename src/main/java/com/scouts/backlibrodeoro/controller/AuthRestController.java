package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.auth.JWTGenerator;
import com.scouts.backlibrodeoro.dto.request.AuthRequestDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.service.UsuarioService;
import com.scouts.backlibrodeoro.types.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", methods= {RequestMethod.POST, RequestMethod.GET})
public class AuthRestController {

    private final UsuarioService usuarioService;

    @Autowired
    public AuthRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<AuthRequestDTO> login(@RequestBody AuthRequestDTO auth) throws NegocioException {
        try {
            if(usuarioService.authIsSuccess(auth)) {
                String token = JWTGenerator.getJWTToken(auth.getUsuario());
                auth.setToken(token);
            }
            return new ResponseEntity(auth, HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<String> pingAuth()  {
        return new ResponseEntity( new HashMap<String, String>() {{
            put("message", "OK");
        }}, HttpStatus.OK);
    }

}
