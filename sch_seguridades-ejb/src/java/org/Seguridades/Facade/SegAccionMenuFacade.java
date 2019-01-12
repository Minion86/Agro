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
import org.Seguridades.Entities.SegAccionMenu;
import org.Seguridades.Entities.SegAcciones;
import org.Seguridades.Entities.SegMenu;

/**
 *
 * @author Nelson
 */
@Stateless
public class SegAccionMenuFacade extends AbstractFacade<SegAccionMenu> {
    @PersistenceContext(unitName = "sch_seguridades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegAccionMenuFacade() {
        super(SegAccionMenu.class);
    }
    
    /**
     * Devuelve el listado acciones atadas a menu
     *
     * @param idMenu
     * @param idAcciones
     * @return
     */
    public List<SegAccionMenu> findbyAccionMenu(SegMenu idMenu, SegAcciones idAcciones) {

        StringBuilder query = new StringBuilder();

       query.append("SELECT s FROM SegAccionMenu s where s.idAcciones=:idAcciones and s.idMenu=:idMenu ");
        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idAcciones", idAcciones);
        q.setParameter("idMenu", idMenu);
        return q.getResultList();
    }
    
    /**
     * Devuelve el listado acciones atadas a menu
     *
     * @param idMenu
     * @return
     */
    public List<SegAccionMenu> findbyMenu(SegMenu idMenu) {

        StringBuilder query = new StringBuilder();

       query.append("SELECT s FROM SegAccionMenu s where s.idMenu=:idMenu ");
        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idMenu", idMenu);
        return q.getResultList();
    }
    
}
