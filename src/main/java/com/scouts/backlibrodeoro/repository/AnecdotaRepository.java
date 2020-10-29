package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Anecdota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnecdotaRepository extends JpaRepository<Anecdota, Integer> {

    default Integer hola(){
        return 1;
    }

}
