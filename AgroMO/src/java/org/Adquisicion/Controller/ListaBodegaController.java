package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.Adquisicion.Entities.Bodega;

/**
 *
 * @author nmartinez
 */
@Named(value = "listaBodegaController")
@ViewScoped
public class ListaBodegaController implements Serializable {

    @EJB
    private org.Adquisicion.Facade.BodegaFacade ejbBodegaFacade;

    private List<SelectItem> catalogoItems;

    public ListaBodegaController() {

    }

    public List<SelectItem> getbyAll() {
        if (catalogoItems == null) {
            List<Bodega> lista = ejbBodegaFacade.findTodos();
            catalogoItems = new ArrayList<>();
            catalogoItems.add(new SelectItem(null, "Por favor escoja un valor"));

            for (Bodega c : lista) {
                catalogoItems.add(new SelectItem(c.getIdBodega(), c.getNombreBodega()));
            }
        }
        return catalogoItems;
    }

    public Bodega getBodega(Integer id) {
        return ejbBodegaFacade.find(id);
    }

}
