/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.servicio;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.Seguridades.Controller.util.JsfUtil;
import org.Seguridades.Entities.SegUsuario;
import org.Seguridades.Facade.SegUsuarioFacade;
import org.springframework.stereotype.Service;

/**
 *
 * @author SergioRios
 */
@Service
public class ServiceLogin implements IfaceLogin {

    //@EJB
    //private org.Seguridades.Facade.SegUsuarioFacade ejbUsuarioFacade;

    @Override
    public SegUsuario validarLogin(SegUsuario obj) throws Exception {
        obj.setPasswordUsuario(JsfUtil.getMD5(obj.getPasswordUsuario()));
        Context context = new InitialContext();
        return ((SegUsuarioFacade) context.lookup("java:global/AgroMO/SegUsuarioFacade")).findbyNombreyPassword(obj.getUsernameUsuario(), obj.getPasswordUsuario());
    }

}
