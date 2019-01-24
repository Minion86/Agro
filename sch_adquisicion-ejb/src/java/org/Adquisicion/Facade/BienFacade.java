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
import org.Adquisicion.Entities.Bien;

/**
 *
 * @author Nelson
 */
@Stateless
public class BienFacade extends AbstractFacade<Bien> {

    @PersistenceContext(unitName = "sch_activosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BienFacade() {
        super(Bien.class);
    }

    /**
     * Devuelve el listado de bienes de acuerdo a la busqueda avanzada
     *
     * @param bien
     * @return
     */
    public List<Bien> findbyBusquedaAvanzada(Bien bien) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Bien s ");
        query.append(" WHERE 1=1 ");

        if (!"".equals(bien.getCodigoBien())) {
            query.append(" AND UPPER(s.codigoBien) like :codigoBien ");
        }
        if (!"".equals(bien.getNombreBien())) {
            query.append(" AND UPPER(s.nombreBien) like :nombreBien ");
        }

        if (bien.getVidaUtilBienDesde() != null) {
            query.append(" AND s.vidaUtilBien >= :vidaUtilBienDesde ");
        }
        if (bien.getVidaUtilBienHasta() != null) {
            query.append(" AND s.vidaUtilBien <= :vidaUtilBienHasta ");
        }
        if (!"".equals(bien.getMarcaBien())) {
            query.append(" AND UPPER(s.marcaBien) like :marcaBien ");
        }
        if (!"".equals(bien.getSerieBien())) {
            query.append(" AND UPPER(s.serieBien) like :serieBien ");
        }
        if (bien.getValorBienDesde() != null) {
            query.append(" AND s.valorBien >= :valorBienDesde ");
        }
        if (bien.getValorBienHasta() != null) {
            query.append(" AND s.valorBien <= :valorBienHasta ");
        }
        if (!"".equals(bien.getMedidaBien())) {
            query.append(" AND UPPER(s.medidaBien) like :medidaBien ");
        }
        if (!"".equals(bien.getPesoBien())) {
            query.append(" AND UPPER(s.pesoBien) like :pesoBien ");
        }
        if (!"".equals(bien.getDescripcionBien())) {
            query.append(" AND UPPER(s.descripcionBien) like :descripcionBien ");
        }

        javax.persistence.Query q = em.createQuery(query.toString());

        if (!"".equals(bien.getCodigoBien())) {
            q.setParameter("codigoBien", "%" + bien.getCodigoBien().toUpperCase() + "%");
        }
        if (!"".equals(bien.getNombreBien())) {
            q.setParameter("nombreBien", "%" + bien.getNombreBien().toUpperCase() + "%");
        }
        if (bien.getVidaUtilBienHasta() != null) {
            q.setParameter("vidaUtilBienHasta", bien.getVidaUtilBienHasta());
        }
        if (bien.getVidaUtilBienDesde() != null) {
            q.setParameter("vidaUtilBienDesde", bien.getVidaUtilBienDesde());
        }
        if (!"".equals(bien.getMarcaBien())) {
            q.setParameter("marcaBien", "%" + bien.getMarcaBien().toUpperCase() + "%");
        }
        if (!"".equals(bien.getSerieBien())) {
            q.setParameter("serieBien", "%" + bien.getSerieBien().toUpperCase() + "%");
        }
        if (!"".equals(bien.getMedidaBien())) {
            q.setParameter("medidaBien", "%" + bien.getMedidaBien().toUpperCase() + "%");
        }
        if (!"".equals(bien.getPesoBien())) {
            q.setParameter("pesoBien", "%" + bien.getPesoBien().toUpperCase() + "%");
        }
        if (bien.getValorBienDesde() != null) {
            q.setParameter("valorBienDesde", bien.getValorBienDesde());
        }
        if (bien.getValorBienHasta() != null) {
            q.setParameter("valorBienHasta", bien.getValorBienHasta());
        }
        if (!"".equals(bien.getDescripcionBien())) {
            q.setParameter("descripcionBien", "%" + bien.getDescripcionBien().toUpperCase() + "%");
        }
        
        return q.getResultList();
    }

}
