package org.Seguridades.Controller;

import org.Seguridades.Entities.SegPerfil;
import org.Seguridades.Controller.util.JsfUtil;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.Seguridades.Entities.SegUsuario;

/**
 *
 * @author nmartinez
 */
@ManagedBean(name = "segListaPerfilController")
@ViewScoped
public class SegListaPerfilController implements Serializable {

    @EJB
    private org.Seguridades.Facade.SegPerfilFacade ejbSegPerfilFacade;

    private List<SegPerfil> sonAllPerfilItems = null;

    private List<SegPerfil> sonPerfilItems = null;

    public SegListaPerfilController() {

    }

    public List<SegPerfil> getSegPerfilItemsbyPermisos(SegUsuario user) {

        sonPerfilItems = getEjbSegPerfilFacade().findbyPermisos(user);
        return sonPerfilItems;
    }

    public List<SegPerfil> getSegPerfilItemsbyPermisosUsername(String username) {

        sonPerfilItems = getEjbSegPerfilFacade().findbyPermisosUsername(username);
        return sonPerfilItems;
    }

    public List<SegPerfil> getSegPerfilItemsAll() {

        sonPerfilItems = getEjbSegPerfilFacade().findbyActivos();
        return sonPerfilItems;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getEjbSegPerfilFacade().findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getEjbSegPerfilFacade().findAll(), true);
    }

    public SegPerfil getSegPerfil(java.lang.Integer id) {
        return getEjbSegPerfilFacade().find(id);
    }

    /**
     * @param sonAllPerfilItems the sonAllPerfilItems to set
     */
    public void setSonAllPerfilItems(List<SegPerfil> sonAllPerfilItems) {
        this.sonAllPerfilItems = sonAllPerfilItems;
    }

    /**
     * @return the sonAllPerfilItems
     */
    public List<SegPerfil> getSonAllPerfilItems() {

        sonAllPerfilItems = getEjbSegPerfilFacade().findAll();
        return sonAllPerfilItems;
    }

    /**
     * @return the ejbSegPerfilFacade
     */
    public org.Seguridades.Facade.SegPerfilFacade getEjbSegPerfilFacade() {
        return ejbSegPerfilFacade;
    }

    /**
     * @param ejbSegPerfilFacade the ejbSegPerfilFacade to set
     */
    public void setEjbSegPerfilFacade(org.Seguridades.Facade.SegPerfilFacade ejbSegPerfilFacade) {
        this.ejbSegPerfilFacade = ejbSegPerfilFacade;
    }

//@FacesConverter(forClass = SegPerfil.class)
}
