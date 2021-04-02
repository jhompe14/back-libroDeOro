package com.scouts.backlibrodeoro.repository.impl;

import com.scouts.backlibrodeoro.dto.request.FilterUsuarioGridRequestDTO;
import com.scouts.backlibrodeoro.dto.response.UsuarioGridResponseDTO;
import com.scouts.backlibrodeoro.repository.UsuarioRepositoryCustom;
import com.scouts.backlibrodeoro.types.TypeUsuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class UsuarioRepositoryCustomImpl implements UsuarioRepositoryCustom {

    private final Integer PAGE_SIZE_GRID= 8;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer countFilterUsuario(FilterUsuarioGridRequestDTO filterUsuarioGridRequestDTO) {
         String sql = "SELECT COUNT(1) " +
                 "FROM Usuario u " +
                 "WHERE UPPER(u.usuario) LIKE '"+filterUsuarioGridRequestDTO.getUsuario().toUpperCase()+"%' " +
                 "AND UPPER(u.nombres) LIKE '"+filterUsuarioGridRequestDTO.getNombres().toUpperCase()+"%' " +
                 "AND UPPER(u.apellidos) LIKE '"+filterUsuarioGridRequestDTO.getApellidos().toUpperCase()+"%' " +
                 "AND u.tipoUsuario != '"+ TypeUsuario.AD.toString()+"'";
        Query q = entityManager.createQuery(sql);
        return q.getSingleResult() != null ? Integer.parseInt(q.getSingleResult().toString()) : 0;
    }

    @Override
    public List<UsuarioGridResponseDTO> getFilterUsuario(FilterUsuarioGridRequestDTO filterUsuarioGridRequestDTO) {
         String sql = "SELECT new com.scouts.backlibrodeoro.dto.response.UsuarioGridResponseDTO" +
                 "(u.nombres, u.apellidos, u.tipoIntegrante, u.correo, u.telefono, u.direccion, u.ciudad, u.usuario) " +
                "FROM Usuario u " +
                "WHERE UPPER(u.usuario) LIKE '"+filterUsuarioGridRequestDTO.getUsuario().toUpperCase()+"%' " +
                "AND UPPER(u.nombres) LIKE '"+filterUsuarioGridRequestDTO.getNombres().toUpperCase()+"%' " +
                "AND UPPER(u.apellidos) LIKE '"+filterUsuarioGridRequestDTO.getApellidos().toUpperCase()+"%' " +
                "AND u.tipoUsuario != '"+ TypeUsuario.AD.toString()+"'";
        Query q = entityManager.createQuery(sql);
        q.setFirstResult((filterUsuarioGridRequestDTO.getPage()-1) * PAGE_SIZE_GRID);
        q.setMaxResults(PAGE_SIZE_GRID);
        return q.getResultList();
    }
}
