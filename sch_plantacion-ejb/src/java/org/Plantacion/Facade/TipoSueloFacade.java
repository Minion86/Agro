/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.Plantacion.Entities.TipoSuelo;

/**
 *
 * @author Nelson
 */
@Stateless
public class TipoSueloFacade extends AbstractFacade<TipoSuelo> {

    @PersistenceContext(unitName = "sch_plantacion")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoSueloFacade() {
        super(TipoSuelo.class);
    }

    /**
     * Devuelve el tipo de suelo por su id
     *
     * @param idTipoSuelo
     * @return
     */
    public TipoSuelo findbyId(Integer idTipoSuelo) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM TipoSuelo s where ");

        query.append(" s.idTipoSuelo=:idTipoSuelo ");

        javax.persistence.Query q = em.createQuery(query.toString());

        q.setParameter("idTipoSuelo", idTipoSuelo);

        return (TipoSuelo) q.getSingleResult();
    }

    /**
     * Devuelve la lista de TipoSuelos
     *
     * @return
     */
    public List<TipoSuelo> findTodos() {
        javax.persistence.Query q = em.createQuery("SELECT u FROM TipoSuelo u where u.estadoTipoSuelo=true");
        return (List<TipoSuelo>) q.getResultList();
    }

}
