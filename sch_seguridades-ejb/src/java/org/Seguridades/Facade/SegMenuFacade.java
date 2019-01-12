/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.Seguridades.Entities.SegMenu;

/**
 *
 * @author Nelson
 */
@Stateless
public class SegMenuFacade extends AbstractFacade<SegMenu> {

    @PersistenceContext(unitName = "sch_seguridades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegMenuFacade() {
        super(SegMenu.class);
    }

    /**
     * Devuelve el listado de items de menu de acuerdo a sus permisos
     *
     * @param idPerfil
     * @param idAcciones
     * @return
     */
    public List<SegMenu> findbyPermisos(Integer idPerfil, Integer idAcciones) {
        javax.persistence.Query q = em.createNamedQuery("SegMenu.findByPermisos");
        q.setParameter("idPerfil", idPerfil);
        q.setParameter("idAcciones", idAcciones);
        return q.getResultList();
    }

    /**
     * Devuelve el listado de items de menu de acuerdo a sus permisos
     *
     * @param idPerfil
     * @return
     */
    public List<SegMenu> findbyPerfil(Integer idPerfil) {
        javax.persistence.Query q = em.createNamedQuery("SegMenu.findByPerfil");
        q.setParameter("idPerfil", idPerfil);
        return q.getResultList();
    }

    /**
     * Devuelve el listado de items de menu de acuerdo a su jerarquia
     *
     * @return
     */
    public List<Object> findbyJerarquia() {

        StoredProcedureQuery q = em.createStoredProcedureQuery("sch_seguridades.menutiered");
        q.execute();
        return q.getResultList();
    }

    /**
     * Devuelve el listado de items de menu de acuerdo a su jerarquia
     *
     * @param ID
     * @return
     */
    public List<Object> findPathbyHijo(Integer ID) {

        StoredProcedureQuery q = em.createStoredProcedureQuery("sch_seguridades.menutiered_byid");
        q.registerStoredProcedureParameter("ID", Integer.class, ParameterMode.IN);
        q.setParameter("ID", ID);
        q.execute();
        return q.getResultList();
    }

    public List<SegMenu> listarPadres() throws Exception {
        String jpaQl = "SELECT m FROM SegMenu m WHERE m.raizMenu=0 order by m.secuencialMenu";
        Query query = getEntityManager().createQuery(jpaQl);
        return query.getResultList();
    }

    public SegMenu listarAbuelo() throws Exception {
        String jpaQl = "SELECT m FROM SegMenu m WHERE m.raizMenu=0 order by m.secuencialMenu";
        Query query = getEntityManager().createQuery(jpaQl);
        return (SegMenu) query.getResultList().get(0);
    }

    public List<SegMenu> listarNodosConHijos() throws Exception {
        String jpaQl = "Select m FROM SegMenu m where m.tieneHijosMenu = true order by m.secuencialMenu";
        Query query = getEntityManager().createQuery(jpaQl);
        return query.getResultList();
    }

    public List<SegMenu> listarSegMenuSistema() throws Exception {
        String jpaQl = "Select m FROM SegMenu m  order by m.secuencialMenu";
        Query query = getEntityManager().createQuery(jpaQl);
        return query.getResultList();
    }

    public SegMenu listarbyId(Integer idMenu) throws Exception {
        String jpaQl = "SELECT s FROM SegMenu s WHERE s.idMenu = :idMenu";
        Query query = getEntityManager().createQuery(jpaQl);
        query.setParameter("idMenu", idMenu);
        return (SegMenu) query.getResultList().get(0);
    }

    public List<SegMenu> listarHijos(Integer raizMenu) throws Exception {
        String jpaQl = "SELECT m FROM SegMenu m WHERE  m.raizMenu = :raizMenu order by m.secuencialMenu";
        Query query = getEntityManager().createQuery(jpaQl);
        query.setParameter("raizMenu", raizMenu);
        return query.getResultList();
    }

}
