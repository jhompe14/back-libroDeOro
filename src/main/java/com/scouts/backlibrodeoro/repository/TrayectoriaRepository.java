package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Trayectoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrayectoriaRepository extends JpaRepository<Trayectoria, Integer>, TrayectoriaRepositoryCustom {

    @Modifying
    @Query("DELETE FROM Trayectoria t where t.usuario.usuario=:usuario")
    void deleteTrayectoriasByUsuario(@Param("usuario") String usuario);

}
