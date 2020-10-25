package com.scouts.backlibrodeoro.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "estados_anecdotas")
public class EstadoAnecdota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String estado;

    @Column(name="fecha_hora_asignacion")
    private Timestamp fechaHoraAsignacion;

    private String gestionado;

    @Column(name="fecha_hora_gestionado")
    private Timestamp fechaHoraGestionado;

    @ManyToOne
    @JoinColumn(name = "id_anecdota")
    private Anecdota anecdota;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaHoraAsignacion() {
        return fechaHoraAsignacion;
    }

    public void setFechaHoraAsignacion(Timestamp fechaHoraAsignacion) {
        this.fechaHoraAsignacion = fechaHoraAsignacion;
    }

    public String getGestionado() {
        return gestionado;
    }

    public void setGestionado(String gestionado) {
        this.gestionado = gestionado;
    }

    public Timestamp getFechaHoraGestionado() {
        return fechaHoraGestionado;
    }

    public void setFechaHoraGestionado(Timestamp fechaHoraGestionado) {
        this.fechaHoraGestionado = fechaHoraGestionado;
    }

    public Anecdota getAnecdota() {
        return anecdota;
    }

    public void setAnecdota(Anecdota anecdota) {
        this.anecdota = anecdota;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
