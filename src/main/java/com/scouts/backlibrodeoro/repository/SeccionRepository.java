package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Integer> {

    @Query("SELECT s FROM Seccion s INNER JOIN s.rama r INNER JOIN r.grupo g ORDER BY g.nombre, r.nombre, s.nombre ASC")
    List<Seccion> findAllSecciones();

    @Query("SELECT Count(1) FROM Seccion s WHERE s.rama.id = :idRama")
    Integer countSeccionByRama(@Param("idRama") Integer idRama);

}
