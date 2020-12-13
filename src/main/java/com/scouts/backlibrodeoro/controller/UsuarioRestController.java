package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.request.ContrasenaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.TrayectoriaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.UsuarioRequestDTO;
import com.scouts.backlibrodeoro.dto.response.UsuarioResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE})
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<UsuarioResponseDTO> findByUsuario(@PathVariable("usuario") String usuario) {
        try {
            return new ResponseEntity(this.usuarioService.getUsuario(usuario), HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        try{
            return new ResponseEntity(new HashMap<String, Boolean>() {{
                put("valid", usuarioService.validateUsuario(usuarioRequestDTO));
            }}, HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/trayectoria/validate")
    public ResponseEntity<Map<String, Boolean>> validateTrayectoria(@RequestBody TrayectoriaRequestDTO trayectoriaRequestDTO){
        try{
            return new ResponseEntity(new HashMap<String, Boolean>() {{
                put("valid", usuarioService.validateTrayectoria(trayectoriaRequestDTO));
            }}, HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try{
            return new ResponseEntity(usuarioService.createUsuario(usuarioRequestDTO), HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException ex){
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try{
            return new ResponseEntity(usuarioService.updateUsuario(usuarioRequestDTO), HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException ex){
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("contrasena/{usuario}")
    public ResponseEntity<Void> updateContrasena(@PathVariable("usuario") String usuario, @RequestBody ContrasenaRequestDTO contrasenaRequestDTO) {
        try{
            usuarioService.updateContrasena(usuario, contrasenaRequestDTO);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
