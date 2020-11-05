package com.scouts.backlibrodeoro.repository.impl;

import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.repository.AnecdotaRepositoryCustom;
import com.scouts.backlibrodeoro.types.TypeUsuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class AnecdotaRepositoryCustomImpl implements AnecdotaRepositoryCustom {

    private final String SQL_ESTADO_ANECDOTA = "(SELECT MAX(easub.id) FROM EstadoAnecdota easub " +
            "INNER JOIN easub.anecdota asub " +
            "WHERE asub.id= a.id) ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnecdotaGridResponseDTO> getAnecdotasGrid(FilterAnecdotaRequestDTO filterAnecdotaRequestDTO) {


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

        if(filterAnecdotaRequestDTO.getTypeUsuarioOwner().equals(TypeUsuario.IN.toString()))
            filterUsuarioSQL(sql, filterAnecdotaRequestDTO.getUsuarioOwner());

        filterSQL(sql, filterAnecdotaRequestDTO);
        Query q = entityManager.createQuery(sql.toString());
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

    private void filterSQL(StringBuilder sql, FilterAnecdotaRequestDTO filterAnecdotaRequestDTO){
        if(filterPresent(filterAnecdotaRequestDTO.getIdGrupo()))
            sql.append("AND g.id = "+filterAnecdotaRequestDTO.getIdGrupo()+" ");
        if(filterPresent(filterAnecdotaRequestDTO.getIdRama()))
            sql.append("AND r.id = "+filterAnecdotaRequestDTO.getIdRama()+" ");
        if(filterPresent(filterAnecdotaRequestDTO.getIdSeccion()))
            sql.append("AND s.id = "+filterAnecdotaRequestDTO.getIdSeccion()+" ");
        if(filterPresent(filterAnecdotaRequestDTO.getFechaInicioAnecdota()) &&
            filterPresent(filterAnecdotaRequestDTO.getFechaFinAnecdota()))
            sql.append("AND a.fecha BETWEEN '"+filterAnecdotaRequestDTO.getFechaInicioAnecdota()+"' " +
                    "AND '"+filterAnecdotaRequestDTO.getFechaFinAnecdota()+"' ");
        if(filterPresent(filterAnecdotaRequestDTO.getEstado()))
            sql.append("AND a.estado = '"+filterAnecdotaRequestDTO.getEstado().toString()+"' ");
        if(filterPresent(filterAnecdotaRequestDTO.getUsuarioFilter()))
            filterUsuarioSQL(sql, filterAnecdotaRequestDTO.getUsuarioFilter());
    }

    private <T> Boolean filterPresent(T filter){
        return Optional.ofNullable(filter).isPresent();
    }
}
