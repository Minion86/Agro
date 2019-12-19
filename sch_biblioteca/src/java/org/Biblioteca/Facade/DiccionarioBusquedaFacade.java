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
import org.Biblioteca.Entities.DiccionarioBusqueda;

/**
 *
 * @author nmartinez
 */
@Stateless
public class DiccionarioBusquedaFacade extends AbstractFacade<DiccionarioBusqueda> {

    @PersistenceContext(unitName = "sch_bibliotecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DiccionarioBusquedaFacade() {
        super(DiccionarioBusqueda.class);
    }

    /**
     * Devuelve el registro de diccionario de acuerdo a su id
     *
     * @param id
     * @return
     */
    public DiccionarioBusqueda findbyId(Long id) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT s FROM DiccionarioBusqueda s ");
        query.append(" WHERE s.id=:id");

        javax.persistence.Query q = em.createQuery(query.toString());
        q.setParameter("id", id);

        return (DiccionarioBusqueda) q.getSingleResult();
    }

    public List<DiccionarioBusquedaDto> getDiccionariobyUserId(Integer id) {
        StringBuffer sql = new StringBuffer(100);
        sql.append("SELECT  DISTINCT palabra FROM sch_biblioteca.diccionario_busqueda ");
        sql.append("where id_usuario=?1   limit 2000");
//order by id desc
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter(1, id);

        List<String> listaTmp = query.getResultList();
        if (listaTmp.isEmpty()) {
            return new ArrayList<DiccionarioBusquedaDto>();
        } else {
            List<DiccionarioBusquedaDto> diccionarioBusquedaLista = new ArrayList<DiccionarioBusquedaDto>();

            for (String obj : listaTmp) {
                DiccionarioBusquedaDto diccionarioBusqueda = new DiccionarioBusquedaDto();
                diccionarioBusqueda.setValue(obj != null ? obj : "");
                diccionarioBusqueda.setData(obj != null ? obj : "");
                diccionarioBusquedaLista.add(diccionarioBusqueda);
            }
            return diccionarioBusquedaLista;
        }

    }

}
