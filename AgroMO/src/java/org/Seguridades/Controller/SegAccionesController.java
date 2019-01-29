package org.Seguridades.Controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import org.Seguridades.Controller.util.JsfUtil;
import org.Seguridades.Entities.SegAcciones;

@Named(value="segAccionesController")
@SessionScoped
public class SegAccionesController implements Serializable {

    private List<SegAcciones> listSegAcciones;
   
    @EJB
    private org.Seguridades.Facade.SegAccionesFacade ejbSegAccionesFacade;
    

    public SegAccionesController() {
    }



    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getEjbSegAccionesFacade().findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getEjbSegAccionesFacade().findAll(), true);
    }

    public SegAcciones getSegAcciones(java.lang.Integer id) {
        return getEjbSegAccionesFacade().find(id);
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

    /**
     * @return the ejbSegAccionesFacade
     */
    public org.Seguridades.Facade.SegAccionesFacade getEjbSegAccionesFacade() {
        return ejbSegAccionesFacade;
    }

    /**
     * @param ejbSegAccionesFacade the ejbSegAccionesFacade to set
     */
    public void setEjbSegAccionesFacade(org.Seguridades.Facade.SegAccionesFacade ejbSegAccionesFacade) {
        this.ejbSegAccionesFacade = ejbSegAccionesFacade;
    }

    @FacesConverter(forClass = SegAcciones.class)
    public static class SegAccionesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SegAccionesController controller = (SegAccionesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "segAccionesController");
            return controller.getSegAcciones(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SegAcciones) {
                SegAcciones o = (SegAcciones) object;
                return getStringKey(o.getIdAcciones());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SegAcciones.class.getName());
            }
        }

    }

}
