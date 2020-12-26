package com.scouts.backlibrodeoro.dto.request;

import com.scouts.backlibrodeoro.util.GeneralValidates;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnecdotaRequestDTO {

    private String nombre;

    private String fecha;

    private String descripcion;

    private String usuario;

    private Integer idRama;

    private Integer idSeccion;

    private List<MultipartFile> attachedFiles;

    private List<VideoEnlaceRequestDTO> videosEnlace;

    public AnecdotaRequestDTO(String nombre, String fecha, String descripcion, String usuario, String idRama,
                              String idSeccion, List<MultipartFile> attachedFiles, List<String> videosEnlace) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.idRama = Optional.ofNullable(idRama).map(GeneralValidates::validateIntegerType).orElse(0);
        this.idSeccion = Optional.ofNullable(idSeccion).map(GeneralValidates::validateIntegerType).orElse(0);
        this.attachedFiles = attachedFiles;
        this.videosEnlace = videosEnlace.stream()
                            .map(VideoEnlaceRequestDTO::new)
                            .collect(Collectors.toList());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getIdRama() {
        return idRama;
    }

    public void setIdRama(Integer idRama) {
        this.idRama = idRama;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public List<MultipartFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<MultipartFile> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public List<VideoEnlaceRequestDTO> getVideosEnlace() {
        return videosEnlace;
    }

    public void setVideosEnlace(List<VideoEnlaceRequestDTO> videosEnlace) {
        this.videosEnlace = videosEnlace;
    }
}
