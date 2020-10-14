package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>  {

    @Query("SELECT usu FROM Usuario usu WHERE usu.usuario = :usuario AND usu.contrasena = :contrasena")
    Usuario findUsuarioByUsuarioAndContrasena(@Param("usuario") String usuario, @Param("contrasena") String contrasena);

}
