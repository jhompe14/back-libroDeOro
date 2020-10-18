package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Trayectoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrayectoriaRepository extends JpaRepository<Trayectoria, Integer> {
}
