package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.request.AnecdotaDTO;
import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.service.AnecdotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Anecdota>> findByUsuario(HttpServletRequest request) {
        try {
            FilterAnecdotaDTO filterAnecdotaDTO =
                    new FilterAnecdotaDTO(request.getParameter("idGrupo"),
                            request.getParameter("idRama"),
                            request.getParameter("idSeccion"),
                            request.getParameter("fechaInicioAnecdota"),
                            request.getParameter("fechaFinAnecdota"),
                            request.getParameter("estado"),
                            request.getParameter("usuarioFilter"),
                            request.getParameter("usuarioOwner"));

            return new ResponseEntity(this.anecdotaService.getFilterAnecdota(filterAnecdotaDTO), HttpStatus.OK);
        } catch (RuntimeException ex){
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<Anecdota> createAnecdota(@RequestBody AnecdotaDTO anecdotaDTO) throws NegocioException {
        try{
            return new ResponseEntity(anecdotaService.createAnecdota(anecdotaDTO), HttpStatus.CREATED);
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
