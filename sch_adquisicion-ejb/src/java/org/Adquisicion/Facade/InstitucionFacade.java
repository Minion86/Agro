/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.Adquisicion.Entities.Institucion;

/**
 *
 * @author Nelson
 */
@Stateless
public class InstitucionFacade extends AbstractFacade<Institucion> {

    @PersistenceContext(unitName = "sch_activosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstitucionFacade() {
        super(Institucion.class);
    }

    /**
     * Devuelve la lista de Instituciones
     *
     * @return
     */
    public List<Institucion> findTodos() {
        javax.persistence.Query q = em.createQuery("SELECT u FROM Institucion u");
        return (List<Institucion>) q.getResultList();
    }

}
