package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.request.*;
import com.scouts.backlibrodeoro.dto.response.PageResponseDTO;
import com.scouts.backlibrodeoro.dto.response.UsuarioResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.RecuperoContrasena;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.service.UsuarioService;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
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

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try {
            return new ResponseEntity(usuarioService.createUsuario(usuarioRequestDTO), HttpStatus.CREATED);
        } catch (NegocioException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try {
            return new ResponseEntity(usuarioService.updateUsuario(usuarioRequestDTO), HttpStatus.ACCEPTED);
        } catch (NegocioException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO> findGridUsuario(HttpServletRequest request) {
        try {
            FilterUsuarioGridRequestDTO filterUsuarioGridRequestDTO =
                    new FilterUsuarioGridRequestDTO(request.getParameter("usuario"),
                            request.getParameter("nombres"),
                            request.getParameter("apellidos"),
                            request.getParameter("page"));

            return new ResponseEntity(new PageResponseDTO<>(
                    this.usuarioService.countFilterUsuario(filterUsuarioGridRequestDTO),
                    this.usuarioService.getFilterUsuario(filterUsuarioGridRequestDTO)), HttpStatus.OK);
        } catch (RuntimeException ex){
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<UsuarioResponseDTO> findByUsuario(@PathVariable("usuario") String usuario) {
        try {
            return new ResponseEntity(this.usuarioService.getUsuario(usuario), HttpStatus.OK);
        } catch (NegocioException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try {
            return new ResponseEntity(new HashMap<String, Boolean>() {{
                put("valid", usuarioService.validateUsuario(usuarioRequestDTO));
            }}, HttpStatus.OK);
        } catch (NegocioException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/trayectoria/validate")
    public ResponseEntity<Map<String, Boolean>> validateTrayectoria(@RequestBody TrayectoriaRequestDTO trayectoriaRequestDTO) {
        try {
            return new ResponseEntity(new HashMap<String, Boolean>() {{
                put("valid", usuarioService.validateTrayectoria(trayectoriaRequestDTO));
            }}, HttpStatus.OK);
        } catch (NegocioException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
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

    @GetMapping("/recovered/contrasena/{idRecovered}")
    public ResponseEntity<Usuario> getUsuarioByRecovered(@PathVariable("idRecovered") String idRecovered){
        try {
            return new ResponseEntity(this.usuarioService.getUsuarioByRecovered(idRecovered), HttpStatus.OK);
        } catch (NegocioException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/recovered/contrasena/{usuario}")
    public ResponseEntity<RecuperoContrasena> recoveredContrasena(@PathVariable("usuario") String usuario){
        try{
            return new ResponseEntity(usuarioService.setRecoveredContrasena(usuario), HttpStatus.ACCEPTED);
        } catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (MessagingException ex){
            return new ResponseEntity(MessagesValidation.ERROR_CORREO_RECEPTOR_NO_VALIDO, HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/recovered/contrasena/{usuario}")
    public ResponseEntity<Void> updateRecoveredContrasena(@PathVariable("usuario") String usuario, @RequestBody ContrasenaRequestDTO contrasenaRequestDTO) {
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
