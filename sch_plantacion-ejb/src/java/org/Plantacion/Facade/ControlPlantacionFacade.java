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
import org.Plantacion.Entities.ControlPlantacion;
import org.Plantacion.Entities.Plantacion;

/**
 *
 * @author fmullo
 */
@Stateless
public class ControlPlantacionFacade extends AbstractFacade<ControlPlantacion> {

    @PersistenceContext(unitName = "sch_plantacion")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ControlPlantacionFacade() {
        super(ControlPlantacion.class);
    }

    /**
     * Devuelve el registro de control plantación de acuerdo a su id
     *
     * @param idControlPlantacion
     * @return
     */
    public ControlPlantacion findbyId(Long idControlPlantacion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM ControlPlantacion s ");
        query.append(" WHERE s.idControlPlantacion=:idControlPlantacion");

        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idControlPlantacion", idControlPlantacion);

        return (ControlPlantacion) q.getSingleResult();
    }

}
