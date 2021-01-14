package com.scouts.backlibrodeoro.dto.request;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.types.TypeEstadoAnecdota;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.types.TypeUsuario;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;

import java.util.Date;
import java.util.Optional;

public class FilterAnecdotaGridRequestDTO {

    private Integer idGrupo;

    private Integer idRama;

    private Integer idSeccion;

    private Date fechaInicioAnecdota;

    private Date fechaFinAnecdota;

    private String estado;

    private String usuarioFilter;

    private String usuarioOwner;

    private String typeUsuarioOwner;

    private Integer codigoAnecdota;

    private Integer page;

    public FilterAnecdotaGridRequestDTO(String idGrupo, String idRama, String idSeccion, String fechaInicioAnecdota,
                                        String fechaFinAnecdota, String estado, String usuarioFilter,
                                        String usuarioOwner, String typeUsuarioOwner, String codigoAnecdota,
                                        String page) {
        this.idGrupo = Optional.ofNullable(idGrupo).map(Integer::parseInt).orElse(null);
        this.idRama = Optional.ofNullable(idRama).map(Integer::parseInt).orElse(null);
        this.idSeccion = Optional.ofNullable(idSeccion).map(Integer::parseInt).orElse(null);
        this.fechaInicioAnecdota = GeneralValidates.validateFormatDate(fechaInicioAnecdota);
        this.fechaFinAnecdota = GeneralValidates.validateFormatDate(fechaFinAnecdota);
        this.estado = validateEstadoAnecdota(estado);
        this.usuarioFilter = usuarioFilter;
        this.usuarioOwner = usuarioOwner;
        this.typeUsuarioOwner = validateTypeUsuario(typeUsuarioOwner);
        this.codigoAnecdota = Optional.ofNullable(codigoAnecdota).map(Integer::parseInt).orElse(null);
        this.page = Integer.parseInt(page);
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public Integer getIdRama() {
        return idRama;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public Date getFechaInicioAnecdota() {
        return fechaInicioAnecdota;
    }

    public Date getFechaFinAnecdota() {
        return fechaFinAnecdota;
    }

    public String getEstado() {
        return estado;
    }

    public String getUsuarioFilter() {
        return usuarioFilter;
    }

    public String getUsuarioOwner() {
        return usuarioOwner;
    }

    public String getTypeUsuarioOwner() {
        return typeUsuarioOwner;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getCodigoAnecdota() {
        return codigoAnecdota;
    }

    private String validateEstadoAnecdota(String estado){
        return Optional.ofNullable(estado).map(e ->{
            try {
                TypeEstadoAnecdota.valueOf(e);
            }catch (IllegalArgumentException ex){
                try {
                    throw new NegocioException(MessagesValidation.VALIDATION_TIPO_ESTADO_ANECDOTA,
                            TypeException.VALIDATION);
                } catch (NegocioException negocioException) {
                    throw new RuntimeException(negocioException);
                }
            }
            return e;
        }).orElse(null);
    }

    private String validateTypeUsuario(String typeUsuario){
        return Optional.ofNullable(typeUsuario).map(tusu ->{
            try {
                TypeUsuario.valueOf(tusu);
            }catch (IllegalArgumentException ex){
                try {
                    throw new NegocioException(MessagesValidation.VALIDATION_TIPO_USUARIO_REQUIRED,
                            TypeException.VALIDATION);
                } catch (NegocioException negocioException) {
                    throw new RuntimeException(negocioException);
                }
            }
            return tusu;
        }).orElse(null);
    }
}
