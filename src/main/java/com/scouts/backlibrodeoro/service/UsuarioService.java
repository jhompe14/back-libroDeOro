package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.AuthDTO;
import com.scouts.backlibrodeoro.dto.TrayectoriaDTO;
import com.scouts.backlibrodeoro.dto.UsuarioDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Trayectoria;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.repository.*;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.AuthValidator;
import com.scouts.backlibrodeoro.validator.TrayectoriaValidator;
import com.scouts.backlibrodeoro.validator.UsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TrayectoriaRepository trayectoriaRepository;
    private final GrupoRepository grupoRepository;
    private final RamaRepository ramaRepository;
    private final SeccionRepository seccionRepository;
    private final CargoRepository cargoRepository;
    private final AuthValidator authValidator;
    private final UsuarioValidator usuarioValidator;
    private final TrayectoriaValidator trayectoriaValidator;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, TrayectoriaRepository trayectoriaRepository,
            GrupoRepository grupoRepository, RamaRepository ramaRepository, SeccionRepository seccionRepository,
            CargoRepository cargoRepository, AuthValidator authValidator, UsuarioValidator usuarioValidator,
            TrayectoriaValidator trayectoriaValidator) {
        this.usuarioRepository = usuarioRepository;
        this.trayectoriaRepository = trayectoriaRepository;
        this.grupoRepository = grupoRepository;
        this.ramaRepository = ramaRepository;
        this.seccionRepository = seccionRepository;
        this.cargoRepository = cargoRepository;
        this.authValidator = authValidator;
        this.usuarioValidator = usuarioValidator;
        this.trayectoriaValidator = trayectoriaValidator;
    }

    @Transactional(readOnly = true)
    public boolean authIsSuccess(AuthDTO auth) throws NegocioException {
        authValidator.validator(auth);
        Optional<Usuario> usuarioOptional = Optional.ofNullable(
                usuarioRepository.findUsuarioByUsuarioAndContrasena(auth.getUsuario(), auth.getContrasena()));
        if(!usuarioOptional.isPresent()) {
            throw new NegocioException(MessagesValidation.NOT_FOUND_USUARIO_CONTRASENA, TypeException.NOTFOUND);
        }else{
            auth.setTipoUsuario(usuarioOptional.orElse(new Usuario()).getTipoUsuario());
        }
        return true;
    }

    @Transactional
    public Usuario createUsuario(UsuarioDTO usuarioDTO) throws NegocioException {
        if(usuarioRepository.countUsuarioByUsuario(usuarioDTO.getUsuario())>0){
            throw new NegocioException(MessagesValidation.VALIDATION_USUARIO_EXISTE, TypeException.VALIDATION);
        }

        Usuario usuario = transformDTOToUsuario(usuarioDTO);
        usuarioValidator.validator(usuario);

        if(!usuarioDTO.getContrasena().equals(usuarioDTO.getConfirmContrasena())){
            throw new NegocioException(MessagesValidation.VALIDATION_CONFIRM_CONTRASENA, TypeException.VALIDATION);
        }

        return commitUsuario(usuario, getTrayectorias(usuario, usuarioDTO.getTrayectoria()));
    }

    private List<Trayectoria> getTrayectorias(Usuario usuario, List<TrayectoriaDTO> trayectoriaDTOList) throws NegocioException {
        List<Trayectoria> trayectorias = new ArrayList<>();
        if(Optional.ofNullable(trayectoriaDTOList)
                .map(trayectoriaDTOS -> trayectoriaDTOS.size()>0)
                .orElse(false)) {
            trayectoriaDTOList.stream().forEach(trayectoriaDTO -> {
                try {
                    Trayectoria trayectoria= transformDTOToTrayectoria(trayectoriaDTO);
                    trayectoriaValidator.validator(trayectoria);
                    trayectorias.add(trayectoria);
                } catch (Exception ex) {
                   throw new RuntimeException(ex);
                }
            });
        }else{
            throw new NegocioException(MessagesValidation.VALIDATION_TRAYECTORIA_OBLIGATORIO, TypeException.VALIDATION);
        }
        return trayectorias;
    }

    private Usuario commitUsuario(Usuario usuario, List<Trayectoria> trayectorias){
        usuarioRepository.save(usuario);
        trayectorias.forEach(trayectoria -> {
            trayectoria.setUsuario(usuario);
            trayectoriaRepository.save(trayectoria);
        });
        return usuario;
    }

    private Usuario transformDTOToUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();

        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setNombres(usuarioDTO.getNombres());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setTipoIntegrante(usuarioDTO.getTipoIntegrante());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setCiudad(usuarioDTO.getCiudad());
        usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());

        return usuario;
    }

    private Trayectoria transformDTOToTrayectoria(TrayectoriaDTO trayectoriaDTO) throws NegocioException {
        Trayectoria trayectoria = new Trayectoria();
        trayectoria.setGrupo(grupoRepository.findById(trayectoriaDTO.getGrupo())
                .orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_GRUPO_NO_EXISTE,
                        TypeException.VALIDATION)));
        trayectoria.setRama(ramaRepository.findById(trayectoriaDTO.getRama())
                .orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_RAMA_NO_EXISTE,
                        TypeException.VALIDATION)));
        trayectoria.setSeccion(seccionRepository.findById(trayectoriaDTO.getSeccion())
                .orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_SECCION_NO_EXISTE,
                        TypeException.VALIDATION)));
        trayectoria.setCargo(cargoRepository.findById(trayectoriaDTO.getCargo())
                .orElseThrow(() -> new NegocioException(MessagesValidation.ERROR_CARGO_NO_EXISTE,
                        TypeException.VALIDATION)));
        trayectoria.setAnioIngreso(trayectoriaDTO.getAnioIngreso());
        trayectoria.setAnioRetiro(trayectoriaDTO.getAnioRetiro());
        return trayectoria;
    }

}
