/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.Seguridades.Entities.SegUsuarioPerfil;

/**
 *
 * @author Nelson
 */
@Stateless
public class SegUsuarioPerfilFacade extends AbstractFacade<SegUsuarioPerfil> {
    @PersistenceContext(unitName = "sch_seguridades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegUsuarioPerfilFacade() {
        super(SegUsuarioPerfil.class);
    }
    
}
