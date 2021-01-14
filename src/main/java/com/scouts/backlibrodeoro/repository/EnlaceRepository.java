package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Enlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnlaceRepository extends JpaRepository<Enlace, Integer> {

    @Query("SELECT e FROM Enlace e WHERE e.idPublica = :idPublica")
    Enlace findEnlaceByIdPublica(@Param("idPublica") String idPublica);

    @Query("SELECT e FROM Enlace e WHERE e.anecdota.id = :idAnecdota ")
    List<Enlace> findEnlaceByIdAnecdota(@Param("idAnecdota") Integer idAnecdota);


}
