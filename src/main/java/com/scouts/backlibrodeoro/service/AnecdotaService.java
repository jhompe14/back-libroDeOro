package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.AnecdotaDTO;
import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.model.EstadoAnecdota;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.repository.*;
import com.scouts.backlibrodeoro.types.TypeEstadoAnecdota;
import com.scouts.backlibrodeoro.types.TypeSiNo;
import com.scouts.backlibrodeoro.util.GeneralValidates;
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
    public List<Anecdota> getFilterAnecdota(FilterAnecdotaDTO filterAnecdotaDTO){
        Integer x= anecdotaRepository.hola();
        return null;
    }

    @Transactional
    public Anecdota createAnecdota(AnecdotaDTO anecdotaDTO) throws NegocioException {
        Anecdota anecdota = transformDTOToAnecdota(anecdotaDTO);
        anecdotaValidator.validator(anecdota);
        anecdotaRepository.save(anecdota);
        addEstadoAnecdota(anecdota, TypeEstadoAnecdota.PA, anecdota.getUsuario());
        return anecdota;
    }

    @FunctionalInterface
    public interface AddSeccion<T, R> {
        R apply(T t) throws NegocioException;
    }

    private Anecdota transformDTOToAnecdota(AnecdotaDTO anecdotaDTO) throws NegocioException {
        Anecdota anecdota = new Anecdota();
        anecdota.setNombre(anecdotaDTO.getNombre());
        anecdota.setFecha(GeneralValidates.validateFormatDate(anecdotaDTO.getFecha()));
        anecdota.setDescripcion(anecdotaDTO.getDescripcion());
        anecdota.setUsuario(InspeccionService.getUsuarioByUsuario(usuarioRepository, anecdotaDTO.getUsuario()));
        anecdota.setRama(InspeccionService.getObjectById(ramaRepository, anecdotaDTO.getRama()));

        AddSeccion<Integer, Seccion> addSeccion = (idSeccion) -> {
            if(Optional.ofNullable(idSeccion).orElse(0) == 0){
                return null;
            }
            return InspeccionService.getObjectById(seccionRepository, idSeccion);
        };

        anecdota.setSeccion(addSeccion.apply(anecdotaDTO.getSeccion()));
        return anecdota;
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
