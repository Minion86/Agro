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
 * @author nmartinez
 */
@Stateless
public class DetalleAdquisicionFacade extends AbstractFacade<DetalleAdquisicion> {

    @PersistenceContext(unitName = "sch_adquisicion")
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
    public List<DetalleAdquisicion> findPorId(Long idDetalleAdquisicion) {
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

        if (!"".equals(detalleAdquisicion.getIdBien().getCodigoProducto())) {
            query.append(" AND UPPER(s.idProducto.codigoProducto) like :codigoProducto ");
        }
        if (!"".equals(detalleAdquisicion.getIdBien().getNombreProducto())) {
            query.append(" AND UPPER(s.idProducto.nombreProducto) like :nombreProducto ");
        }
        if (detalleAdquisicion.getValorBienDesde() != null) {
            query.append(" AND s.valorDetalleAdquisicion >= :valorProductoDesde ");
        }
        if (detalleAdquisicion.getValorBienHasta() != null) {
            query.append(" AND s.valorDetalleAdquisicion <= :valorProductoHasta ");
        }
        if (!"".equals(detalleAdquisicion.getIdBien().getMedidaProducto())) {
            query.append(" AND UPPER(s.idProducto.medidaProducto) like :medidaProducto ");
        }
        if (!"".equals(detalleAdquisicion.getIdBien().getPesoProducto())) {
            query.append(" AND UPPER(s.idProducto.pesoProducto) like :pesoProducto ");
        }
        if (!"".equals(detalleAdquisicion.getIdBien().getDescripcionProducto())) {
            query.append(" AND UPPER(s.idProducto.descripcionProducto) like :descripcionProducto ");
        }

        javax.persistence.Query q = em.createQuery(query.toString());

        if (!"".equals(detalleAdquisicion.getIdBien().getCodigoProducto())) {
            q.setParameter("codigoProducto", "%" + detalleAdquisicion.getIdBien().getCodigoProducto().toUpperCase() + "%");
        }
        if (!"".equals(detalleAdquisicion.getIdBien().getNombreProducto())) {
            q.setParameter("nombreProducto", "%" + detalleAdquisicion.getIdBien().getNombreProducto().toUpperCase() + "%");
        }
        if (!"".equals(detalleAdquisicion.getIdBien().getMedidaProducto())) {
            q.setParameter("medidaProducto", "%" + detalleAdquisicion.getIdBien().getMedidaProducto().toUpperCase() + "%");
        }
        if (!"".equals(detalleAdquisicion.getIdBien().getPesoProducto())) {
            q.setParameter("pesoProducto", "%" + detalleAdquisicion.getIdBien().getPesoProducto().toUpperCase() + "%");
        }
        if (detalleAdquisicion.getValorBienDesde() != null) {
            q.setParameter("valorProductoDesde", detalleAdquisicion.getValorBienDesde());
        }
        if (detalleAdquisicion.getValorBienHasta() != null) {
            q.setParameter("valorProductoHasta", detalleAdquisicion.getValorBienHasta());
        }
        if (!"".equals(detalleAdquisicion.getIdBien().getDescripcionProducto())) {
            q.setParameter("descripcionProducto", "%" + detalleAdquisicion.getIdBien().getDescripcionProducto().toUpperCase() + "%");
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

    /**
     * Devuelve la el detalle de adquisición por su id
     *
     * @param idDetalleAdquisicion
     * @return
     */
    public DetalleAdquisicion findbyId(Integer idDetalleAdquisicion) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM DetalleAdquisicion s where ");
        query.append(" s.idDetalleAdquisicion=:idDetalleAdquisicion ");
        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("idDetalleAdquisicion", idDetalleAdquisicion);
        return (DetalleAdquisicion) q.getSingleResult();
    }

}
