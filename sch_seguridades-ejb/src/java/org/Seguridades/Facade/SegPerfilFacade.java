/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Facade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.Seguridades.Entities.SegPerfil;
import org.Seguridades.Entities.SegUsuario;

/**
 *
 * @author Nelson
 */
@Stateless
public class SegPerfilFacade extends AbstractFacade<SegPerfil> {

    @PersistenceContext(unitName = "sch_seguridades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegPerfilFacade() {
        super(SegPerfil.class);
    }

    /**
     * Devuelve el listado de perfiles de acuerdo a los permisos de usuario
     *
     * @param user
     * @return
     */
    public List<SegPerfil> findbyPermisos(SegUsuario user) {
        javax.persistence.Query q = em.createNamedQuery("SegPerfil.findByPermisosUsuario");
        q.setParameter("idUsuario", user);

        return q.getResultList();
    }

    /**
     * Devuelve el listado de perfiles de acuerdo a su nombre
     *
     * @param nombrePerfil
     * @return
     */
    public SegPerfil findbyNombre(String nombrePerfil) {
        javax.persistence.Query q = em.createQuery("select p from SegPerfil p where p.nombrePerfil=:nombrePerfil");
        q.setParameter("nombrePerfil", nombrePerfil);

        return (SegPerfil) q.getSingleResult();
    }

    /**
     * Devuelve el listado de perfiles de acuerdo a los permisos de usuario
     *
     * @param nombreUsuario
     * @return
     */
    public List<SegPerfil> findbyPermisosUsername(String nombreUsuario) {
        if (nombreUsuario != null) {

            javax.persistence.Query q = em.createNamedQuery("SegPerfil.findByPermisosUsername");

            q.setParameter("nombreUsuario", nombreUsuario);

            return q.getResultList();

        }

        return new ArrayList<>();
    }

    /**
     * Devuelve el listado de perfiles activos
     *
     * @return
     */
    public List<SegPerfil> findbyActivos() {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM SegPerfil s ");
        query.append(" WHERE s.estadoPerfil = :estadoPerfil ");
        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("estadoPerfil", true);

        return q.getResultList();
    }

    /**
     * Devuelve el listado de perfiles de acuerdo a la busqueda avanzada
     *
     * @param perfil
     * @return
     */
    public List<SegPerfil> findbyBusquedaAvanzada(SegPerfil perfil) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM SegPerfil s ");
        query.append(" WHERE s.estadoPerfil = :estadoPerfil ");

        if (!"".equals(perfil.getNombrePerfil())) {
            query.append(" AND UPPER(s.nombrePerfil) like :nombrePerfil ");
        }

        javax.persistence.Query q = em.createQuery(query.toString());

        if (!"".equals(perfil.getNombrePerfil())) {
            q.setParameter("nombrePerfil", "%" + perfil.getNombrePerfil().toUpperCase() + "%");

        }

        q.setParameter("estadoPerfil", perfil.getEstadoPerfil());

        return q.getResultList();
    }

}
