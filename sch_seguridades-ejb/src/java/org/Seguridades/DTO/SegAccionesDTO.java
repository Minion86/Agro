/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.DTO;

import org.Seguridades.Entities.SegAcciones;

/**
 *
 * @author Nelson
 */
public class SegAccionesDTO {

    private SegAcciones accion;
    private boolean existeAsignadoPerfil;
    private boolean existeAsignadoMenu;

    public SegAccionesDTO(SegAcciones accion, boolean existeAsignadoPerfil, boolean existeAsignadoMenu) {
        this.accion = accion;
        this.existeAsignadoPerfil = existeAsignadoPerfil;
        this.existeAsignadoMenu = existeAsignadoMenu;
    }

    /**
     * @return the accion
     */
    public SegAcciones getAccion() {
        return accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(SegAcciones accion) {
        this.accion = accion;
    }

    /**
     * @return the existeAsignadoPerfil
     */
    public boolean isExisteAsignadoPerfil() {
        return existeAsignadoPerfil;
    }

    /**
     * @param existeAsignadoPerfil the existeAsignadoPerfil to set
     */
    public void setExisteAsignadoPerfil(boolean existeAsignadoPerfil) {
        this.existeAsignadoPerfil = existeAsignadoPerfil;
    }

    /**
     * @return the existeAsignadoMenu
     */
    public boolean isExisteAsignadoMenu() {
        return existeAsignadoMenu;
    }

    /**
     * @param existeAsignadoMenu the existeAsignadoMenu to set
     */
    public void setExisteAsignadoMenu(boolean existeAsignadoMenu) {
        this.existeAsignadoMenu = existeAsignadoMenu;
    }

}
