package com.scouts.backlibrodeoro.service;

import com.scouts.backlibrodeoro.util.QueryUtil;
import com.scouts.backlibrodeoro.dto.request.AuthRequestDTO;
import com.scouts.backlibrodeoro.dto.request.ContrasenaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.TrayectoriaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.UsuarioRequestDTO;
import com.scouts.backlibrodeoro.dto.response.UsuarioResponseDTO;
import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.RecuperoContrasena;
import com.scouts.backlibrodeoro.model.Trayectoria;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.repository.*;
import com.scouts.backlibrodeoro.types.TypeActiveInactive;
import com.scouts.backlibrodeoro.types.TypeChangeContrasena;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.LibroOroUtil;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import com.scouts.backlibrodeoro.validator.impl.AuthValidator;
import com.scouts.backlibrodeoro.validator.impl.ContrasenaRequestDTOValidator;
import com.scouts.backlibrodeoro.validator.impl.TrayectoriaValidator;
import com.scouts.backlibrodeoro.validator.impl.UsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
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
    private final RecuperoContrasenaRepository recuperoContrasenaRepository;

    private final AuthValidator authValidator;
    private final UsuarioValidator usuarioValidator;
    private final TrayectoriaValidator trayectoriaValidator;
    private final ContrasenaRequestDTOValidator contrasenaRequestDTOValidator;

    @Value("${host.frontend}")
    private String hostFrontEnd;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, TrayectoriaRepository trayectoriaRepository,
            GrupoRepository grupoRepository, RamaRepository ramaRepository, SeccionRepository seccionRepository,
            CargoRepository cargoRepository, RecuperoContrasenaRepository recuperoContrasenaRepository,
            AuthValidator authValidator, UsuarioValidator usuarioValidator, TrayectoriaValidator trayectoriaValidator,
            ContrasenaRequestDTOValidator contrasenaRequestDTOValidator) {
        this.usuarioRepository = usuarioRepository;
        this.trayectoriaRepository = trayectoriaRepository;
        this.grupoRepository = grupoRepository;
        this.ramaRepository = ramaRepository;
        this.seccionRepository = seccionRepository;
        this.cargoRepository = cargoRepository;
        this.recuperoContrasenaRepository = recuperoContrasenaRepository;
        this.authValidator = authValidator;
        this.usuarioValidator = usuarioValidator;
        this.trayectoriaValidator = trayectoriaValidator;
        this.contrasenaRequestDTOValidator = contrasenaRequestDTOValidator;
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

    @Transactional(readOnly = true)
    public UsuarioResponseDTO getUsuario(String usuario) throws NegocioException {
        UsuarioResponseDTO usuarioResponseDTO =
                transformUsuarioToUsuarioResponseDTO(QueryUtil.getUsuarioByUsuario(usuarioRepository, usuario));
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
        Usuario usuario = QueryUtil.getUsuarioByUsuario(usuarioRepository, usuarioRequestDTO.getUsuario());

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
        trayectoria.setGrupo(QueryUtil.getObjectById(grupoRepository, trayectoriaRequestDTO.getGrupo()));
        trayectoria.setRama(QueryUtil.getObjectById(ramaRepository, trayectoriaRequestDTO.getRama()));
        trayectoria.setSeccion(QueryUtil.getObjectById(seccionRepository, trayectoriaRequestDTO.getSeccion()));
        trayectoria.setCargo(QueryUtil.getObjectById(cargoRepository, trayectoriaRequestDTO.getCargo()));
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

    @Transactional(readOnly = true)
    public Usuario getUsuarioByRecovered(String idRecovered) throws NegocioException {
        return Optional.ofNullable(usuarioRepository.findUsuarioByRecoverd(idRecovered, TypeActiveInactive.AC.toString()))
                .orElseThrow(() -> new NegocioException(MessagesValidation.CODIGO_RECOVERED_CONTRASENA_NO_VALIDO,
                        TypeException.VALIDATION));
    }

    @Transactional
    public void updateContrasena(String usuario, ContrasenaRequestDTO contrasenaRequestDTO) throws NegocioException {
        contrasenaRequestDTOValidator.validator(contrasenaRequestDTO);

        if(!contrasenaRequestDTO.getNewContrasena().equals(contrasenaRequestDTO.getConfirmContrasena())){
            throw new NegocioException(MessagesValidation.VALIDATION_CONFIRM_CONTRASENA, TypeException.VALIDATION);
        }

        Usuario usuarioToUpdate = QueryUtil.getUsuarioByUsuario(usuarioRepository, usuario);
        if(contrasenaRequestDTO.getTypeChangeContrasena().equals(TypeChangeContrasena.MO.toString())
                && !contrasenaRequestDTO.getActualContrasena().equals(usuarioToUpdate.getContrasena())){
            throw new NegocioException(MessagesValidation.VALIDATION_ACTUAL_CONTRASENA, TypeException.VALIDATION);
        }

        if(contrasenaRequestDTO.getTypeChangeContrasena().equals(TypeChangeContrasena.RC.toString())){
            inactivateEstadoRecuperoContrasena(usuario);
        }

        usuarioToUpdate.setContrasena(contrasenaRequestDTO.getNewContrasena());

        usuarioRepository.save(usuarioToUpdate);
    }

    @Transactional
    public RecuperoContrasena setRecoveredContrasena(String usuario) throws NegocioException, MessagingException {
        Usuario usuarioRecoveredContrasena = QueryUtil.getUsuarioByUsuario(usuarioRepository, usuario);
        inactivateEstadoRecuperoContrasena(usuario);

        RecuperoContrasena recuperoContrasena = new RecuperoContrasena();
        recuperoContrasena.setUsuario(usuarioRecoveredContrasena);
        recuperoContrasena.setEstado(TypeActiveInactive.AC.toString());
        recuperoContrasena.setCodigo(LibroOroUtil.randomCode(10));

        LibroOroUtil.sendEmail(usuarioRecoveredContrasena.getCorreo(),
                "Recupero Contrase\u00F1a", messageRecoveredContrasena(recuperoContrasena.getCodigo()));

        return recuperoContrasenaRepository.save(recuperoContrasena);
    }

    private void inactivateEstadoRecuperoContrasena(String usuario){
        RecuperoContrasena recuperoContrasena = recuperoContrasenaRepository
                .findRecuperoContrasenaUsuarioAndEstado(usuario, TypeActiveInactive.AC.toString());
        if (Optional.ofNullable(recuperoContrasena).isPresent()) {
            recuperoContrasena.setEstado(TypeActiveInactive.IN.toString());
            recuperoContrasenaRepository.save(recuperoContrasena);
        }
    }

    private String messageRecoveredContrasena(String codigo){
        return hostFrontEnd+"/contrasena/"+codigo;
    }

}
