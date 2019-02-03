package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.Adquisicion.Entities.DetalleAdquisicion;

/**
 *
 * @author nmartinez
 */
@Named(value = "listaDetalleAdquisicionController")
@ViewScoped
public class ListaDetalleAdquisicionController implements Serializable {

    @EJB
    private org.Adquisicion.Facade.DetalleAdquisicionFacade ejbDetalleAdquisicionFacade;

    private List<SelectItem> catalogoItems;

    public ListaDetalleAdquisicionController() {

    }

    public List<SelectItem> getbyAll() {

        if (catalogoItems == null) {
            List<DetalleAdquisicion> lista = ejbDetalleAdquisicionFacade.findTodos();
            catalogoItems = new ArrayList<>();
            catalogoItems.add(new SelectItem(null, "Por favor escoja un valor"));

            for (DetalleAdquisicion c : lista) {
                catalogoItems.add(new SelectItem(c.getIdDetalleAdquisicion(), c.getIdBien().getNombreProducto()));
            }
        }
        return catalogoItems;
    }

    public DetalleAdquisicion getDetalleAdquisicion(Integer id) {
        return ejbDetalleAdquisicionFacade.find(id);
    }

}
