package com.scouts.backlibrodeoro.dto.request;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.GeneralValidates;
import com.scouts.backlibrodeoro.util.MessagesValidation;

import java.util.Date;
import java.util.Optional;

public class CatalogAnecdotaRequestDTO {

    private String usuario;

    private Date fechaInicioAnecdota;

    private Date fechaFinAnecdota;

    private Integer page;

    public CatalogAnecdotaRequestDTO(String usuario, String fechaInicioAnecdota, String fechaFinAnecdota, String page)
            throws NegocioException {
        this.usuario = usuario;
        this.fechaInicioAnecdota = GeneralValidates.validateFormatDate(fechaInicioAnecdota);
        this.fechaFinAnecdota = GeneralValidates.validateFormatDate(fechaFinAnecdota);
        this.page = Integer.parseInt(page);
        validateRangeFechaInicioAndFechaFin();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Date getFechaInicioAnecdota() {
        return fechaInicioAnecdota;
    }

    public void setFechaInicioAnecdota(Date fechaInicioAnecdota) {
        this.fechaInicioAnecdota = fechaInicioAnecdota;
    }

    public Date getFechaFinAnecdota() {
        return fechaFinAnecdota;
    }

    public void setFechaFinAnecdota(Date fechaFinAnecdota) {
        this.fechaFinAnecdota = fechaFinAnecdota;
    }

    private void validateRangeFechaInicioAndFechaFin() throws NegocioException {
        if(Optional.ofNullable(this.fechaInicioAnecdota).isPresent() &&
                Optional.ofNullable(this.fechaFinAnecdota).isPresent() &&
                this.fechaInicioAnecdota.after(fechaFinAnecdota)){
            throw new NegocioException(MessagesValidation.VALIDATION_ANECDOTA_CATALOG_DATES,
                    TypeException.VALIDATION);
        }
    }
}
