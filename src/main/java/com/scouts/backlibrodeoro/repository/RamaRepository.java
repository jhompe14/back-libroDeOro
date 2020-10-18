package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Rama;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RamaRepository extends JpaRepository<Rama, Integer> {

    @Query("SELECT Count(1) FROM Rama r WHERE r.grupo.id = :idGrupo")
    Integer countRamaByGrupo(@Param("idGrupo") Integer idGrupo);
}
