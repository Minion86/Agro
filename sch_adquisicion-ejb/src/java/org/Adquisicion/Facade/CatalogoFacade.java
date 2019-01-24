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
import org.Adquisicion.Entities.Catalogo;

/**
 *
 * @author Nelson
 */
@Stateless
public class CatalogoFacade extends AbstractFacade<Catalogo> {
    @PersistenceContext(unitName = "sch_activosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CatalogoFacade() {
        super(Catalogo.class);
    }
    
    
    /**
     * Devuelve la lista de catálogos por nemonico grupo catálogo
     *
     * @param nemonicoGrupoCatalogo
     * @return
     */
    public List<Catalogo> findbyGrupoCatalogoNemonico(String nemonicoGrupoCatalogo) {
        javax.persistence.Query q = em.createQuery("SELECT c FROM Catalogo c WHERE c.idGrupocatalogo.nemonicoGrupocatalogo = :nemonicoGrupoCatalogo");
        q.setParameter("nemonicoGrupoCatalogo", nemonicoGrupoCatalogo);
        return (List<Catalogo>) q.getResultList();
    }

    
}
