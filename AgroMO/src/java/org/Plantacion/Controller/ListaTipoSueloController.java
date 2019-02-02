package org.Plantacion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.Plantacion.Entities.TipoSuelo;

/**
 *
 * @author nmartinez
 */
@Named(value = "listaTipoSueloController")
@ViewScoped
public class ListaTipoSueloController implements Serializable {

    @EJB
    private org.Plantacion.Facade.TipoSueloFacade ejbTipoSueloFacade;

    private List<SelectItem> catalogoItems;

    public ListaTipoSueloController() {

    }

    public List<SelectItem> getbyAll() {
        if (catalogoItems == null) {
            List<TipoSuelo> lista = ejbTipoSueloFacade.findTodos();
            catalogoItems = new ArrayList<>();
            catalogoItems.add(new SelectItem(null, "Por favor escoja un valor"));

            for (TipoSuelo c : lista) {
                catalogoItems.add(new SelectItem(c.getIdTipoSuelo(), c.getNombreTipoSuelo()));
            }
        }
        return catalogoItems;
    }

    public TipoSuelo getTipoSuelo(Integer id) {
        return ejbTipoSueloFacade.find(id);
    }

}
