package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.dto.request.AuthRequestDTO;
import com.scouts.backlibrodeoro.dto.request.ContrasenaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.TrayectoriaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.UsuarioRequestDTO;
import com.scouts.backlibrodeoro.dto.response.UsuarioResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Trayectoria;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.repository.*;
import com.scouts.backlibrodeoro.types.TypeChangeContrasena;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.AuthValidator;
import com.scouts.backlibrodeoro.validator.ContrasenaRequestDTOValidator;
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

    private final String CREATE_USUARIO = "CR";
    private final String UPDATE_USUARIO = "UP";
    private final String CONTRASENA_TEMPORAL = "123456";

    private final UsuarioRepository usuarioRepository;
    private final TrayectoriaRepository trayectoriaRepository;
    private final GrupoRepository grupoRepository;
    private final RamaRepository ramaRepository;
    private final SeccionRepository seccionRepository;
    private final CargoRepository cargoRepository;
    private final AuthValidator authValidator;
    private final UsuarioValidator usuarioValidator;
    private final TrayectoriaValidator trayectoriaValidator;
    private final ContrasenaRequestDTOValidator contrasenaRequestDTOValidator;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, TrayectoriaRepository trayectoriaRepository,
            GrupoRepository grupoRepository, RamaRepository ramaRepository, SeccionRepository seccionRepository,
            CargoRepository cargoRepository, AuthValidator authValidator, UsuarioValidator usuarioValidator,
            TrayectoriaValidator trayectoriaValidator, ContrasenaRequestDTOValidator contrasenaRequestDTOValidator) {
        this.usuarioRepository = usuarioRepository;
        this.trayectoriaRepository = trayectoriaRepository;
        this.grupoRepository = grupoRepository;
        this.ramaRepository = ramaRepository;
        this.seccionRepository = seccionRepository;
        this.cargoRepository = cargoRepository;
        this.authValidator = authValidator;
        this.usuarioValidator = usuarioValidator;
        this.trayectoriaValidator = trayectoriaValidator;
        this.contrasenaRequestDTOValidator = contrasenaRequestDTOValidator;
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO getUsuario(String usuario) throws NegocioException {
        UsuarioResponseDTO usuarioResponseDTO =
                transformUsuarioToUsuarioResponseDTO(InspeccionService.getUsuarioByUsuario(usuarioRepository, usuario));
        usuarioResponseDTO.setTrayectoria(trayectoriaRepository.findTrayectoriaResponseDTOByUsuario(usuario));
        return usuarioResponseDTO;
    }

    public Boolean validateUsuario(UsuarioRequestDTO usuarioRequestDTO) throws NegocioException {
        Usuario usuario = transformDTOToUsuario(usuarioRequestDTO, new Usuario(), UPDATE_USUARIO);
        usuario.setContrasena(CONTRASENA_TEMPORAL);
        usuarioValidator.validator(usuario);
        return Boolean.TRUE;
    }

    public Boolean validateTrayectoria(TrayectoriaRequestDTO trayectoriaRequestDTO) throws NegocioException {
        trayectoriaValidator.validator(transformDTOToTrayectoria(trayectoriaRequestDTO));
        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    public boolean authIsSuccess(AuthRequestDTO auth) throws NegocioException {
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
    public Usuario createUsuario(UsuarioRequestDTO usuarioRequestDTO) throws NegocioException {
        if(usuarioRepository.countUsuarioByUsuario(usuarioRequestDTO.getUsuario())>0){
            throw new NegocioException(MessagesValidation.VALIDATION_USUARIO_EXISTE, TypeException.VALIDATION);
        }

        Usuario usuario = transformDTOToUsuario(usuarioRequestDTO, new Usuario(), CREATE_USUARIO);
        usuarioValidator.validator(usuario);

        if(!usuarioRequestDTO.getContrasena().equals(usuarioRequestDTO.getConfirmContrasena())){
            throw new NegocioException(MessagesValidation.VALIDATION_CONFIRM_CONTRASENA, TypeException.VALIDATION);
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUsuario(UsuarioRequestDTO usuarioRequestDTO) throws NegocioException{
        Usuario usuario = InspeccionService.getUsuarioByUsuario(usuarioRepository, usuarioRequestDTO.getUsuario());

        transformDTOToUsuario(usuarioRequestDTO, usuario, UPDATE_USUARIO);
        usuarioValidator.validator(usuario);

        return commitUpdateUsuario(usuario, getTrayectorias(usuarioRequestDTO.getTrayectoria()));
    }

    private List<Trayectoria> getTrayectorias(List<TrayectoriaRequestDTO> trayectoriaRequestDTOList) {
        List<Trayectoria> trayectorias = new ArrayList<>();
        if(Optional.ofNullable(trayectoriaRequestDTOList)
                .map(trayectoriaDTOS -> trayectoriaDTOS.size()>0)
                .orElse(false)) {
            trayectoriaRequestDTOList.stream().forEach(trayectoriaRequestDTO -> {
                try {
                    Trayectoria trayectoria= transformDTOToTrayectoria(trayectoriaRequestDTO);
                    trayectoriaValidator.validator(trayectoria);
                    trayectorias.add(trayectoria);
                } catch (Exception ex) {
                   throw new RuntimeException(ex);
                }
            });
        }

        return trayectorias;
    }

    private Usuario commitUpdateUsuario(Usuario usuario, List<Trayectoria> trayectorias){
        trayectoriaRepository.deleteTrayectoriasByUsuario(usuario.getUsuario());
        usuarioRepository.save(usuario);
        trayectorias.forEach(trayectoria -> {
            trayectoria.setUsuario(usuario);
            trayectoriaRepository.save(trayectoria);
        });
        return usuario;
    }

    private Usuario transformDTOToUsuario(UsuarioRequestDTO usuarioRequestDTO, Usuario usuario, String typeTransaction){
        usuario.setUsuario(usuarioRequestDTO.getUsuario());
        usuario.setNombres(usuarioRequestDTO.getNombres());
        usuario.setApellidos(usuarioRequestDTO.getApellidos());
        usuario.setTipoIntegrante(usuarioRequestDTO.getTipoIntegrante());
        usuario.setCorreo(usuarioRequestDTO.getCorreo());
        usuario.setTelefono(usuarioRequestDTO.getTelefono());
        usuario.setDireccion(usuarioRequestDTO.getDireccion());
        usuario.setCiudad(usuarioRequestDTO.getCiudad());
        usuario.setTipoUsuario(usuarioRequestDTO.getTipoUsuario());
        if(typeTransaction.equals(CREATE_USUARIO))
            usuario.setContrasena(usuarioRequestDTO.getContrasena());

        return usuario;
    }

    private Trayectoria transformDTOToTrayectoria(TrayectoriaRequestDTO trayectoriaRequestDTO) throws NegocioException {
        Trayectoria trayectoria = new Trayectoria();
        trayectoria.setGrupo(InspeccionService.getObjectById(grupoRepository, trayectoriaRequestDTO.getGrupo()));
        trayectoria.setRama(InspeccionService.getObjectById(ramaRepository, trayectoriaRequestDTO.getRama()));
        trayectoria.setSeccion(InspeccionService.getObjectById(seccionRepository, trayectoriaRequestDTO.getSeccion()));
        trayectoria.setCargo(InspeccionService.getObjectById(cargoRepository, trayectoriaRequestDTO.getCargo()));
        trayectoria.setAnioIngreso(trayectoriaRequestDTO.getAnioIngreso());
        trayectoria.setAnioRetiro(trayectoriaRequestDTO.getAnioRetiro());
        return trayectoria;
    }

    private UsuarioResponseDTO transformUsuarioToUsuarioResponseDTO(Usuario usuario){
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

        usuarioResponseDTO.setUsuario(usuario.getUsuario());
        usuarioResponseDTO.setNombres(usuario.getNombres());
        usuarioResponseDTO.setApellidos(usuario.getApellidos());
        usuarioResponseDTO.setTipoIntegrante(usuario.getTipoIntegrante());
        usuarioResponseDTO.setCorreo(usuario.getCorreo());
        usuarioResponseDTO.setTelefono(usuario.getTelefono());
        usuarioResponseDTO.setDireccion(usuario.getDireccion());
        usuarioResponseDTO.setCiudad(usuario.getCiudad());
        usuarioResponseDTO.setTipoUsuario(usuario.getTipoUsuario());

        return usuarioResponseDTO;
    }

    @Transactional
    public void updateContrasena(String usuario, ContrasenaRequestDTO contrasenaRequestDTO) throws NegocioException {
        contrasenaRequestDTOValidator.validator(contrasenaRequestDTO);

        if(!contrasenaRequestDTO.getNewContrasena().equals(contrasenaRequestDTO.getConfirmContrasena())){
            throw new NegocioException(MessagesValidation.VALIDATION_CONFIRM_CONTRASENA, TypeException.VALIDATION);
        }

        Usuario usuarioToUpdate = InspeccionService.getUsuarioByUsuario(usuarioRepository, usuario);
        if(contrasenaRequestDTO.getTypeChangeContrasena().equals(TypeChangeContrasena.MO.toString())
                && !contrasenaRequestDTO.getActualContrasena().equals(usuarioToUpdate.getContrasena())){
            throw new NegocioException(MessagesValidation.VALIDATION_ACTUAL_CONTRASENA, TypeException.VALIDATION);
        }

        usuarioToUpdate.setContrasena(contrasenaRequestDTO.getNewContrasena());

        usuarioRepository.save(usuarioToUpdate);
    }


}
