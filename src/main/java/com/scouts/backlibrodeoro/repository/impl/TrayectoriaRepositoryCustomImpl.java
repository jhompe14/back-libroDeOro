package com.scouts.backlibrodeoro.repository.impl;

import com.scouts.backlibrodeoro.dto.response.TrayectoriaResponseDTO;
import com.scouts.backlibrodeoro.repository.TrayectoriaRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class TrayectoriaRepositoryCustomImpl implements TrayectoriaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TrayectoriaResponseDTO> findTrayectoriaResponseDTOByUsuario(String usuario) {
        String sql = "SELECT new com.scouts.backlibrodeoro.dto.response.TrayectoriaResponseDTO" +
                "(t.id, g.id, g.nombre, r.id, r.nombre, s.id, s.nombre, c.id, c.nombre, t.anioIngreso, t.anioRetiro) " +
                "FROM Trayectoria t " +
                "INNER JOIN t.usuario usu " +
                "INNER JOIN t.grupo g " +
                "LEFT JOIN t.rama r " +
                "LEFT JOIN t.seccion s " +
                "LEFT JOIN t.cargo c " +
                "WHERE usu.usuario = :usuario";

        Query q = entityManager.createQuery(sql);
        q.setParameter("usuario", usuario);

        return q.getResultList();
    }
}
