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
 * @author nmartinez
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
    
    
    /**
     * Devuelve el listado de plantaciones de acuerdo a la busqueda avanzada
     *
     * @param plantacion
     * @return
     */
    public List<PlantacionDetalle> findbyBusquedaAvanzada(PlantacionDetalle plantacion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM PlantacionDetalle s ");
        query.append(" WHERE s.plantacion.estadoPlantacion=true and s.plantacion.estadoCosecha=false ");

        if (!"".equals(plantacion.getNombrePlantacion())) {
            query.append(" AND UPPER(s.plantacion.nombrePlantacion) like :nombrePlantacion ");
        }

        if (plantacion.getIdUbicacion() != null) {
            query.append(" AND s.plantacion.idUbicacion = :idUbicacion ");
        }
        if (plantacion.getFechaPlantacionDesde() != null) {
            query.append(" AND s.plantacion.fechaPlantacion >= :fechaPlantacionDesde ");
        }
        if (plantacion.getFechaPlantacionHasta() != null) {
            query.append(" AND s.plantacion.fechaPlantacion <= :fechaPlantacionHasta ");
        }

        javax.persistence.Query q = em.createQuery(query.toString());

        if (!"".equals(plantacion.getNombrePlantacion())) {
            q.setParameter("nombrePlantacion", "%" + plantacion.getNombrePlantacion().toUpperCase() + "%");
        }

        if (plantacion.getIdUbicacion() != null) {
            q.setParameter("idUbicacion", plantacion.getIdUbicacion());
        }
        if (plantacion.getFechaPlantacionDesde() != null) {
            q.setParameter("fechaPlantacionDesde", plantacion.getFechaPlantacionDesde());
        }
        if (plantacion.getFechaPlantacionHasta() != null) {
            q.setParameter("fechaPlantacionHasta", plantacion.getFechaPlantacionHasta());
        }
        return q.getResultList();
    }

    
    

}
