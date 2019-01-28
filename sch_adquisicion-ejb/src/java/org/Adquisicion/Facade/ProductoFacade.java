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
import org.Adquisicion.Entities.Producto;

/**
 *
 * @author Nelson
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "sch_adquisicion")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    /**
     * Devuelve el listado de bienes de acuerdo a la busqueda avanzada
     *
     * @param bien
     * @return
     */
    public List<Producto> findbyBusquedaAvanzada(Producto bien) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Producto s ");
        query.append(" WHERE 1=1 ");

        if (!"".equals(bien.getCodigoProducto())) {
            query.append(" AND UPPER(s.codigoProducto) like :codigoProducto ");
        }
        if (!"".equals(bien.getNombreProducto())) {
            query.append(" AND UPPER(s.nombreProducto) like :nombreProducto ");
        }
        if (bien.getValorProductoDesde() != null) {
            query.append(" AND s.valorProducto >= :valorProductoDesde ");
        }
        if (bien.getValorProductoHasta() != null) {
            query.append(" AND s.valorProducto <= :valorProductoHasta ");
        }
        if (!"".equals(bien.getMedidaProducto())) {
            query.append(" AND UPPER(s.medidaProducto) like :medidaProducto ");
        }
        if (!"".equals(bien.getPesoProducto())) {
            query.append(" AND UPPER(s.pesoProducto) like :pesoProducto ");
        }
        if (!"".equals(bien.getDescripcionProducto())) {
            query.append(" AND UPPER(s.descripcionProducto) like :descripcionProducto ");
        }

        javax.persistence.Query q = em.createQuery(query.toString());

        if (!"".equals(bien.getCodigoProducto())) {
            q.setParameter("codigoProducto", "%" + bien.getCodigoProducto().toUpperCase() + "%");
        }
        if (!"".equals(bien.getNombreProducto())) {
            q.setParameter("nombreProducto", "%" + bien.getNombreProducto().toUpperCase() + "%");
        }
        if (!"".equals(bien.getMedidaProducto())) {
            q.setParameter("medidaProducto", "%" + bien.getMedidaProducto().toUpperCase() + "%");
        }
        if (!"".equals(bien.getPesoProducto())) {
            q.setParameter("pesoProducto", "%" + bien.getPesoProducto().toUpperCase() + "%");
        }
        if (bien.getValorProductoDesde() != null) {
            q.setParameter("valorProductoDesde", bien.getValorProductoDesde());
        }
        if (bien.getValorProductoHasta() != null) {
            q.setParameter("valorProductoHasta", bien.getValorProductoHasta());
        }
        if (!"".equals(bien.getDescripcionProducto())) {
            q.setParameter("descripcionProducto", "%" + bien.getDescripcionProducto().toUpperCase() + "%");
        }
        
        return q.getResultList();
    }
    
    
    /**
     * Devuelve la lista de Productos
     *
     * @return
     */
    public List<Producto> findTodos() {
        javax.persistence.Query q = em.createQuery("SELECT u FROM Producto u where u.estadoProducto=true");
        return (List<Producto>) q.getResultList();
    }

}
