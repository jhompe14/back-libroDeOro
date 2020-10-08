package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    @Query("SELECT c FROM Cargo c WHERE c.grupo.id = :idGrupo")
    List<Cargo> findCargoByTypeGrupo(@Param("idGrupo") Integer idGrupo);

    @Query("SELECT c FROM Cargo c WHERE c.rama.id = :idRama")
    List<Cargo> findCargoByTypeRama(@Param("idRama") Integer idRama);

    @Query("SELECT c FROM Cargo c WHERE c.seccion.id = :idSeccion")
    List<Cargo> findCargoByTypeSeccion(@Param("idSeccion") Integer idSeccion);

    @Query("SELECT COUNT(1) FROM Cargo c WHERE c.grupo.id = :idGrupo")
    Integer countCargoByTypeGrupo(@Param("idGrupo") Integer idGrupo);

    @Query("SELECT COUNT(1) FROM Cargo c WHERE c.rama.id = :idRama")
    Integer countCargoByTypeRama(@Param("idRama") Integer idRama);

    @Query("SELECT COUNT(1) FROM Cargo c WHERE c.seccion.id = :idSeccion")
    Integer countCargoByTypeSeccion(@Param("idSeccion") Integer idSeccion);

}
