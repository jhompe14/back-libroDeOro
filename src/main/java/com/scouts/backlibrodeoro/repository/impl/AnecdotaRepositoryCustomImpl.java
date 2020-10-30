package com.scouts.backlibrodeoro.repository.impl;

import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridDTO;
import com.scouts.backlibrodeoro.repository.AnecdotaRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class AnecdotaRepositoryCustomImpl implements AnecdotaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnecdotaGridDTO> getAnecdotasGrid(FilterAnecdotaDTO filterAnecdotaDTO) {
        final String SQL_ESTADO_ANECDOTA = "(SELECT MAX(easub.id) FROM EstadoAnecdota easub " +
                "INNER JOIN easub.anecdota asub " +
                "WHERE asub.id= a.id) ";

        String sql = "SELECT new com.scouts.backlibrodeoro.dto.response.AnecdotaGridDTO" +
                "(a.id, g.nombre, r.nombre, s.nombre, a.fecha, u.usuario, " +
                "(SELECT usuea.usuario FROM EstadoAnecdota eausuario " +
                    "INNER JOIN eausuario.usuario usuea WHERE eausuario.id = "+SQL_ESTADO_ANECDOTA+")," +
                "(SELECT eaestado.estado FROM EstadoAnecdota eaestado WHERE eaestado.id = "+SQL_ESTADO_ANECDOTA+")) " +
                "FROM Anecdota a " +
                "INNER JOIN a.rama r " +
                "INNER JOIN r.grupo g " +
                "LEFT JOIN a.seccion s " +
                "INNER JOIN a.usuario u ";

        Query q = entityManager.createQuery(sql);

        return q.getResultList();
    }
}
