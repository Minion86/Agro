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
import org.Adquisicion.Entities.Ubicacion;

/**
 *
 * @author Nelson
 */
@Stateless
public class UbicacionFacade extends AbstractFacade<Ubicacion> {

    @PersistenceContext(unitName = "sch_adquisicion")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UbicacionFacade() {
        super(Ubicacion.class);
    }

    /**
     * Devuelve la lista de ubicaciones por nivel
     *
     * @param nivelUbicacion
     * @return
     */
    public List<Ubicacion> findbyNivelUbicacion(Integer nivelUbicacion) {
        javax.persistence.Query q = em.createQuery("SELECT u FROM Ubicacion u WHERE u.nivelUbicacion = :nivelUbicacion");
        q.setParameter("nivelUbicacion", nivelUbicacion);
        return (List<Ubicacion>) q.getResultList();
    }

    /**
     * Devuelve la lista de ubicaciones por padre
     *
     * @param padreId
     * @return
     */
    public List<Ubicacion> findbyPadreUbicacion(Ubicacion padreId) {
        javax.persistence.Query q = em.createQuery("SELECT u FROM Ubicacion u WHERE u.padreId = :padreId");
        q.setParameter("padreId", padreId);
        return (List<Ubicacion>) q.getResultList();
    }

}
