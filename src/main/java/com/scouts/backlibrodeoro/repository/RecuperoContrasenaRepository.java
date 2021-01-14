package com.scouts.backlibrodeoro.repository;


import com.scouts.backlibrodeoro.model.RecuperoContrasena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecuperoContrasenaRepository extends JpaRepository<RecuperoContrasena, Integer> {

    @Query("SELECT rc FROM RecuperoContrasena rc WHERE rc.usuario.usuario = :usuario AND rc.estado = :estado ")
    RecuperoContrasena findRecuperoContrasenaUsuarioAndEstado(@Param("usuario") String usuario,
                                                              @Param("estado") String estado);

}
