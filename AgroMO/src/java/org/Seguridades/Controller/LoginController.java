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
import org.Seguridades.Controller.util.JsfUtil;
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
@Named(value =  "loginController")
@SessionScoped
public class LoginController
        implements Serializable {

    /**
     * The user .
     */
    private SegUsuario user;

    /**
     * El rol .
     */
    private SegPerfil rol;

    /**
     * The Menu
     */
    private MenuModel model;

    /**
     * The Breadcrumb
     */
    private MenuModel modelBreadcrumb;

    @EJB
    private SegMenuFacade ejbSegMenuFacade;
    @EJB
    private SegAccionesFacade ejbSegAccionesFacade;
    @EJB
    private org.Seguridades.Facade.SegPerfilFacade ejbSegPerfilFacade;
    @EJB
    private org.Seguridades.Facade.SegUsuarioFacade ejbSegUsuarioFacade;

    private List<SegAcciones> listSegAcciones;

    static Logger log = Logger.getLogger(
            LoginController.class.getName());

    /**
     *
     */
    public LoginController() {
        try {

        } catch (Exception e) {
            // e.printStackTrace();

            log.error("Usuario:" + ((org.Seguridades.details.Login) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() + " error no encontrado", e);
            JsfUtil.addErrorMessage(((org.Seguridades.details.Login) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() + " error no encontrado");
        }
        //getUser().setJsfSpringSecUsersUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());  

    }

    @PostConstruct
    public void init() {
        try {
            String username = ((org.Seguridades.details.Login) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            setUser(getEjbUsuarioFacade().findbyUsername(username));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Usuario:" + ((org.Seguridades.details.Login) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() + " error no encontrado", e);
            JsfUtil.addErrorMessage(((org.Seguridades.details.Login) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() + " error no encontrado");
        }
    }

    /**
     * escogePerfil.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void escogePerfil(ActionEvent event) throws IOException {
        try {

            //getSegMenuController().findbyJerarquia();
            setMenuBean();
            setListSegAcciones(getejbSegAccionesFacade().findByPerfil(rol));
            List<GrantedAuthority> autoridades = new ArrayList<GrantedAuthority>();

            autoridades.add(new SimpleGrantedAuthority(rol.getNombrePerfil()));

            Authentication customAuthentication = new UsernamePasswordAuthenticationToken(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                    SecurityContextHolder.getContext().getAuthentication().getCredentials(), autoridades);

            SecurityContextHolder.getContext().setAuthentication(customAuthentication);
            FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Principal_Menu.jsf");
            log.info("Usuario:" + this.getUser().getUsernameUsuario() + " ha escogido el perfil: " + getRol().getNombrePerfil());

        } catch (AuthenticationException e) {
            // e.printStackTrace();

            log.error("Usuario:" + this.getUser().getUsernameUsuario() + " error en escoger perfil", e);
            JsfUtil.addErrorMessage(this.getUser().getUsernameUsuario() + " error en escoger perfil");
        }
        // return "secured";
    }

    /**
     * escogePerfil.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void iraescogePerfil(ActionEvent event) throws IOException {
        try {

            FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Principal.jsf");
            //log.info("Usuario:" + this.getUser().getUsernameUsuario() +" ha escogido el perfil: "+getRol().getSonDescripcionPerfil());

        } catch (AuthenticationException e) {
            // e.printStackTrace();

            log.error("Usuario:" + this.getUser().getUsernameUsuario() + " error en escoger perfil", e);
            JsfUtil.addErrorMessage(this.getUser().getUsernameUsuario() + " error en escoger perfil");
        }
        // return "secured";
    }

    public void setBreadcrumb(ActionEvent event) {
        try {

            setModelBreadcrumb(new DefaultMenuModel());
//           String []URL =JsfUtil.SplitString(JsfUtil.GetCompleteURI(),"/");

            DefaultMenuItem item = new DefaultMenuItem();
            //item.setUrl("#");
            item.setValue("Home");
            getModelBreadcrumb().addElement(item);
            String buttonId = ((MenuActionEvent) event).getMenuItem().getParams().get("ID").get(0);
            List<Object> ListaPathByHijo = getEjbSegMenuFacade().findPathbyHijo(Integer.parseInt(buttonId));

            for (int i = 0; i < ListaPathByHijo.size(); i++) {
                item = new DefaultMenuItem();
                item.setUrl("#");
                item.setValue(((Object[]) ListaPathByHijo.get(i))[2].toString());
                getModelBreadcrumb().addElement(item);
            }

            String url = ((MenuActionEvent) event).getMenuItem().getParams().get("URL").get(0).toString();
            FacesContext context = FacesContext.getCurrentInstance();
            String result = "";
            if (!url.startsWith("#")) {
                result = url;
            } else {
                result = resolveExpression(url);
            }
            NavigationHandler handler = context.getApplication().getNavigationHandler();
            handler.handleNavigation(context, null, result);
//            context.();
            // FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + ((MenuActionEvent) event).getMenuItem().getParams().get("URL").get(0).toString());

        } catch (Exception e) {
            // e.printStackTrace();

            log.error("Usuario:" + this.getUser().getUsernameUsuario() + " error en breadcrumb", e);
            JsfUtil.addErrorMessage(this.getUser().getUsernameUsuario() + " error en breadcrumb");
        }
        // return "secured";
    }

    private String resolveExpression(String expression) {

        if (expression == null) {
            return null;
        }

        MethodExpression methodExpression = null;
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExpressionFactory factory = fc.getApplication().getExpressionFactory();
            methodExpression = factory.createMethodExpression(fc.getELContext(), expression, String.class, new Class[]{});
            return (String) methodExpression.invoke(fc.getELContext(), new Object[0]);

        } catch (ELException ex) {
        }

        return expression;
    }

    private DefaultSubMenu recurseMenu(String parent, String level, List<Object> ListaJerarquiaDepurada, DefaultSubMenu Submenu) {

        for (Object x : ListaJerarquiaDepurada) {

            if (((Object[]) x)[1].toString().equals(parent)) {

                DefaultSubMenu itemSubmenu = new DefaultSubMenu(((Object[]) x)[2].toString());

                if (((Object[]) x)[7].toString().equals("true")) {

                    Submenu.addElement(recurseMenu(((Object[]) x)[0].toString(), (level + 1).toString(), ListaJerarquiaDepurada, itemSubmenu));

                    //Submenu=itemSubmenu;
                } else if (((Object[]) x)[7].toString().equals("false")) {

                    // DefaultMenuItem  item = new DefaultMenuItem(((Object[])x)[2].toString());
                    DefaultMenuItem item = new DefaultMenuItem(((Object[]) x)[2].toString());

                    // item.setIcon("ui-icon-close");
                    item.setCommand("#{loginController.setBreadcrumb}");
                    // item.setValue(((Object[])x)[0].toString());
                    item.setParam("ID", ((Object[]) x)[0].toString());
                    item.setId(((Object[]) x)[0].toString());
                    //item.setParam("BreadCrumb", ((Object[])x)[2].toString());
                    item.setParam("URL", ((Object[]) x)[5].toString());
                    //item.setUrl("#");
                    //item.setAjax(false);
                    Submenu.addElement(item);
                    //return getSubmenu();

                }

            }

        }
        return Submenu;

    }

    public void setMenuBean() {
        model = new DefaultMenuModel();
        List<Object> ListaJerarquia = getEjbSegMenuFacade().findbyJerarquia();
        SegAcciones SysAcciones = getejbSegAccionesFacade().findBySonAccion("listar pagina");

        List<SegMenu> ListaMenu = new ArrayList<>();

        if (SysAcciones != null && rol != null) {
            ListaMenu = getEjbSegMenuFacade().findbyPermisos(rol.getIdPerfil(), SysAcciones.getIdAcciones());
        }

        List<Object> ListaJerarquiaDepurada = new ArrayList<>();

        for (int i = 0; i < ListaJerarquia.size(); i++) {
            for (SegMenu itemitera : ListaMenu) {
                if (((Object[]) ListaJerarquia.get(i))[0].toString().equals(itemitera.getIdMenu().toString())) {
                    ListaJerarquiaDepurada.add(ListaJerarquia.get(i));
                }

            }
        }

        DefaultSubMenu SubmenuPadre = (recurseMenu("0", "1", ListaJerarquiaDepurada, new DefaultSubMenu()));

        for (MenuElement x : (SubmenuPadre.getElements())) {
            model.addElement(((DefaultSubMenu) x));
        }

//       
//        
//       
//        
//        model.addElement(firstSubmenu);
//		
//		//Second submenu
//		DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions");
//
//		item = new DefaultMenuItem("Save");
//		item.setIcon("ui-icon-disk");
//        item.setCommand("#{menuBean.save}");
//        item.setUpdate("messages");
//		secondSubmenu.addElement(item);
//        
//        item = new DefaultMenuItem("Delete");
//        item.setIcon("ui-icon-close");
//        item.setCommand("#{menuBean.delete}");
//        item.setAjax(false);
//        secondSubmenu.addElement(item);
//        
//        item = new DefaultMenuItem("Redirect");
//        item.setIcon("ui-icon-search");
//        item.setCommand("#{menuBean.redirect}");
//		secondSubmenu.addElement(item);
//
//        model.addElement(secondSubmenu);
    }

    public void GetPagePermissions() {

    }

    /**
     * Cancel.
     *
     * @return the string
     */
    public String cancel() {
        return null;
    }

    /**
     * @return the user
     */
    public SegUsuario getUser() {
        if (this.user == null) {
            user = new SegUsuario();
        }
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(SegUsuario user) {

        this.user = user;
    }

    /**
     * @return the model
     */
    public MenuModel getModel() {
        if (this.model == null) {
            model = new DefaultMenuModel();
        }
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MenuModel model) {
        this.model = model;
    }

    /**
     * @return the rol
     */
    public SegPerfil getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(SegPerfil rol) {
        this.rol = rol;
    }

    /**
     * @return the ejbSegAccionesFacade
     */
    public SegAccionesFacade getejbSegAccionesFacade() {
        return ejbSegAccionesFacade;
    }

    /**
     * @param ejbSegAccionesFacade the ejbSegAccionesFacade to set
     */
    public void setejbSegAccionesFacade(SegAccionesFacade ejbSegAccionesFacade) {
        this.ejbSegAccionesFacade = ejbSegAccionesFacade;
    }

    /**
     * @return the ejbSegMenuFacade
     */
    public SegMenuFacade getEjbSegMenuFacade() {
        return ejbSegMenuFacade;
    }

    /**
     * @param ejbSegMenuFacade the ejbSegMenuFacade to set
     */
    public void setEjbSegMenuFacade(SegMenuFacade ejbSegMenuFacade) {
        this.ejbSegMenuFacade = ejbSegMenuFacade;
    }

    /**
     * @return the ejbSegPerfilFacade
     */
    public org.Seguridades.Facade.SegPerfilFacade getEjbSonPerfilFacade() {
        return ejbSegPerfilFacade;
    }

    /**
     * @return the modelBreadcrumb
     */
    public MenuModel getModelBreadcrumb() {
        if (this.modelBreadcrumb == null) {
            modelBreadcrumb = new DefaultMenuModel();
        }
        return modelBreadcrumb;
    }

    /**
     * @param modelBreadcrumb the modelBreadcrumb to set
     */
    public void setModelBreadcrumb(MenuModel modelBreadcrumb) {
        this.modelBreadcrumb = modelBreadcrumb;
    }

    /**
     * @return the ejbSegUsuarioFacade
     */
    public org.Seguridades.Facade.SegUsuarioFacade getEjbUsuarioFacade() {
        return ejbSegUsuarioFacade;
    }

    /**
     * @param ejbSegUsuarioFacade the ejbSegUsuarioFacade to set
     */
    public void setEjbUsuarioFacade(org.Seguridades.Facade.SegUsuarioFacade ejbSegUsuarioFacade) {
        this.ejbSegUsuarioFacade = ejbSegUsuarioFacade;
    }

    /**
     * @return the listSegAcciones
     */
    public List<SegAcciones> getListSegAcciones() {
        return listSegAcciones;
    }

    /**
     * @param listSegAcciones the listSegAcciones to set
     */
    public void setListSegAcciones(List<SegAcciones> listSegAcciones) {
        this.listSegAcciones = listSegAcciones;
    }

}
