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
import org.Adquisicion.Entities.Adquisicion;

/**
 *
 * @author fmullo
 */
@Stateless
public class AdquisicionFacade extends AbstractFacade<Adquisicion> {
    @PersistenceContext(unitName = "sch_adquisicion")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdquisicionFacade() {
        super(Adquisicion.class);
    }
    
    /**
     * Devuelve el listado de adquisiciones de acuerdo a la busqueda avanzada
     *
     * @param adquisicion
     * @return
     */
    public List<Adquisicion> findbyBusquedaAvanzada(Adquisicion adquisicion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Adquisicion s ");
        query.append(" WHERE 1=1 ");

        if (!"".equals(adquisicion.getNumeroFacturaBien())) {
            query.append(" AND UPPER(s.numeroFacturaProducto) like :numeroFacturaProducto ");
        }
        if (!"".equals(adquisicion.getCedulaResponsableAdquisicion())) {
            query.append(" AND UPPER(s.cedulaResponsableAdquisicion) like :cedulaResponsableAdquisicion ");
        }
        if (!"".equals(adquisicion.getNombresResponsableAdquisicion())) {
            query.append(" AND UPPER(s.nombresResponsableAdquisicion) like :nombresResponsableAdquisicion ");
        }
        if (!"".equals(adquisicion.getApellidosResponsableAdquisicion())) {
            query.append(" AND UPPER(s.apellidosResponsableAdquisicion) like :apellidosResponsableAdquisicion ");
        }
        if (adquisicion.getIdBodegaInt() != null) {
            query.append(" AND s.idBodega.idBodega = :idBodega ");
        }
        if (adquisicion.getFechaAdquisicionDesde() != null) {
            query.append(" AND s.fechaAdquisicion >= :fechaAdquisicionDesde ");
        }
        if (adquisicion.getFechaAdquisicionHasta() != null) {
            query.append(" AND s.fechaAdquisicion <= :fechaAdquisicionHasta ");
        }

        javax.persistence.Query q = em.createQuery(query.toString());

        if (!"".equals(adquisicion.getNumeroFacturaBien())) {
            q.setParameter("numeroFacturaProducto", "%" + adquisicion.getNumeroFacturaBien().toUpperCase() + "%");
        }
        if (!"".equals(adquisicion.getCedulaResponsableAdquisicion())) {
            q.setParameter("cedulaResponsableAdquisicion", "%" + adquisicion.getCedulaResponsableAdquisicion().toUpperCase() + "%");
        }
        if (!"".equals(adquisicion.getNombresResponsableAdquisicion())) {
            q.setParameter("nombresResponsableAdquisicion", "%" + adquisicion.getNombresResponsableAdquisicion().toUpperCase() + "%");
        }
        if (!"".equals(adquisicion.getApellidosResponsableAdquisicion())) {
            q.setParameter("apellidosResponsableAdquisicion", "%" + adquisicion.getApellidosResponsableAdquisicion().toUpperCase() + "%");
        }
        if (adquisicion.getIdBodegaInt() != null) {
            q.setParameter("idBodega", adquisicion.getIdBodegaInt());
        }
        if (adquisicion.getFechaAdquisicionDesde() != null) {
            q.setParameter("fechaAdquisicionDesde", adquisicion.getFechaAdquisicionDesde());
        }
        if (adquisicion.getFechaAdquisicionHasta() != null) {
            q.setParameter("fechaAdquisicionHasta", adquisicion.getFechaAdquisicionHasta());
        }

        return q.getResultList();
    }

    /**
     * Devuelve el listado de adquisiciones de acuerdo a idAdquisicion
     *
     * @param idAdquisicion
     * @return
     */
    public List<Adquisicion> findbyId(Integer idAdquisicion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Adquisicion s ");
        query.append(" WHERE s.idAdquisicion=:idAdquisicion");

        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idAdquisicion", idAdquisicion);

        return q.getResultList();
    }

    
}
