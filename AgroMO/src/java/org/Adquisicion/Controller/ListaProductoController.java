package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.Adquisicion.Entities.Producto;

/**
 *
 * @author nmartinez
 */
@Named(value = "listaProductoController")
@ViewScoped
public class ListaProductoController implements Serializable {

    @EJB
    private org.Adquisicion.Facade.ProductoFacade ejbProductoFacade;

    private List<SelectItem> catalogoItems;

    public ListaProductoController() {

    }

    public List<SelectItem> getbyAll() {

        if (catalogoItems == null) {
            List<Producto> lista = ejbProductoFacade.findTodos();
            catalogoItems = new ArrayList<>();
            catalogoItems.add(new SelectItem(null, "Por favor escoja un valor"));

            for (Producto c : lista) {
                catalogoItems.add(new SelectItem(c.getIdProducto(), c.getNombreProducto()));
            }
        }
        return catalogoItems;
    }

    public Producto getProducto(Integer id) {
        return ejbProductoFacade.find(id);
    }

}
