package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.external.CloudinaryComponent;
import com.scouts.backlibrodeoro.model.*;
import com.scouts.backlibrodeoro.util.QueryUtil;
import com.scouts.backlibrodeoro.dto.request.AnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.EstadoAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.repository.*;
import com.scouts.backlibrodeoro.types.TypeEstadoAnecdota;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.types.TypeSiNo;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.AnecdotaValidator;
import com.scouts.backlibrodeoro.validator.impl.EstadoAnecdotaRequestDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnecdotaService {

    private final UsuarioRepository usuarioRepository;
    private final RamaRepository ramaRepository;
    private final SeccionRepository seccionRepository;
    private final AnecdotaRepository anecdotaRepository;
    private final EstadoAnecdotaRepository estadoAnecdotaRepository;
    private final EnlaceRepository enlaceRepository;
    private final AnecdotaValidator anecdotaValidator;
    private final EstadoAnecdotaRequestDTOValidator estadoAnecdotaRequestDTOValidator;
    private final CloudinaryComponent cloudinaryComponent;

    @Autowired
    public AnecdotaService(UsuarioRepository usuarioRepository, RamaRepository ramaRepository,
                           SeccionRepository seccionRepository, AnecdotaRepository anecdotaRepository,
                           EstadoAnecdotaRepository estadoAnecdotaRepository, EnlaceRepository enlaceRepository,
                           AnecdotaValidator anecdotaValidator, EstadoAnecdotaRequestDTOValidator estadoAnecdotaRequestDTOValidator,
                           CloudinaryComponent cloudinaryComponent) {
        this.usuarioRepository = usuarioRepository;
        this.ramaRepository = ramaRepository;
        this.seccionRepository = seccionRepository;
        this.anecdotaRepository = anecdotaRepository;
        this.estadoAnecdotaRepository = estadoAnecdotaRepository;
        this.enlaceRepository = enlaceRepository;
        this.anecdotaValidator = anecdotaValidator;
        this.estadoAnecdotaRequestDTOValidator = estadoAnecdotaRequestDTOValidator;
        this.cloudinaryComponent = cloudinaryComponent;
    }

    @Transactional(readOnly = true)
    public Integer countFilterAnecdota(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO){
        return anecdotaRepository.countAnecdotasGrid(filterAnecdotaGridRequestDTO);
    }

    @Transactional(readOnly = true)
    public List<AnecdotaGridResponseDTO> getFilterAnecdota(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO){
        return anecdotaRepository.getAnecdotasGrid(filterAnecdotaGridRequestDTO);
    }

    @Transactional(readOnly = true)
    public AnecdotaResponseDTO getAnecdota(Integer id) throws NegocioException {
        return anecdotaRepository.getAnecdotaById(id).orElseThrow(
                () -> new NegocioException(MessagesValidation.ERROR_ANECDOTA_NO_EXISTE, TypeException.VALIDATION));
    }

    @Transactional
    public Anecdota createAnecdota(AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException {
        Anecdota anecdota = new Anecdota();
        transformDTOToAnecdota(anecdota, anecdotaRequestDTO);
        anecdotaValidator.validator(anecdota);
        anecdotaRepository.save(anecdota);
        addEstadoAnecdota(anecdota, TypeEstadoAnecdota.PA, anecdota.getUsuario());
        addAttachedAnecdota(anecdota, anecdotaRequestDTO.getAttachedFiles());
        return anecdota;
    }

    @Transactional
    public Anecdota updateAnecdota(Integer idAnecdota, AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException{
        Anecdota anecdota= anecdotaRepository.findById(idAnecdota).orElseThrow(
                () -> new NegocioException(MessagesValidation.ERROR_ANECDOTA_NO_EXISTE, TypeException.VALIDATION));
        transformDTOToAnecdota(anecdota, anecdotaRequestDTO);
        anecdotaValidator.validator(anecdota);
        anecdotaRepository.save(anecdota);
        addAttachedAnecdota(anecdota, anecdotaRequestDTO.getAttachedFiles());

        if(estadoAnecdotaRepository.findEstadoAnecdotaActive(idAnecdota).getEstado()
                .equals(TypeEstadoAnecdota.PM.toString())){
            addEstadoAnecdota(anecdota, TypeEstadoAnecdota.PA, anecdota.getUsuario());
        }

        return anecdota;
    }

    @Transactional
    public Anecdota updateEstadoVisualizacionAnecdota(Integer idAnecdota, EstadoAnecdotaRequestDTO estadoAnecdotaRequestDTO) throws NegocioException {
       estadoAnecdotaRequestDTOValidator.validator(estadoAnecdotaRequestDTO);
       Anecdota anecdota = anecdotaRepository.findById(idAnecdota).orElseThrow(
               () -> new NegocioException(MessagesValidation.ERROR_ANECDOTA_NO_EXISTE, TypeException.VALIDATION));
       anecdota.setVisualizacion(estadoAnecdotaRequestDTO.getVisualizacion());
       anecdotaRepository.save(anecdota);

        Usuario usuario = null;
        if(estadoAnecdotaRequestDTO.getEstado().equals(TypeEstadoAnecdota.PM.toString())){
            usuario = QueryUtil.getUsuarioByUsuario(usuarioRepository, estadoAnecdotaRequestDTO.getUsuarioModificacion());
        } else{
            usuario = QueryUtil.getUsuarioByUsuario(usuarioRepository, estadoAnecdotaRequestDTO.getUsuario());
        }
        addEstadoAnecdota(anecdota, TypeEstadoAnecdota.valueOf(estadoAnecdotaRequestDTO.getEstado()), usuario);

       return anecdota;
    }

    @Transactional
    public void deleteAttachedAnecdota(String idPublica) throws NegocioException {
        Optional.ofNullable(enlaceRepository.findEnlaceByIdPublica(idPublica)).map(enlace -> {
            try {
                Map result= cloudinaryComponent.delete(idPublica);
                if(Optional.ofNullable(result).isPresent() && Optional.ofNullable(result.get("result")).isPresent()
                        && result.get("result").toString().equals("ok")){
                    enlaceRepository.delete(enlace);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }).orElseThrow(() -> new NegocioException(MessagesValidation.VALIDATION_ATTACHED_ANECDOTA, TypeException.VALIDATION));
    }

    @FunctionalInterface
    public interface AddSeccion<T, R> {
        R apply(T t) throws NegocioException;
    }

    private void transformDTOToAnecdota(Anecdota anecdota, AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException {
        anecdota.setNombre(anecdotaRequestDTO.getNombre());
        anecdota.setFecha(GeneralValidates.validateFormatDate(anecdotaRequestDTO.getFecha()));
        anecdota.setDescripcion(anecdotaRequestDTO.getDescripcion());
        anecdota.setUsuario(QueryUtil.getUsuarioByUsuario(usuarioRepository, anecdotaRequestDTO.getUsuario()));
        anecdota.setRama(QueryUtil.getObjectById(ramaRepository, anecdotaRequestDTO.getIdRama()));

        AddSeccion<Integer, Seccion> addSeccion = (idSeccion) -> {
            if(Optional.ofNullable(idSeccion).orElse(0) == 0){
                return null;
            }
            return QueryUtil.getObjectById(seccionRepository, idSeccion);
        };

        anecdota.setSeccion(addSeccion.apply(anecdotaRequestDTO.getIdSeccion()));
    }

    private void addEstadoAnecdota(Anecdota anecdota, TypeEstadoAnecdota typeEstadoAnecdota, Usuario usuario){
        EstadoAnecdota estadoAnecdota = estadoAnecdotaRepository
                .findEstadoAnecdotaByIdAnecdotaAndEstadoGestionado(anecdota.getId(), TypeSiNo.NO.name());
        if(Optional.ofNullable(estadoAnecdota).isPresent()){
            finalizeEstadoAnecdota(estadoAnecdota);
        }
        inicializeEstadoAnecdota(anecdota, typeEstadoAnecdota, usuario);
    }

    private void inicializeEstadoAnecdota(Anecdota anecdota, TypeEstadoAnecdota typeEstadoAnecdota, Usuario usuario){
        EstadoAnecdota estadoAnecdota = new EstadoAnecdota();
        estadoAnecdota.setEstado(typeEstadoAnecdota.name());
        estadoAnecdota.setUsuario(usuario);
        estadoAnecdota.setFechaHoraAsignacion(new Timestamp(new Date().getTime()));
        estadoAnecdota.setAnecdota(anecdota);
        estadoAnecdota.setGestionado(TypeSiNo.NO.name());
        estadoAnecdotaRepository.save(estadoAnecdota);
    }

    private void finalizeEstadoAnecdota(EstadoAnecdota estadoAnecdota){
        estadoAnecdota.setGestionado(TypeSiNo.SI.name());
        estadoAnecdota.setFechaHoraGestionado(new Timestamp(new Date().getTime()));
        estadoAnecdotaRepository.save(estadoAnecdota);
    }

    private void addAttachedAnecdota(Anecdota anecdota, List<MultipartFile> attachedFiles){
        attachedFiles.forEach(attached ->{
            try {
                Map responseCloudinary = cloudinaryComponent.upload(attached);
                Enlace enlace = new Enlace();
                enlace.setNombre(attached.getName());
                enlace.setIdPublica(responseCloudinary.get("public_id").toString());
                enlace.setUrl(responseCloudinary.get("url").toString());
                enlace.setAnecdota(anecdota);
                enlaceRepository.save(enlace);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
