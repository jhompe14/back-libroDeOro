package com.scouts.backlibrodeoro.dto.request;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;

import java.util.Optional;

public class VideoEnlaceRequestDTO {
    private final String SEPARATED = ";;;";

    private String nombre;
    private String url;

    public VideoEnlaceRequestDTO(String urlVideo) {
        if(urlVideo.contains(SEPARATED)){
            String [] infoVideoEnlace= urlVideo.split(SEPARATED);
            this.nombre = Optional.ofNullable(infoVideoEnlace[0]).orElse("");
            this.url = Optional.ofNullable(infoVideoEnlace[1]).orElse("");
        }else {
            this.nombre = "";
            this.url = "";
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

}
