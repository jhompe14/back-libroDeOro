package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnecdotaRepository extends JpaRepository<Anecdota, Integer>, AnecdotaRepositoryCustom {

}
