package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.request.AnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.service.AnecdotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<List<AnecdotaGridResponseDTO>> findByUsuario(HttpServletRequest request) {
        try {
            FilterAnecdotaRequestDTO filterAnecdotaRequestDTO =
                    new FilterAnecdotaRequestDTO(request.getParameter("idGrupo"),
                            request.getParameter("idRama"),
                            request.getParameter("idSeccion"),
                            request.getParameter("fechaInicioAnecdota"),
                            request.getParameter("fechaFinAnecdota"),
                            request.getParameter("estado"),
                            request.getParameter("usuarioFilter"),
                            request.getParameter("usuarioOwner"),
                            request.getParameter("typeUsuarioOwner"));

            return new ResponseEntity(this.anecdotaService.getFilterAnecdota(filterAnecdotaRequestDTO), HttpStatus.OK);
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



}
