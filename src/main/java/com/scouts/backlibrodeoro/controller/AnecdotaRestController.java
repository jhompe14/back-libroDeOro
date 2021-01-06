package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.dto.request.*;
import com.scouts.backlibrodeoro.dto.response.AnecdotaResponseDTO;
import com.scouts.backlibrodeoro.dto.response.CatalogAnecdotaResponseDTO;
import com.scouts.backlibrodeoro.dto.response.PageResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.model.Enlace;
import com.scouts.backlibrodeoro.service.AnecdotaService;
import com.scouts.backlibrodeoro.types.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/anecdota")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AnecdotaRestController {

    private final AnecdotaService anecdotaService;

    @Autowired
    public AnecdotaRestController(AnecdotaService anecdotaService) {
        this.anecdotaService = anecdotaService;
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO> findGridAnecdota(HttpServletRequest request) {
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
                            request.getParameter("codigoAnecdota"),
                            request.getParameter("page"));

            return new ResponseEntity(new PageResponseDTO<>(
                    this.anecdotaService.countFilterAnecdota(filterAnecdotaGridRequestDTO),
                    this.anecdotaService.getFilterAnecdota(filterAnecdotaGridRequestDTO)), HttpStatus.OK);
        } catch (RuntimeException ex){
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idAnecdota}")
    public ResponseEntity<AnecdotaResponseDTO> findById(@PathVariable("idAnecdota") Integer idAnecdota) {
        try {
            return new ResponseEntity(this.anecdotaService.getAnecdota(idAnecdota), HttpStatus.OK);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/catalog")
    public ResponseEntity<PageResponseDTO> findCatalogAnecdota(HttpServletRequest request) {
        try {
            CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO = new CatalogAnecdotaRequestDTO(
                    request.getParameter("usuario"), request.getParameter("page"));

            return new ResponseEntity(new PageResponseDTO<>(
                    this.anecdotaService.countCatalogAnecdota(catalogAnecdotaRequestDTO),
                    this.anecdotaService.getCatalogAnecdota(catalogAnecdotaRequestDTO)), HttpStatus.OK);
        } catch (RuntimeException ex){
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Anecdota> createAnecdota(@RequestParam(value = "nombre", required = false) String nombre,
                                                   @RequestParam(value = "fecha", required = false) String fecha,
                                                   @RequestParam("descripcion") String descripcion,
                                                   @RequestParam("usuario") String usuario,
                                                   @RequestParam("idRama") String idRama,
                                                   @RequestParam(value = "idSeccion", required = false) String idSeccion,
                                                   @RequestParam(value = "attachedFiles", required = false) List<MultipartFile> attachedFiles,
                                                   @RequestParam(value = "videos", required = false) List<String> videos) {
        try{
            return new ResponseEntity(anecdotaService.createAnecdota(
                            new AnecdotaRequestDTO(nombre, fecha, descripcion, usuario, idRama, idSeccion, attachedFiles, videos)),
                    HttpStatus.CREATED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException ex){
            NegocioException negocioException = (NegocioException) ex.getCause();
            return new ResponseEntity(negocioException.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{idAnecdota}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Anecdota> updateAnecdota(@PathVariable("idAnecdota") Integer idAnecdota,
                                                   @RequestParam(value = "nombre", required = false) String nombre,
                                                   @RequestParam(value = "fecha", required = false) String fecha,
                                                   @RequestParam("descripcion") String descripcion,
                                                   @RequestParam("usuario") String usuario,
                                                   @RequestParam("idRama") String idRama,
                                                   @RequestParam(value = "idSeccion", required = false) String idSeccion,
                                                   @RequestParam(value = "attachedFiles", required = false) List<MultipartFile> attachedFiles,
                                                   @RequestParam(value = "videos", required = false) List<String> videos){
        try{
            return new ResponseEntity(this.anecdotaService.updateAnecdota(idAnecdota,
                    new AnecdotaRequestDTO(nombre, fecha, descripcion, usuario, idRama, idSeccion, attachedFiles, videos)),
                    HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/estado/visualizacion/{idAnecdota}")
    public ResponseEntity<Anecdota> updateEstadoAnecdota(@PathVariable("idAnecdota") Integer idAnecdota,
                                                         @RequestBody EstadoAnecdotaRequestDTO estadoAnecdotaRequestDTO){
        try{
            return new ResponseEntity(this.anecdotaService
                    .updateEstadoVisualizacionAnecdota(idAnecdota, estadoAnecdotaRequestDTO), HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/enlace/{idAnecdota}")
    public ResponseEntity<List<Enlace>> getEnlacesByIdAnecdota(@PathVariable("idAnecdota") Integer idAnecdota){
        try {
            return new ResponseEntity(anecdotaService.getEnlacesByIdAnecdota(idAnecdota), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/enlace/{idEnlace}")
    public ResponseEntity deleteEnlaceAnecdota(@PathVariable("idEnlace") Integer idEnlace){
        try{
            this.anecdotaService.deleteEnlaceAnecdota(idEnlace);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }catch (NegocioException ex){
            return new ResponseEntity(ex.getMessage(), ex.getTypeException().equals(TypeException.VALIDATION) ?
                    HttpStatus.BAD_REQUEST: HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
