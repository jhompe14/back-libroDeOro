package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.AnecdotaDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.model.Seccion;
import com.scouts.backlibrodeoro.repository.RamaRepository;
import com.scouts.backlibrodeoro.repository.SeccionRepository;
import com.scouts.backlibrodeoro.repository.UsuarioRepository;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OptionalDataException;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AnecdotaService {

    private final UsuarioRepository usuarioRepository;
    private final RamaRepository ramaRepository;
    private final SeccionRepository seccionRepository;

    @Autowired
    public AnecdotaService(UsuarioRepository usuarioRepository, RamaRepository ramaRepository, SeccionRepository seccionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.ramaRepository = ramaRepository;
        this.seccionRepository = seccionRepository;
    }

    @Transactional
    public Anecdota createAnecdota(AnecdotaDTO anecdotaDTO) throws NegocioException {
        Anecdota anecdota = transformDTOToAnecdota(anecdotaDTO);
        return anecdota;
    }

    @FunctionalInterface
    public interface AddSeccion<T, R> {
        R apply(T t) throws NegocioException;
    }

    private Anecdota transformDTOToAnecdota(AnecdotaDTO anecdotaDTO) throws NegocioException {
        Anecdota anecdota = new Anecdota();
        anecdota.setNombre(anecdotaDTO.getNombre());
        anecdota.setFecha(anecdotaDTO.getFecha());
        anecdota.setDescripcion(anecdotaDTO.getDescripcion());
        anecdota.setUsuario(Optional.ofNullable(usuarioRepository.findUsuarioByUsuario(anecdotaDTO.getUsuario()))
                .orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_USUARIO_NO_EXISTE,
                        TypeException.VALIDATION)));
        anecdota.setRama(ramaRepository.findById(anecdotaDTO.getRama())
                .orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_RAMA_NO_EXISTE,
                TypeException.VALIDATION)));

        AddSeccion<Integer, Seccion> addSeccion = (idSeccion) -> {
            if(Optional.ofNullable(idSeccion).orElse(0) == 0){
                return null;
            }
            return seccionRepository.findById(idSeccion)
                    .orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_SECCION_NO_EXISTE,
                            TypeException.VALIDATION));
        };

        anecdota.setSeccion(addSeccion.apply(anecdotaDTO.getSeccion()));
        return anecdota;
    }

}
