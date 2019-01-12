/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.servicio;

import org.Seguridades.Entities.SegUsuario;


/**
 *
 * @author SergioRios
 */
public interface IfaceLogin {
    SegUsuario validarLogin(SegUsuario obj) throws Exception;
}
