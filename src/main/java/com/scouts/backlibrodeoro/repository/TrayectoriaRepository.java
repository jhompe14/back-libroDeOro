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

    @Query("SELECT Count(1) FROM Trayectoria t WHERE t.grupo.id = :idGrupo")
    Integer countTrayectoriaByGrupo(@Param("idGrupo") Integer idGrupo);

    @Query("SELECT Count(1) FROM Trayectoria t WHERE t.rama.id = :idRama")
    Integer countTrayectoriaByRama(@Param("idRama") Integer idRama);

    @Query("SELECT Count(1) FROM Trayectoria t WHERE t.seccion.id = :idSeccion")
    Integer countTrayectoriaBySeccion(@Param("idSeccion") Integer idSeccion);

    @Query("SELECT Count(1) FROM Trayectoria t WHERE t.cargo.id = :idCargo")
    Integer countTrayectoriaByCargo(@Param("idCargo") Integer idCargo);

}
