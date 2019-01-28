/*
 * To change this license header, choose License Headers in Project Properties.idProducto.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Facade;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.Adquisicion.Entities.Bodega;
import org.Adquisicion.Entities.DetalleAdquisicion;

/**
 *
 * @author fmullo
 */
@Stateless
public class DetalleAdquisicionFacade extends AbstractFacade<DetalleAdquisicion> {

    @PersistenceContext(unitName = "sch_activosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetalleAdquisicionFacade() {
        super(DetalleAdquisicion.class);
    }

    /**
     * Devuelve la lista de Productoes dado el id
     *
     * @param idDetalleAdquisicion
     * @return
     */
    public List<DetalleAdquisicion> findPorId(Integer idDetalleAdquisicion) {
        javax.persistence.Query q = em.createQuery("SELECT u FROM DetalleAdquisicion u where u.estadoDetalle=true and u.idDetalleAdquisicion=:idDetalleAdquisicion");
        q.setParameter("idDetalleAdquisicion", idDetalleAdquisicion);
        return (List<DetalleAdquisicion>) q.getResultList();
    }

    /**
     * Devuelve el listado de productos de acuerdo a la busqueda avanzada
     *
     * @param detalleAdquisicion
     * @return
     */
    public List<DetalleAdquisicion> findbyBusquedaAvanzada(DetalleAdquisicion detalleAdquisicion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM DetalleAdquisicion s ");
        query.append(" WHERE 1=1 ");

        if (!"".equals(detalleAdquisicion.getIdProducto().getCodigoProducto())) {
            query.append(" AND UPPER(s.idProducto.codigoProducto) like :codigoProducto ");
        }
        if (!"".equals(detalleAdquisicion.getIdProducto().getNombreProducto())) {
            query.append(" AND UPPER(s.idProducto.nombreProducto) like :nombreProducto ");
        }
        if (detalleAdquisicion.getValorProductoDesde() != null) {
            query.append(" AND s.valorDetalleAdquisicion >= :valorProductoDesde ");
        }
        if (detalleAdquisicion.getValorProductoHasta() != null) {
            query.append(" AND s.valorDetalleAdquisicion <= :valorProductoHasta ");
        }
        if (!"".equals(detalleAdquisicion.getIdProducto().getMedidaProducto())) {
            query.append(" AND UPPER(s.idProducto.medidaProducto) like :medidaProducto ");
        }
        if (!"".equals(detalleAdquisicion.getIdProducto().getPesoProducto())) {
            query.append(" AND UPPER(s.idProducto.pesoProducto) like :pesoProducto ");
        }
        if (!"".equals(detalleAdquisicion.getIdProducto().getDescripcionProducto())) {
            query.append(" AND UPPER(s.idProducto.descripcionProducto) like :descripcionProducto ");
        }

        javax.persistence.Query q = em.createQuery(query.toString());

        if (!"".equals(detalleAdquisicion.getIdProducto().getCodigoProducto())) {
            q.setParameter("codigoProducto", "%" + detalleAdquisicion.getIdProducto().getCodigoProducto().toUpperCase() + "%");
        }
        if (!"".equals(detalleAdquisicion.getIdProducto().getNombreProducto())) {
            q.setParameter("nombreProducto", "%" + detalleAdquisicion.getIdProducto().getNombreProducto().toUpperCase() + "%");
        }
        if (!"".equals(detalleAdquisicion.getIdProducto().getMedidaProducto())) {
            q.setParameter("medidaProducto", "%" + detalleAdquisicion.getIdProducto().getMedidaProducto().toUpperCase() + "%");
        }
        if (!"".equals(detalleAdquisicion.getIdProducto().getPesoProducto())) {
            q.setParameter("pesoProducto", "%" + detalleAdquisicion.getIdProducto().getPesoProducto().toUpperCase() + "%");
        }
        if (detalleAdquisicion.getValorProductoDesde() != null) {
            q.setParameter("valorProductoDesde", detalleAdquisicion.getValorProductoDesde());
        }
        if (detalleAdquisicion.getValorProductoHasta() != null) {
            q.setParameter("valorProductoHasta", detalleAdquisicion.getValorProductoHasta());
        }
        if (!"".equals(detalleAdquisicion.getIdProducto().getDescripcionProducto())) {
            q.setParameter("descripcionProducto", "%" + detalleAdquisicion.getIdProducto().getDescripcionProducto().toUpperCase() + "%");
        }

        return q.getResultList();
    }

    /**
     * Devuelve la lista de Productos
     *
     * @return
     */
    public List<DetalleAdquisicion> findTodos() {
        javax.persistence.Query q = em.createQuery("SELECT u FROM DetalleAdquisicion u where u.estadoDetalle=true and u.cantidadBodegaDetalleAdquisicion>0");
        return (List<DetalleAdquisicion>) q.getResultList();
    }
    
        /**
     * Devuelve la lista de Productos
     *
     * @return
     */
    public List<DetalleAdquisicion> findTodosDepreciacion() {
        javax.persistence.Query q = em.createQuery("SELECT u FROM DetalleAdquisicion u where u.estadoDetalle=true");
        return (List<DetalleAdquisicion>) q.getResultList();
    }

    /**
     * Devuelve la lista de Productoes dado la bodega
     *
     * @param idBodega
     * @return
     */
    public List<DetalleAdquisicion> findPorBodega(Bodega idBodega) {
        javax.persistence.Query q = em.createQuery("SELECT u FROM DetalleAdquisicion u where u.estadoDetalle=true and u.idAdquisicion.idBodega=:idBodega");
        q.setParameter("idBodega", idBodega);
        return (List<DetalleAdquisicion>) q.getResultList();
    }

    /**
     * Devuelve la lista de Productoes dado el código de barra
     *
     * @param codigoBarra
     * @return
     */
    public List<DetalleAdquisicion> findPorCodigoBarra(BigInteger codigoBarra) {
        javax.persistence.Query q = em.createQuery("SELECT u FROM DetalleAdquisicion u where u.estadoDetalle=true and u.codigoBarra=:codigoBarra");
        q.setParameter("codigoBarra", codigoBarra);
        return (List<DetalleAdquisicion>) q.getResultList();
    }

}
