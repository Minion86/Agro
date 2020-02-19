/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Facade;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.Plantacion.Entities.Plantacion;

/**
 *
 * @author nmartinez
 */
@Stateless
public class PlantacionFacade extends AbstractFacade<Plantacion> {

    @PersistenceContext(unitName = "sch_plantacion")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlantacionFacade() {
        super(Plantacion.class);
    }

    /**
     * Devuelve el listado de plantaciones de acuerdo a la busqueda avanzada
     *
     * @param plantacion
     * @return
     */
    public List<Plantacion> findbyBusquedaAvanzada(Plantacion plantacion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Plantacion s ");
        query.append(" WHERE s.estadoPlantacion=true and s.estadoCosecha=false ");

        if (!"".equals(plantacion.getNombrePlantacion())) {
            query.append(" AND UPPER(s.nombrePlantacion) like :nombrePlantacion ");
        }

        if (plantacion.getIdUbicacion() != null) {
            query.append(" AND s.idUbicacion = :idUbicacion ");
        }
        if (plantacion.getFechaPlantacionDesde() != null) {
            query.append(" AND s.fechaPlantacion >= :fechaPlantacionDesde ");
        }
        if (plantacion.getFechaPlantacionHasta() != null) {
            query.append(" AND s.fechaPlantacion <= :fechaPlantacionHasta ");
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

    /**
     * Devuelve el registro de plantación de acuerdo a su id
     *
     * @param idPlantacion
     * @return
     */
    public Plantacion findbyId(Long idPlantacion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Plantacion s ");
        query.append(" WHERE s.idPlantacion=:idPlantacion");

        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idPlantacion", idPlantacion);

        return (Plantacion) q.getSingleResult();
    }

    /**
     * Devuelve el registro de plantación de acuerdo a su id
     *
     * @param hoy
     * @return
     */
    public List<Plantacion> findActivos(Date hoy) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Plantacion s ");
        query.append(" WHERE s.estadoPlantacion=true and s.idPlantacion not in ");
        query.append(" (SELECT w.idPlantacion.idPlantacion from WeatherMap w where CAST(w.fechaRegistro AS date)=CAST(:hoy AS date))");
        javax.persistence.Query q = em.createQuery(query.toString());
        q.setMaxResults(59);
        q.setParameter("hoy", hoy, TemporalType.DATE);
        return (List<Plantacion>) q.getResultList();
    }

    /*
    Obtengo el total lista de Plantaciones en la base de conocimiento dado el texto de busqueda
     */
    public Long getPlantacionbyBusquedaTotal(Plantacion plantacion) {
        StringBuffer sql = new StringBuffer(100);
        sql.append("SELECT count(s) FROM Plantacion s ");
        sql.append(" WHERE s.estadoPlantacion=true and s.estadoCosecha=false ");

        if (plantacion != null) {
            if (plantacion.getNombrePlantacion() != null && !"".equals(plantacion.getNombrePlantacion())) {
                sql.append(" AND UPPER(s.nombrePlantacion) like :nombrePlantacion ");
            }

            if (plantacion.getIdUbicacion() != null) {
                sql.append(" AND s.idUbicacion = :idUbicacion ");
            }
            if (plantacion.getFechaPlantacionDesde() != null) {
                sql.append(" AND s.fechaPlantacion >= :fechaPlantacionDesde ");
            }
            if (plantacion.getFechaPlantacionHasta() != null) {
                sql.append(" AND s.fechaPlantacion <= :fechaPlantacionHasta ");
            }
        }

        javax.persistence.Query q = em.createQuery(sql.toString());
        if (plantacion != null) {
            if (plantacion.getNombrePlantacion() != null && !"".equals(plantacion.getNombrePlantacion())) {
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
        }
        return (Long) q.getSingleResult();

    }

    /*
    Obtengo la lista de Plantaciones en la base de conocimiento dado el texto de busqueda
     */
    public List<Plantacion> getPlantacionbyBusquedaPaginado(Plantacion plantacion, Integer index, Integer cantidad_resultados) {
        StringBuffer sql = new StringBuffer(100);
        sql.append("SELECT s FROM Plantacion s ");
        sql.append(" WHERE s.estadoPlantacion=true and s.estadoCosecha=false ");
        if (plantacion != null) {
            if (plantacion.getNombrePlantacion() != null && !"".equals(plantacion.getNombrePlantacion())) {
                sql.append(" AND UPPER(s.nombrePlantacion) like :nombrePlantacion ");
            }

            if (plantacion.getIdUbicacion() != null) {
                sql.append(" AND s.idUbicacion = :idUbicacion ");
            }
            if (plantacion.getFechaPlantacionDesde() != null) {
                sql.append(" AND s.fechaPlantacion >= :fechaPlantacionDesde ");
            }
            if (plantacion.getFechaPlantacionHasta() != null) {
                sql.append(" AND s.fechaPlantacion <= :fechaPlantacionHasta ");
            }
        }

        javax.persistence.Query q = em.createQuery(sql.toString());
        if (plantacion != null) {
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
        }
        Query query = em.createQuery(sql.toString());
        query.setFirstResult(index);
        query.setMaxResults(cantidad_resultados);
        List<Plantacion> listaTmp = query.getResultList();
        return listaTmp;

    }

}
