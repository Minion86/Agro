/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.Adquisicion.Entities.Adquisicion;

/**
 *
 * @author fmullo
 */
@Stateless
public class AdquisicionFacade extends AbstractFacade<Adquisicion> {
    @PersistenceContext(unitName = "sch_activosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdquisicionFacade() {
        super(Adquisicion.class);
    }
    
}
