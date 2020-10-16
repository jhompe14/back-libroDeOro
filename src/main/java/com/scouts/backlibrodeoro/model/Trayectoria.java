package com.scouts.backlibrodeoro.model;

import javax.persistence.*;

@Entity
@Table(name = "trayectorias")
public class Trayectoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_rama")
    private Rama rama;

    @ManyToOne
    @JoinColumn(name = "id_seccion")
    private Seccion seccion;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;

    @Column(name="anio_ingreso")
    private Integer anioIngreso;

    @Column(name="anio_retiro")
    private Integer anioRetiro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Rama getRama() {
        return rama;
    }

    public void setRama(Rama rama) {
        this.rama = rama;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Integer getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Integer anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public Integer getAnioRetiro() {
        return anioRetiro;
    }

    public void setAnioRetiro(Integer anioRetiro) {
        this.anioRetiro = anioRetiro;
    }
}
