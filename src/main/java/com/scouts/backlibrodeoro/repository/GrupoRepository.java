package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

    @Query("SELECT g FROM Grupo g ORDER BY g.nombre ASC")
    List<Grupo> findAllGrupos();

}
