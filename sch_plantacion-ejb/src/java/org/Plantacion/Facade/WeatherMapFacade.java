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
import org.Plantacion.Entities.WeatherMap;

/**
 *
 * @author nmartinez
 */
@Stateless
public class WeatherMapFacade extends AbstractFacade<WeatherMap> {

    @PersistenceContext(unitName = "sch_plantacion")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeatherMapFacade() {
        super(WeatherMap.class);
    }

    /**
     * Devuelve el registro de weather map de acuerdo a su id
     *
     * @param id
     * @return
     */
    public WeatherMap findbyId(Long id) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM WeatherMap s ");
        query.append(" WHERE s.id=:id");

        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("id", id);

        return (WeatherMap) q.getSingleResult();
    }

    /**
     * Devuelve el listado de registro de weather map de acuerdo a su id de
     * plantación
     *
     * @param idPlantacion
     * @return
     */
    public List<WeatherMap> findbyIdPlantacion(Long idPlantacion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM WeatherMap s ");
        query.append(" WHERE s.idPlantacion.idPlantacion=:idPlantacion");

        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idPlantacion", idPlantacion);

        return (List<WeatherMap>) q.getResultList();
    }

}
