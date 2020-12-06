package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.request.AnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.EstadoAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaGridRequestDTO;
import com.scouts.backlibrodeoro.dto.request.GrupoRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaResponseDTO;
import com.scouts.backlibrodeoro.dto.response.GridResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.model.Grupo;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.service.AnecdotaService;
import com.scouts.backlibrodeoro.types.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/anecdota")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class AnecdotaRestController {

    private final AnecdotaService anecdotaService;

    @Autowired
    public AnecdotaRestController(AnecdotaService anecdotaService) {
        this.anecdotaService = anecdotaService;
    }

    @GetMapping("/{idAnecdota}")
    public ResponseEntity<AnecdotaResponseDTO> findById(@PathVariable("idAnecdota") Integer idAnecdota) throws NegocioException {
        try {
            return new ResponseEntity(this.anecdotaService.getAnecdota(idAnecdota), HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<GridResponseDTO>> findGridAnecdota(HttpServletRequest request) {
        try {
            FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO =
                    new FilterAnecdotaGridRequestDTO(request.getParameter("idGrupo"),
                            request.getParameter("idRama"),
                            request.getParameter("idSeccion"),
                            request.getParameter("fechaInicioAnecdota"),
                            request.getParameter("fechaFinAnecdota"),
                            request.getParameter("estado"),
                            request.getParameter("usuarioFilter"),
                            request.getParameter("usuarioOwner"),
                            request.getParameter("typeUsuarioOwner"),
                            request.getParameter("page"));

            return new ResponseEntity(new GridResponseDTO<>(
                    this.anecdotaService.countFilterAnecdota(filterAnecdotaGridRequestDTO),
                    this.anecdotaService.getFilterAnecdota(filterAnecdotaGridRequestDTO)), HttpStatus.OK);
        } catch (RuntimeException ex){
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Anecdota> createAnecdota(@RequestBody AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException {
        try{
            return new ResponseEntity(anecdotaService.createAnecdota(anecdotaRequestDTO), HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException ex){
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idAnecdota}")
    public ResponseEntity<Anecdota> updateAnecdota(@PathVariable("idAnecdota") Integer idAnecdota, @RequestBody AnecdotaRequestDTO anecdotaRequestDTO)
            throws NegocioException{
        try{
            return new ResponseEntity(this.anecdotaService.updateAnecdota(idAnecdota, anecdotaRequestDTO), HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/estado/{idAnecdota}")
    public ResponseEntity<Anecdota> updateEstadoAnecdota(@PathVariable("idAnecdota") Integer idAnecdota, @RequestBody EstadoAnecdotaRequestDTO estadoAnecdotaRequestDTO)
            throws NegocioException{
        try{
            return new ResponseEntity(this.anecdotaService.updateEstadoAnecdota(idAnecdota, estadoAnecdotaRequestDTO), HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
