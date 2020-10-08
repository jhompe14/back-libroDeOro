package com.scouts.backlibrodeoro.repository;


import com.scouts.backlibrodeoro.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
}
