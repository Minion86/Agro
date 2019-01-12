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
import org.Seguridades.Entities.SegAcciones;
import org.Seguridades.Entities.SegPerfil;

/**
 *
 * @author Nelson
 */
@Stateless
public class SegAccionesFacade extends AbstractFacade<SegAcciones> {
    @PersistenceContext(unitName = "sch_seguridades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegAccionesFacade() {
        super(SegAcciones.class);
    }
    
 /**
     Devuelve la lista de acciones disponibles dado su nombre
     * @param nombreAccion        
     * @return 
     */
    public SegAcciones findBySonAccion(String nombreAccion) {
        javax.persistence.Query q = em.createNamedQuery("SegAcciones.findByAccion");
       
        q.setParameter("nombreAccion", nombreAccion);
        return  (SegAcciones)q.getSingleResult();
    }
    
     /**
     Devuelve el listado de items de menu de acuerdo a las acciones que tiene atadas
     * @param idMenu
     * @return 
     */
    public List<SegAcciones> findByOpcion(Integer idMenu) {
        javax.persistence.Query q = em.createNamedQuery("SegAcciones.findByOpcion");
        q.setParameter("idMenu", idMenu);
        return  q.getResultList();
    }
    
     /**
     Devuelve el listado de acciones de acuerdo al perfil
     * @param idPerfil
     * @return 
     */
    public List<SegAcciones> findByPerfil(SegPerfil idPerfil) {
        javax.persistence.Query q = em.createNamedQuery("SegAcciones.findByPerfil");
        q.setParameter("idPerfil", idPerfil);
        return  q.getResultList();
    }
    
}
