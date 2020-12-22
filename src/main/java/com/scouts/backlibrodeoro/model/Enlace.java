package com.scouts.backlibrodeoro.model;

import javax.persistence.*;

@Entity
@Table(name = "enlaces")
public class Enlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true)
    private String url;

    @Column(name="id_publica", unique=true)
    private String idPublica;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_anecdota")
    private Anecdota anecdota;

    public Enlace() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdPublica() {
        return idPublica;
    }

    public void setIdPublica(String idPublica) {
        this.idPublica = idPublica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Anecdota getAnecdota() {
        return anecdota;
    }

    public void setAnecdota(Anecdota anecdota) {
        this.anecdota = anecdota;
    }
}
