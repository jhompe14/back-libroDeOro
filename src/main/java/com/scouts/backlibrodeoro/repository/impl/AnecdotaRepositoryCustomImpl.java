package com.scouts.backlibrodeoro.repository.impl;

import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.repository.AnecdotaRepositoryCustom;
import com.scouts.backlibrodeoro.types.TypeUsuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class AnecdotaRepositoryCustomImpl implements AnecdotaRepositoryCustom {

    private final Integer PAGE_SIZE= 4;

    private final String SQL_ESTADO_ANECDOTA = "(SELECT MAX(easub.id) FROM EstadoAnecdota easub " +
            "INNER JOIN easub.anecdota asub " +
            "WHERE asub.id= a.id) ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer countAnecdotasGrid(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) " +
                "FROM Anecdota a " +
                "INNER JOIN a.rama r " +
                "INNER JOIN r.grupo g " +
                "LEFT JOIN a.seccion s " +
                "INNER JOIN a.usuario u " +
                "WHERE 1=1 ");

        if(filterAnecdotaGridRequestDTO.getTypeUsuarioOwner().equals(TypeUsuario.IN.toString()))
            filterUsuarioSQL(sql, filterAnecdotaGridRequestDTO.getUsuarioOwner());

        filterSQL(sql, filterAnecdotaGridRequestDTO);
        Query q = entityManager.createQuery(sql.toString());
        return q.getSingleResult() != null ? Integer.parseInt(q.getSingleResult().toString()) : 0;
    }

    @Override
    public List<AnecdotaGridResponseDTO> getAnecdotasGrid(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO) {


        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO" +
                "(a.id, g.nombre, r.nombre, s.nombre, a.fecha, u.usuario, " +
                "(SELECT eaestado.estado FROM EstadoAnecdota eaestado WHERE eaestado.id = "+SQL_ESTADO_ANECDOTA+")," +
                "(SELECT usuea.usuario FROM EstadoAnecdota eausuario " +
                        "INNER JOIN eausuario.usuario usuea WHERE eausuario.id = "+SQL_ESTADO_ANECDOTA+")) " +
                "FROM Anecdota a " +
                "INNER JOIN a.rama r " +
                "INNER JOIN r.grupo g " +
                "LEFT JOIN a.seccion s " +
                "INNER JOIN a.usuario u " +
                "WHERE 1=1 ");

        if(filterAnecdotaGridRequestDTO.getTypeUsuarioOwner().equals(TypeUsuario.IN.toString()))
            filterUsuarioSQL(sql, filterAnecdotaGridRequestDTO.getUsuarioOwner());

        filterSQL(sql, filterAnecdotaGridRequestDTO);
        Query q = entityManager.createQuery(sql.toString());
        q.setFirstResult((filterAnecdotaGridRequestDTO.getPage()-1) * PAGE_SIZE);
        q.setMaxResults(PAGE_SIZE);
        return q.getResultList();
    }

    private void filterUsuarioSQL(StringBuilder sql, String usuario){
        sql.append("AND (u.usuario='"+usuario+"' " +
                "      OR " +
                "      a.id IN(SELECT ea_eaa.id FROM EstadoAnecdota ea_ea " +
                "                INNER JOIN ea_ea.usuario ea_eausu " +
                "                INNER JOIN ea_ea.anecdota ea_eaa " +
                "                INNER JOIN ea_eaa.usuario eaa_usu " +
                "                WHERE eaa_usu.usuario != '"+usuario+"' " +
                "                AND ea_eausu.usuario = '"+usuario+"' " +
                "                AND ea_ea.id= "+SQL_ESTADO_ANECDOTA+"))");
    }

    private void filterSQL(StringBuilder sql, FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO){
        if(filterPresent(filterAnecdotaGridRequestDTO.getIdGrupo()))
            sql.append("AND g.id = "+ filterAnecdotaGridRequestDTO.getIdGrupo()+" ");
        if(filterPresent(filterAnecdotaGridRequestDTO.getIdRama()))
            sql.append("AND r.id = "+ filterAnecdotaGridRequestDTO.getIdRama()+" ");
        if(filterPresent(filterAnecdotaGridRequestDTO.getIdSeccion()))
            sql.append("AND s.id = "+ filterAnecdotaGridRequestDTO.getIdSeccion()+" ");
        if(filterPresent(filterAnecdotaGridRequestDTO.getFechaInicioAnecdota()) &&
            filterPresent(filterAnecdotaGridRequestDTO.getFechaFinAnecdota()))
            sql.append("AND a.fecha BETWEEN '"+ filterAnecdotaGridRequestDTO.getFechaInicioAnecdota()+"' " +
                    "AND '"+ filterAnecdotaGridRequestDTO.getFechaFinAnecdota()+"' ");
        if(filterPresent(filterAnecdotaGridRequestDTO.getEstado()))
            sql.append("AND a.estado = '"+ filterAnecdotaGridRequestDTO.getEstado().toString()+"' ");
        if(filterPresent(filterAnecdotaGridRequestDTO.getUsuarioFilter()))
            filterUsuarioSQL(sql, filterAnecdotaGridRequestDTO.getUsuarioFilter());
    }

    private <T> Boolean filterPresent(T filter){
        return Optional.ofNullable(filter).isPresent();
    }
}
