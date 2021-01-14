package com.scouts.backlibrodeoro.repository;

import com.scouts.backlibrodeoro.model.EstadoAnecdota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoAnecdotaRepository extends JpaRepository<EstadoAnecdota, Integer> {

    @Query("SELECT ea FROM EstadoAnecdota ea WHERE ea.anecdota.id = :idAnecdota AND ea.gestionado = :estadoGestionado")
    EstadoAnecdota findEstadoAnecdotaByIdAnecdotaAndEstadoGestionado(@Param("idAnecdota") Integer idAnecdota,
                                                                     @Param("estadoGestionado") String estadoGestionado);

    @Query("SELECT ea FROM EstadoAnecdota ea WHERE ea.id = (SELECT MAX(easub.id) FROM EstadoAnecdota easub " +
            "INNER JOIN easub.anecdota asub WHERE asub.id= :idAnecdota) ")
    EstadoAnecdota findEstadoAnecdotaActive(@Param("idAnecdota") Integer idAnecdota);

}
