/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.NavigationHandler;
//import javax.faces.bean.RequestScoped;
//import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.Plantacion.Controller.ControlController;
import org.Seguridades.Controller.util.JsfUtil;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.Seguridades.Entities.SegAcciones;
import org.Seguridades.Entities.SegMenu;
import org.Seguridades.Entities.SegPerfil;
import org.Seguridades.Entities.SegUsuario;
import org.Seguridades.Facade.SegAccionesFacade;
import org.Seguridades.Facade.SegMenuFacade;
import org.apache.log4j.Logger;
import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The Class LoginBean.
 */
@Named(value = "dashboardController")
@SessionScoped
public class DashboardController
        implements Serializable {

    static Logger log = Logger.getLogger(DashboardController.class.getName());

    @EJB
    private org.Seguridades.Facade.SegAccionMenuPerfilFacade ejbSegAccionMenuPerfilFacade;

    private boolean permisoListarPagina = false;

    @Inject
    private LoginController loginController;

    /**
     *
     */
    public DashboardController() {

    }

    @PostConstruct
    private void verificarPermisos() {

        if (getLoginController().getRol() != null) {
            permisoListarPagina = true;
        }
    }

    /**
     * @return the permisoListarPagina
     */
    public boolean isPermisoListarPagina() {
        return permisoListarPagina;
    }

    /**
     * @param permisoListarPagina the permisoListarPagina to set
     */
    public void setPermisoListarPagina(boolean permisoListarPagina) {
        this.permisoListarPagina = permisoListarPagina;
    }

    /**
     * @return the loginController
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * @param loginController the loginController to set
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}
