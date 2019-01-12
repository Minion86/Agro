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
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.Seguridades.Entities.SegAcciones;
import org.Seguridades.Entities.SegMenu;
import org.Seguridades.Entities.SegPerfil;

/**
 *
 * @author Nelson
 */
@Stateless
public class SegAccionMenuPerfilFacade extends AbstractFacade<SegAccionMenuPerfil> {

    @PersistenceContext(unitName = "sch_seguridades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegAccionMenuPerfilFacade() {
        super(SegAccionMenuPerfil.class);
    }

    /**
     * Devuelve el listado Perfiles que están atados a una opción de menu
     *
     * @param idMenu
     * @param idAcciones
     * @return
     */
    public List<SegAccionMenuPerfil> findbyAccionMenu(SegMenu idMenu, SegAcciones idAcciones) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM SegAccionMenuPerfil s where s.idAccionOpcion.idAcciones=:idAcciones and  s.idAccionOpcion.idMenu=:idMenu ");
        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idAcciones", idAcciones);
        q.setParameter("idMenu", idMenu);
        return q.getResultList();
    }
    
     /**
     * Devuelve el listado Perfiles que están atados a una opción de menu
     *
     * @param idMenu
     * @param idAcciones
     * @param idPerfil
     * @return
     */
    public List<SegAccionMenuPerfil> findbyAccionMenuPerfil(SegMenu idMenu, SegAcciones idAcciones, SegPerfil idPerfil) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM SegAccionMenuPerfil s where s.idAccionOpcion.idAcciones=:idAcciones and  s.idAccionOpcion.idMenu=:idMenu and s.idPerfil=:idPerfil");
        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idAcciones", idAcciones);
        q.setParameter("idMenu", idMenu);
        q.setParameter("idPerfil", idPerfil);
        return q.getResultList();
    }
    
     /**
     * Devuelve el listado Perfiles que están atados a una opción de menu por nombre
     *
     * @param textoMenu
     * @param idPerfil
     * @return
     */
    public List<SegAccionMenuPerfil> findbyMenuPerfil(String textoMenu, SegPerfil idPerfil) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM SegAccionMenuPerfil s where s.idAccionOpcion.idMenu.textoMenu=:textoMenu and s.idPerfil=:idPerfil");
        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("textoMenu", textoMenu);
        q.setParameter("idPerfil", idPerfil);
        return q.getResultList();
    }
    
    /**
     * Devuelve el listado Perfiles que están atados a una opción de menu
     *
     * @param idAccionOpcion
     * @return
     */
    public List<SegAccionMenuPerfil> findbySegAccionMenu(SegAccionMenu idAccionOpcion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM SegAccionMenuPerfil s where s.idAccionOpcion=:idAccionOpcion");
        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idAccionOpcion", idAccionOpcion);
        return q.getResultList();
    }

}
