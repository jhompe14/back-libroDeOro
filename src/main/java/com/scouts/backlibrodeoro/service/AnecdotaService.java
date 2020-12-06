package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.AnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.EstadoAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.model.EstadoAnecdota;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.repository.*;
import com.scouts.backlibrodeoro.types.TypeEstadoAnecdota;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.types.TypeSiNo;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.AnecdotaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnecdotaService {

    private final UsuarioRepository usuarioRepository;
    private final RamaRepository ramaRepository;
    private final SeccionRepository seccionRepository;
    private final AnecdotaRepository anecdotaRepository;
    private final EstadoAnecdotaRepository estadoAnecdotaRepository;
    private final AnecdotaValidator anecdotaValidator;

    @Autowired
    public AnecdotaService(UsuarioRepository usuarioRepository, RamaRepository ramaRepository,
                           SeccionRepository seccionRepository, AnecdotaRepository anecdotaRepository,
                           EstadoAnecdotaRepository estadoAnecdotaRepository, AnecdotaValidator anecdotaValidator) {
        this.usuarioRepository = usuarioRepository;
        this.ramaRepository = ramaRepository;
        this.seccionRepository = seccionRepository;
        this.anecdotaRepository = anecdotaRepository;
        this.estadoAnecdotaRepository = estadoAnecdotaRepository;
        this.anecdotaValidator = anecdotaValidator;
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
        return anecdota;
    }

    @Transactional
    public Anecdota updateAnecdota(Integer idAnecdota, AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException{
        Anecdota anecdota= anecdotaRepository.findById(idAnecdota).orElseThrow(
                () -> new NegocioException(MessagesValidation.ERROR_ANECDOTA_NO_EXISTE, TypeException.VALIDATION));
        transformDTOToAnecdota(anecdota, anecdotaRequestDTO);
        anecdotaValidator.validator(anecdota);
        return anecdotaRepository.save(anecdota);
    }

    @Transactional
    public Anecdota updateEstadoAnecdota(Integer idAnecdota, EstadoAnecdotaRequestDTO estadoAnecdotaRequestDTO) throws NegocioException{
        return null;
    }

    @FunctionalInterface
    public interface AddSeccion<T, R> {
        R apply(T t) throws NegocioException;
    }

    private void transformDTOToAnecdota(Anecdota anecdota, AnecdotaRequestDTO anecdotaRequestDTO) throws NegocioException {
        anecdota.setNombre(anecdotaRequestDTO.getNombre());
        anecdota.setFecha(GeneralValidates.validateFormatDate(anecdotaRequestDTO.getFecha()));
        anecdota.setDescripcion(anecdotaRequestDTO.getDescripcion());
        anecdota.setUsuario(InspeccionService.getUsuarioByUsuario(usuarioRepository, anecdotaRequestDTO.getUsuario()));
        anecdota.setRama(InspeccionService.getObjectById(ramaRepository, anecdotaRequestDTO.getIdRama()));

        AddSeccion<Integer, Seccion> addSeccion = (idSeccion) -> {
            if(Optional.ofNullable(idSeccion).orElse(0) == 0){
                return null;
            }
            return InspeccionService.getObjectById(seccionRepository, idSeccion);
        };

        anecdota.setSeccion(addSeccion.apply(anecdotaRequestDTO.getIdSeccion()));
    }

    private void addEstadoAnecdota(Anecdota anecdota, TypeEstadoAnecdota typeEstadoAnecdota, Usuario usuario){
        EstadoAnecdota estadoAnecdota = estadoAnecdotaRepository
                .findEstadoAnecdotaByIdAnecdotaAndEstadoGestionado(anecdota.getId(), TypeSiNo.NO.name());
        if(Optional.ofNullable(estadoAnecdota).isPresent()){
            finalizeEstadoAnecdota(estadoAnecdota);
        }
        createEstadoAnecdota(anecdota, typeEstadoAnecdota, usuario);
    }

    private void createEstadoAnecdota(Anecdota anecdota, TypeEstadoAnecdota typeEstadoAnecdota, Usuario usuario){
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

}
