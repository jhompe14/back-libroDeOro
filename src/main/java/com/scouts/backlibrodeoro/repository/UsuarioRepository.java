package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>  {

    @Query("SELECT usu FROM Usuario usu WHERE usu.usuario = :usuario AND usu.contrasena = :contrasena")
    Usuario findUsuarioByUsuarioAndContrasena(@Param("usuario") String usuario, @Param("contrasena") String contrasena);

    @Query("SELECT COUNT(1) FROM Usuario usu WHERE usu.usuario = :usuario")
    Integer countUsuarioByUsuario(@Param("usuario") String usuario);

    @Query("SELECT usu FROM Usuario usu WHERE usu.usuario = :usuario")
    Usuario findUsuarioByUsuario(@Param("usuario") String usuario);

    @Query("Select usu FROM Usuario usu WHERE usu.usuario = " +
            "(SELECT rc.usuario.usuario FROM RecuperoContrasena rc WHERE rc.codigo= :codigo AND rc.estado = :estado)")
    Usuario findUsuarioByRecoverd(@Param("codigo") String codigo, @Param("estado") String estado);
}
