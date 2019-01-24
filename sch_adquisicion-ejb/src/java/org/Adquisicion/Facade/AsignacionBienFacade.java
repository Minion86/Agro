/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.Adquisicion.Entities.AsignacionBien;

/**
 *
 * @author fmullo
 */
@Stateless
public class AsignacionBienFacade extends AbstractFacade<AsignacionBien> {
    @PersistenceContext(unitName = "sch_activosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsignacionBienFacade() {
        super(AsignacionBien.class);
    }
    
}
