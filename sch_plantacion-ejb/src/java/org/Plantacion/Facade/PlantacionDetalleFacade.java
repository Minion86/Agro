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
import org.Plantacion.Entities.PlantacionDetalle;

/**
 *
 * @author fmullo
 */
@Stateless
public class PlantacionDetalleFacade extends AbstractFacade<PlantacionDetalle> {

    @PersistenceContext(unitName = "sch_plantacion")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlantacionDetalleFacade() {
        super(PlantacionDetalle.class);
    }

    /**
     * Devuelve el registro de detalle plantación de acuerdo a su id
     *
     * @param idPlantacionDetalle
     * @return
     */
    public PlantacionDetalle findbyId(Long idPlantacionDetalle) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM PlantacionDetalle s ");
        query.append(" WHERE s.idPlantacionDetalle=:idPlantacionDetalle");

        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idPlantacionDetalle", idPlantacionDetalle);

        return (PlantacionDetalle) q.getSingleResult();
    }

    /**
     * Devuelve el registro de detalle plantación de acuerdo a su id de
     * plantación
     *
     * @param idPlantacion
     * @return
     */
    public List<PlantacionDetalle> findbyPlantacionId(Long idPlantacion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM PlantacionDetalle s ");
        query.append(" WHERE s.plantacion.idPlantacion=:idPlantacion and s.estado=1");

        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idPlantacion", idPlantacion);

        return (List<PlantacionDetalle>) q.getResultList();
    }

}
