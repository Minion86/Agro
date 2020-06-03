/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Biblioteca.Facade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.Biblioteca.Dto.DiccionarioBusquedaDto;
import org.Biblioteca.Entities.Accion;

/**
 *
 * @author nmartinez
 */
@Stateless
public class AccionFacade extends AbstractFacade<Accion> {

    @PersistenceContext(unitName = "sch_bibliotecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccionFacade() {
        super(Accion.class);
    }

    /**
     * Devuelve el registro de diccionario de acuerdo a su id
     *
     * @param id
     * @return
     */
    public Accion findbyId(Long id) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM Accion s ");
        query.append(" WHERE s.id=:id");

        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("id", id);

        return (Accion) q.getSingleResult();
    }

    /*
    Obtengo el total lista de Acciones en la base de conocimiento dado el texto de busqueda
     */
    public Long getAccionbyTextoBusquedaTotal(String textoBusqueda) {
        StringBuffer sql = new StringBuffer(100);
        sql.append("SELECT count(a) FROM Accion a ");
        sql.append(" where UPPER(a.descripcion) like  ?1 or UPPER(a.tags) like  ?1 or  UPPER(a.producto.nombre) like ?1");

        Query query = em.createQuery(sql.toString());
        query.setParameter(1, "%" + textoBusqueda.toUpperCase() + "%");

        return (Long) query.getSingleResult();

    }

    /*
    Obtengo la lista de Acciones en la base de conocimiento dado el texto de busqueda
     */
    public List<Accion> getAccionbyTextoBusquedaPaginado(String textoBusqueda, Integer index, Integer cantidad_resultados) {
        StringBuffer sql = new StringBuffer(100);
        sql.append("SELECT a FROM Accion a ");
        sql.append(" where UPPER(a.descripcion) like  ?1 or UPPER(a.tags) like  ?1 or  UPPER(a.producto.nombre) like ?1 order by a.id asc");

        Query query = em.createQuery(sql.toString());
        query.setParameter(1, "%" + textoBusqueda.toUpperCase() + "%");
        query.setFirstResult(index);
        query.setMaxResults(cantidad_resultados);
        List<Accion> listaTmp = query.getResultList();
        return listaTmp;

    }
    
      /*
    Obtengo la lista de Acciones en la base de conocimiento dado el texto de busqueda
     */
    public List<Accion> getAccionbyTextoAfeccion(String textoBusqueda) {
        StringBuffer sql = new StringBuffer(100);
        sql.append("SELECT a FROM Accion a ");
        sql.append(" where UPPER(a.descripcion) like  ?1 or UPPER(a.tags) like  ?1 or  UPPER(a.producto.nombre) like ?1 order by a.id asc");

        Query query = em.createQuery(sql.toString());
        query.setParameter(1, "%" + textoBusqueda.toUpperCase() + "%");
        List<Accion> listaTmp = query.getResultList();
        return listaTmp;

    }

}
