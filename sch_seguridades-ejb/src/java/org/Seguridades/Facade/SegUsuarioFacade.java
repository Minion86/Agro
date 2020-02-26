/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.Seguridades.Entities.SegUsuario;

/**
 *
 * @author Nelson
 */
@Stateless
public class SegUsuarioFacade extends AbstractFacade<SegUsuario> {

    @PersistenceContext(unitName = "sch_seguridades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegUsuarioFacade() {
        super(SegUsuario.class);
    }

    /**
     * Devuelve el usuario por el username
     *
     * @param usernameUsuario
     * @return
     */
    public SegUsuario findbyUsername(String usernameUsuario) {
        javax.persistence.Query q = em.createNamedQuery("SegUsuario.findByUsernameUsuario");
        q.setParameter("usernameUsuario", usernameUsuario);

        return (SegUsuario) q.getSingleResult();
    }

    /**
     * Devuelve el usuario por el username y password
     *
     * @param nombreUsuario
     * @param passwordUsuario
     * @return
     */
    public SegUsuario findbyNombreyPassword(String usernameUsuario, String passwordUsuario) {
        javax.persistence.Query q = em.createNamedQuery("SegUsuario.findByPasswordUsuario");
        q.setParameter("usernameUsuario", usernameUsuario);
        q.setParameter("passwordUsuario", passwordUsuario);
        List<SegUsuario> usuario = q.getResultList();
        if (!usuario.isEmpty()) {
            return (usuario.get(0));
        } else {
            return null;
        }
    }

    /**
     * Devuelve el listado de usuarios de acuerdo a la busqueda avanzada
     *
     * @param usuario
     * @return
     */
    public List<SegUsuario> findbyBusquedaAvanzada(SegUsuario usuario) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM SegUsuario s ");
        query.append(" WHERE 1=1 ");

        if (!"".equals(usuario.getUsernameUsuario())) {
            query.append(" AND UPPER(s.usernameUsuario) like :usernameUsuario ");
        }
        if (!"".equals(usuario.getIdentificacionUsuario())) {
            query.append(" AND UPPER(s.identificacionUsuario) like :identificacionUsuario ");
        }
        if (!"".equals(usuario.getNombreUsuario())) {
            query.append(" AND UPPER(s.nombreUsuario) like :nombreUsuario ");
        }
        if (!"".equals(usuario.getApellidoUsuario())) {
            query.append(" AND UPPER(s.apellidoUsuario) like :apellidoUsuario ");
        }
        if (!"".equals(usuario.getEmailUsuario())) {
            query.append(" AND UPPER(s.emailUsuario) like :emailUsuario ");
        }

        javax.persistence.Query q = em.createQuery(query.toString());

        if (!"".equals(usuario.getUsernameUsuario())) {
            q.setParameter("usernameUsuario", "%" + usuario.getUsernameUsuario().toUpperCase() + "%");

        }
        if (!"".equals(usuario.getIdentificacionUsuario())) {
            q.setParameter("identificacionUsuario", "%" + usuario.getIdentificacionUsuario().toUpperCase() + "%");

        }
        if (!"".equals(usuario.getNombreUsuario())) {
            q.setParameter("nombreUsuario", "%" + usuario.getNombreUsuario().toUpperCase() + "%");

        }
        if (!"".equals(usuario.getApellidoUsuario())) {
            q.setParameter("apellidoUsuario", "%" + usuario.getApellidoUsuario().toUpperCase() + "%");

        }
        if (!"".equals(usuario.getEmailUsuario())) {
            q.setParameter("emailUsuario", "%" + usuario.getEmailUsuario().toUpperCase() + "%");

        }

        return q.getResultList();
    }

    /**
     * Devuelve el usuario por el nombrePerfil
     *
     * @param nombrePerfil
     * @return
     */
    public List<SegUsuario> findbyPerfil(String nombrePerfil) {
        javax.persistence.Query q = em.createQuery("Select s from SegUsuario s join s.segUsuarioPerfilList u on s=u.idUsuario where  u.idPerfil.nombrePerfil=:nombrePerfil and s.activoUsuario=true");
        q.setParameter("nombrePerfil", nombrePerfil);

        return (List<SegUsuario>) q.getResultList();
    }

}
