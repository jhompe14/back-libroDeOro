package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Integer> {

    @Query("SELECT Count(1) FROM Seccion s WHERE s.rama.id = :idRama")
    Integer countSeccionByRama(@Param("idRama") Integer idRama);

}
