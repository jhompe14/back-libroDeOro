package com.scouts.backlibrodeoro.repository.impl;

import com.scouts.backlibrodeoro.dto.request.CatalogAnecdotaRequestDTO;
import com.scouts.backlibrodeoro.dto.request.FilterAnecdotaGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO;
import com.scouts.backlibrodeoro.dto.response.AnecdotaResponseDTO;
import com.scouts.backlibrodeoro.dto.response.CatalogAnecdotaResponseDTO;
import com.scouts.backlibrodeoro.repository.AnecdotaRepositoryCustom;
import com.scouts.backlibrodeoro.types.TypeEstadoAnecdota;
import com.scouts.backlibrodeoro.types.TypeUsuario;
import com.scouts.backlibrodeoro.types.TypeVisualizacion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class AnecdotaRepositoryCustomImpl implements AnecdotaRepositoryCustom {

    private final Integer PAGE_SIZE_GRID= 6;
    private final Integer PAGE_SIZE_CATALOG = 8;

    private final String SQL_ESTADO_ANECDOTA = "(SELECT MAX(easub.id) FROM EstadoAnecdota easub " +
            "INNER JOIN easub.anecdota asub " +
            "WHERE asub.id= a.id) ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AnecdotaResponseDTO> getAnecdotaById(Integer idAnecdota) {
        String sql = "SELECT new com.scouts.backlibrodeoro.dto.response.AnecdotaResponseDTO" +
                "(a.id, g.id, g.nombre, r.id, r.nombre, s.id, s.nombre, a.nombre, a.fecha, u.usuario, a.descripcion,  " +
                "(SELECT eaestado.estado FROM EstadoAnecdota eaestado WHERE eaestado.id = "+SQL_ESTADO_ANECDOTA+"), " +
                "a.visualizacion) " +
                "FROM Anecdota a " +
                "INNER JOIN a.rama r " +
                "INNER JOIN r.grupo g " +
                "LEFT JOIN a.seccion s " +
                "INNER JOIN a.usuario u " +
                "WHERE a.id= "+idAnecdota+" ";
        Query q = entityManager.createQuery(sql);
        return q.getResultList().stream().findFirst();
    }

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

        filterGridSQL(sql, filterAnecdotaGridRequestDTO);
        Query q = entityManager.createQuery(sql.toString());
        return q.getSingleResult() != null ? Integer.parseInt(q.getSingleResult().toString()) : 0;
    }

    @Override
    public List<AnecdotaGridResponseDTO> getAnecdotasGrid(FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new com.scouts.backlibrodeoro.dto.response.AnecdotaGridResponseDTO" +
                "(a.id, g.nombre, r.nombre, s.nombre, a.nombre, a.fecha, u.usuario, " +
                "(SELECT eaestado.estado FROM EstadoAnecdota eaestado WHERE eaestado.id = "+SQL_ESTADO_ANECDOTA+")," +
                "(SELECT usuea.usuario FROM EstadoAnecdota eausuario " +
                        "INNER JOIN eausuario.usuario usuea WHERE eausuario.id = "+SQL_ESTADO_ANECDOTA+")) " +
                "FROM Anecdota a " +
                "INNER JOIN a.rama r " +
                "INNER JOIN r.grupo g " +
                "LEFT JOIN a.seccion s " +
                "INNER JOIN a.usuario u " +
                "WHERE 1=1 ");

        filterGridSQL(sql, filterAnecdotaGridRequestDTO);
        sql.append("ORDER BY 1 DESC ");
        Query q = entityManager.createQuery(sql.toString());
        q.setFirstResult((filterAnecdotaGridRequestDTO.getPage()-1) * PAGE_SIZE_GRID);
        q.setMaxResults(PAGE_SIZE_GRID);
        return q.getResultList();
    }

    @Override
    public Integer countCatalogAnecdota(CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) " +
                "FROM Anecdota a " +
                "INNER JOIN a.rama r " +
                "LEFT JOIN a.seccion s " +
                "INNER JOIN a.usuario u " +
                "WHERE 1=1 ");

        filterCatalogSQL(sql, catalogAnecdotaRequestDTO);
        Query q = entityManager.createQuery(sql.toString());
        return q.getSingleResult() != null ? Integer.parseInt(q.getSingleResult().toString()) : 0;
    }

    @Override
    public List<CatalogAnecdotaResponseDTO> getCatalogAnecdota(CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new com.scouts.backlibrodeoro.dto.response.CatalogAnecdotaResponseDTO" +
                "(a.id, a.nombre, a.descripcion, a.fecha, u.nombres, u.apellidos, u.usuario) " +
                "FROM Anecdota a " +
                "INNER JOIN a.rama r " +
                "LEFT JOIN a.seccion s " +
                "INNER JOIN a.usuario u " +
                "WHERE 1=1 ");

        filterCatalogSQL(sql, catalogAnecdotaRequestDTO);
        sql.append("ORDER BY 1 DESC ");
        Query q = entityManager.createQuery(sql.toString());
        q.setFirstResult((catalogAnecdotaRequestDTO.getPage()-1) * PAGE_SIZE_CATALOG);
        q.setMaxResults(PAGE_SIZE_CATALOG);
        return q.getResultList();
    }

    private void filterCatalogSQL(StringBuilder sql, CatalogAnecdotaRequestDTO catalogAnecdotaRequestDTO){
        filterEstadoSQL(sql, TypeEstadoAnecdota.AP.toString());
        sql.append("AND ( ");
        filterRamaUsuarioSQL(sql, catalogAnecdotaRequestDTO.getUsuario());
        sql.append("OR ");
        filterSeccionUsuarioSQL(sql, catalogAnecdotaRequestDTO.getUsuario());
        sql.append("OR ");
        filterVisualizacionAnecdotaSQL(sql, TypeVisualizacion.PL.toString());
        sql.append(") ");
    }

    private void filterGridSQL(StringBuilder sql, FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO){
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
        if(filterPresent(filterAnecdotaGridRequestDTO.getCodigoAnecdota()))
            sql.append("AND a.id = "+filterAnecdotaGridRequestDTO.getCodigoAnecdota()+"");

        filterEstadoAnecdota(sql, filterAnecdotaGridRequestDTO);
    }

    private void filterEstadoAnecdota(StringBuilder sql,
                                      FilterAnecdotaGridRequestDTO filterAnecdotaGridRequestDTO){
        String usuarioBuscar= null;
        if(TypeUsuario.IN.toString().equals(filterAnecdotaGridRequestDTO.getTypeUsuarioOwner())){
            usuarioBuscar=filterAnecdotaGridRequestDTO.getUsuarioOwner();
        }else if(filterPresent(filterAnecdotaGridRequestDTO.getUsuarioFilter())){
            usuarioBuscar=filterAnecdotaGridRequestDTO.getUsuarioFilter();
        }
        filterUsuarioEstado(sql, usuarioBuscar, filterAnecdotaGridRequestDTO.getEstado());
    }

    private void filterUsuarioEstado(StringBuilder sql, String usuario, String estado){
        if(filterPresent(usuario) && !filterPresent(estado)){
            filterUsuarioSQL(sql, usuario);
        }else if(!filterPresent(usuario) && filterPresent(estado)){
            filterEstadoSQL(sql, estado);
        }else if(filterPresent(usuario) && filterPresent(estado)){
            filterUsuarioEstadoSQL(sql, usuario, estado);
        }
    }

    private void filterUsuarioEstadoSQL(StringBuilder sql, String usuario, String estado){
        sql.append("AND a.id IN(SELECT ea_eaa.id FROM EstadoAnecdota ea_ea " +
                "                INNER JOIN ea_ea.usuario ea_eausu " +
                "                INNER JOIN ea_ea.anecdota ea_eaa " +
                "                INNER JOIN ea_eaa.usuario eaa_usu " +
                "                WHERE ( " +
                "                           eaa_usu.usuario = '"+usuario+"' " +
                "                               OR " +
                "                           (eaa_usu.usuario != '"+usuario+"' " +
                "                               AND ea_eausu.usuario = '"+usuario+"') " +
                "                      ) " +
                "                AND ea_ea.estado = '"+estado+"' " +
                "                AND ea_ea.id= "+SQL_ESTADO_ANECDOTA+") ");
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
                "                AND ea_ea.id= "+SQL_ESTADO_ANECDOTA+")) ");
    }

    private void filterEstadoSQL(StringBuilder sql, String estado){
        sql.append("AND a.id IN(SELECT ea_eaa.id FROM EstadoAnecdota ea_ea " +
                "                INNER JOIN ea_ea.anecdota ea_eaa " +
                "                WHERE ea_ea.estado = '"+estado+"' " +
                "                AND ea_ea.id= "+SQL_ESTADO_ANECDOTA+") ");
    }

    private void filterRamaUsuarioSQL(StringBuilder sql, String usuario){
        sql.append("(r.id IN(SELECT rt.id FROM Trayectoria t " +
                                "INNER JOIN t.usuario usurt " +
                                "INNER JOIN t.rama rt "+
                                "LEFT JOIN t.seccion srt "+
                                "WHERE usurt.usuario = '"+usuario+"' " +
                                "AND srt IS NULL) " +
                        "AND s IS NULL) ");
    }

    private void filterSeccionUsuarioSQL(StringBuilder sql, String usuario){
        sql.append("(s.id IN(SELECT sst.id FROM Trayectoria t " +
                                "INNER JOIN t.usuario usust " +
                                "LEFT JOIN t.seccion sst " +
                                "WHERE usust.usuario = '"+usuario+"' " +
                                "AND sst IS NOT NULL) " +
                        "AND s IS NOT NULL) ");
    }

    private void filterVisualizacionAnecdotaSQL(StringBuilder sql, String visualizacion){
        sql.append("(a.visualizacion = '"+visualizacion+"') ");
    }

    private <T> Boolean filterPresent(T filter){
        return Optional.ofNullable(filter).isPresent();
    }
}
