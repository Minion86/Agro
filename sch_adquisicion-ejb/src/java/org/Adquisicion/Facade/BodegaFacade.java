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
import org.Adquisicion.Entities.Bodega;

/**
 *
 * @author Nelson
 */
@Stateless
public class BodegaFacade extends AbstractFacade<Bodega> {

    @PersistenceContext(unitName = "sch_adquisicion")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BodegaFacade() {
        super(Bodega.class);
    }

    /**
     * Devuelve el listado de bodegas de acuerdo a la busqueda avanzada
     *
     * @param bodega
     * @return
     */
    public List<Bodega> findbyBusquedaAvanzada(Bodega bodega) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Bodega s ");
        query.append(" WHERE s.estadoBodega=true ");

        if (!"".equals(bodega.getNombreBodega())) {
            query.append(" AND UPPER(s.nombreBodega) like :nombreBodega ");
        }
        if (!"".equals(bodega.getDireccionBodega())) {
            query.append(" AND UPPER(s.direccionBodega) like :direccionBodega ");
        }
        if (bodega.getClaseBodega() != null) {
            query.append(" AND s.claseBodega = :claseBodega ");
        }
        if (bodega.getTipoBodega() != null) {
            query.append(" AND s.tipoBodega = :tipoBodega ");
        }
        if (bodega.getIdBodega() != null) {
            query.append(" AND s.idBodega = :idBodega ");
        }
        if (bodega.getIdUbicacionPadre() != null) {
            if (bodega.getIdUbicacion() != null) {
                query.append(" AND s.idUbicacion = :idUbicacion ");
            } else {
                query.append("AND s.idUbicacion.padreId = :padreId");
            }
        }

        javax.persistence.Query q = em.createQuery(query.toString());

        if (!"".equals(bodega.getNombreBodega())) {
            q.setParameter("nombreBodega", "%" + bodega.getNombreBodega().toUpperCase() + "%");

        }
        if (!"".equals(bodega.getDireccionBodega())) {
            q.setParameter("direccionBodega", "%" + bodega.getDireccionBodega().toUpperCase() + "%");

        }

        if (bodega.getClaseBodega() != null) {
            q.setParameter("claseBodega", bodega.getClaseBodega());

        }
        if (bodega.getTipoBodega() != null) {
            q.setParameter("tipoBodega", bodega.getTipoBodega());
        }
        if (bodega.getIdBodega() != null) {
            q.setParameter("idBodega", bodega.getIdBodega());
        }

        if (bodega.getIdUbicacionPadre() != null) {
            if (bodega.getIdUbicacion() != null) {
                q.setParameter("idUbicacion", bodega.getIdUbicacion());
            } else {
                q.setParameter("padreId", bodega.getIdUbicacionPadre());
            }
        }

        return q.getResultList();
    }

    /**
     * Devuelve el listado de bodegas de acuerdo a la busqueda avanzada
     *
     * @param bodega
     * @return
     */
    public List<Bodega> findbyNombreBodega(Bodega bodega) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Bodega s ");

        query.append(" WHERE UPPER(s.nombreBodega) like :nombreBodega ");

        javax.persistence.Query q = em.createQuery(query.toString());

        q.setParameter("nombreBodega", "%" + bodega.getNombreBodega().toUpperCase() + "%");

        return q.getResultList();
    }

    /**
     * Devuelve la lista de Bodegas
     *
     * @return
     */
    public List<Bodega> findTodos() {
        javax.persistence.Query q = em.createQuery("SELECT u FROM Bodega u where u.estadoBodega=true");
        return (List<Bodega>) q.getResultList();
    }

}
