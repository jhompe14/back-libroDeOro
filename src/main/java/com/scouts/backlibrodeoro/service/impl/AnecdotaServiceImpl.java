package com.scouts.backlibrodeoro.service.impl;

import com.scouts.backlibrodeoro.dto.request.*;
import com.scouts.backlibrodeoro.dto.response.CatalogAnecdotaResponseDTO;
import com.scouts.backlibrodeoro.external.CloudinaryComponent;
import com.scouts.backlibrodeoro.model.*;
import com.scouts.backlibrodeoro.notification.EstadoAnecdotaNotification;
import com.scouts.backlibrodeoro.service.AnecdotaService;
import com.scouts.backlibrodeoro.types.TypeEnlace;
import com.scouts.backlibrodeoro.util.LibroOroUtil;
import com.scouts.backlibrodeoro.util.QueryUtil;
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

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class AnecdotaServiceImpl implements AnecdotaService {

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
    public AnecdotaServiceImpl(UsuarioRepository usuarioRepository, RamaRepository ramaRepository,
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

    @Override
    @Transactional(readOnly = true)
    public Integer countFilterAnecdota(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO){
        return anecdotaRepository.countAnecdotasGrid(filterAnecdotaGridRequestDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnecdotaGridResponseDTO> getFilterAnecdota(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO){
        return anecdotaRepository.getAnecdotasGrid(filterAnecdotaGridRequestDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer countCatalogAnecdota(CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO){
        return anecdotaRepository.countCatalogAnecdota(catalogAnecdotaRequestDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatalogAnecdotaResponseDTO> getCatalogAnecdota(CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO){
        return anecdotaRepository.getCatalogAnecdota(catalogAnecdotaRequestDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public AnecdotaResponseDTO getAnecdota(Integer id) throws NegocioException {
        return anecdotaRepository.getAnecdotaById(id).orElseThrow(
                () -> new NegocioException(MessagesValidation.ERROR_ANECDOTA_NO_EXISTE, TypeException.VALIDATION));
    }

    @Override
    @Transactional
    public Anecdota createAnecdota(AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException, MessagingException {
        Anecdota anecdota = new Anecdota();
        transformDTOToAnecdota(anecdota, anecdotaRequestDTO);
        anecdotaValidator.validator(anecdota);
        anecdotaRepository.save(anecdota);
        addVideosAnecdota(anecdota, anecdotaRequestDTO.getVideosEnlace());
        addImagesAnecdota(anecdota, anecdotaRequestDTO.getAttachedFiles());
        addEstadoAnecdota(anecdota, TypeEstadoAnecdota.PA, anecdota.getUsuario());
        return anecdota;
    }

    @Override
    @Transactional
    public Anecdota updateAnecdota(Integer idAnecdota, AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException,
            MessagingException {
        Anecdota anecdota= anecdotaRepository.findById(idAnecdota).orElseThrow(
                () -> new NegocioException(MessagesValidation.ERROR_ANECDOTA_NO_EXISTE, TypeException.VALIDATION));
        transformDTOToAnecdota(anecdota, anecdotaRequestDTO);
        anecdotaValidator.validator(anecdota);
        anecdotaRepository.save(anecdota);
        addVideosAnecdota(anecdota, anecdotaRequestDTO.getVideosEnlace());
        addImagesAnecdota(anecdota, anecdotaRequestDTO.getAttachedFiles());

        if(estadoAnecdotaRepository.findEstadoAnecdotaActive(idAnecdota).getEstado()
                .equals(TypeEstadoAnecdota.PM.toString())) {
            addEstadoAnecdota(anecdota, TypeEstadoAnecdota.PA, anecdota.getUsuario());
        }

        return anecdota;
    }

    @Override
    @Transactional
    public Anecdota updateEstadoVisualizacionAnecdota(Integer idAnecdota, EstadoAnecdotaRequestDTO estadoAnecdotaRequestDTO)
            throws NegocioException, MessagingException {
       estadoAnecdotaRequestDTOValidator.validator(estadoAnecdotaRequestDTO);
       Anecdota anecdota = anecdotaRepository.findById(idAnecdota).orElseThrow(
               () -> new NegocioException(MessagesValidation.ERROR_ANECDOTA_NO_EXISTE, TypeException.VALIDATION));
       anecdota.setVisualizacion(estadoAnecdotaRequestDTO.getVisualizacion());
       anecdotaRepository.save(anecdota);

        if(Optional.ofNullable(estadoAnecdotaRequestDTO.getEstado()).isPresent() &&
                GeneralValidates.validateStringNotIsEmpty(estadoAnecdotaRequestDTO.getEstado())) {

            Usuario usuario = null;
            if(estadoAnecdotaRequestDTO.getEstado().equals(TypeEstadoAnecdota.PM.toString())){
                usuario = QueryUtil.getUsuarioByUsuario(usuarioRepository, estadoAnecdotaRequestDTO.getUsuarioModificacion());
            } else{
                usuario = QueryUtil.getUsuarioByUsuario(usuarioRepository, estadoAnecdotaRequestDTO.getUsuario());
            }

            addEstadoAnecdota(anecdota, TypeEstadoAnecdota.valueOf(estadoAnecdotaRequestDTO.getEstado()), usuario);
        }

       return anecdota;
    }

    @Transactional(readOnly = true)
    public void sendNotificationEstadoAnecdotaPM(Integer idAnecdota, String usuario){
        Usuario usuarioSolicitud = usuarioRepository.findUsuarioByUsuario(usuario);
        usuarioRepository.findUsuariosAdmin().forEach(usuarioAdmin -> {
            final String SUBJECT_EMAIL = "Solicitud de modificaci\u00F3n de an\u00E9cdota";
            final String CONTENT_EMAIL = "El usuario <b>"+usuarioSolicitud.getUsuario()+"</b> " +
                    "<br> Nombre: <b>"+usuarioSolicitud.getNombres()+" "+usuarioSolicitud.getApellidos()+"</b>" +
                    "<br> Correo: <b>"+usuarioSolicitud.getCorreo()+"</b>" +
                    "<br> acaba de solicitar el permiso de modificaci\u00F3n para la an\u00E9cdota con codigo <b>"+idAnecdota+"</b>  " +
                    "<br> Ingrese por favor al listado de an\u00E9cdotas para conceder el permiso o en su defecto comun\u00EDquese " +
                    "con el usuario para exponer las razones por el cual es permiso no ser\u00E1 concedido";
            try {
                LibroOroUtil.sendEmail(usuarioAdmin.getCorreo(), SUBJECT_EMAIL, CONTENT_EMAIL);
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    @Transactional
    public void deleteEnlaceAnecdota(Integer idEnlace) throws NegocioException {
        enlaceRepository.findById(idEnlace).map(enlace -> {
            try {
                if(enlace.getTipoEnlace().equals(TypeEnlace.IM.toString())){
                    Map result= cloudinaryComponent.delete(enlace.getIdPublica());
                    if(Optional.ofNullable(result).isPresent() && Optional.ofNullable(result.get("result")).isPresent()
                            && result.get("result").toString().equals("ok")){
                        enlaceRepository.delete(enlace);
                    }
                }else if(enlace.getTipoEnlace().equals(TypeEnlace.VI.toString())){
                    enlaceRepository.delete(enlace);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }).orElseThrow(() -> new NegocioException(MessagesValidation.VALIDATION_ATTACHED_ANECDOTA, TypeException.VALIDATION));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enlace> getEnlacesByIdAnecdota(Integer idAnecdota){
        return enlaceRepository.findEnlaceByIdAnecdota(idAnecdota);
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

    private void addEstadoAnecdota(Anecdota anecdota, TypeEstadoAnecdota typeEstadoAnecdota, Usuario usuario) throws MessagingException {
        EstadoAnecdota estadoAnecdota = estadoAnecdotaRepository
                .findEstadoAnecdotaByIdAnecdotaAndEstadoGestionado(anecdota.getId(), TypeSiNo.NO.name());
        if(Optional.ofNullable(estadoAnecdota).isPresent()){
            finalizeEstadoAnecdota(estadoAnecdota);
        }
        inicializeEstadoAnecdota(anecdota, typeEstadoAnecdota, usuario);
        new EstadoAnecdotaNotification(anecdota, typeEstadoAnecdota, usuario).run();
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

    private void addVideosAnecdota(Anecdota anecdota, List<VideoEnlaceRequestDTO> videosEnlace) throws NegocioException {
        if(errorFormatVideos(videosEnlace)){
            throw new NegocioException(MessagesValidation.VALIDATION_VIDEO_FORMAT_ANECDOTA, TypeException.VALIDATION);
        }

        videosEnlace.forEach(video -> {
            Enlace enlace = new Enlace();
            enlace.setNombre(video.getNombre());
            enlace.setUrl(video.getUrl());
            enlace.setTipoEnlace(TypeEnlace.VI.toString());
            enlace.setAnecdota(anecdota);
            enlaceRepository.save(enlace);
        });
    }

    private boolean errorFormatVideos(List<VideoEnlaceRequestDTO> videosEnlace) {
        return videosEnlace.stream()
                .map(video -> !video.getNombre().isEmpty() && !video.getUrl().isEmpty())
                .anyMatch(isCorrect -> !isCorrect);
    }

    private void addImagesAnecdota(Anecdota anecdota, List<MultipartFile> attachedFiles) throws NegocioException {
        if(errorFormatImages(attachedFiles)){
            throw new NegocioException(MessagesValidation.VALIDATION_ATTACHED_FORMAT_ANECDOTA, TypeException.VALIDATION);
        }

        attachedFiles.forEach(attached ->{
            try {
                Map responseCloudinary = cloudinaryComponent.upload(attached);
                Enlace enlace = new Enlace();
                enlace.setNombre(attached.getOriginalFilename());
                enlace.setIdPublica(responseCloudinary.get("public_id").toString());
                enlace.setUrl(responseCloudinary.get("url").toString());
                enlace.setTipoEnlace(TypeEnlace.IM.toString());
                enlace.setAnecdota(anecdota);
                enlaceRepository.save(enlace);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean errorFormatImages(List<MultipartFile> attachedFiles) {
        return attachedFiles.stream().map(attached -> {
            String fileName = attached.getOriginalFilename().toUpperCase();
            return fileName.endsWith(".JPG") || fileName.endsWith(".JPEG") || fileName.endsWith(".PNG");
        }).anyMatch(isCorrect -> !isCorrect);
    }

}
