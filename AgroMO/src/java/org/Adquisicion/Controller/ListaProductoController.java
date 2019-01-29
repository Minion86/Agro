package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.Adquisicion.Entities.Producto;

/**
 *
 * @author nmartinez
 */
@Named(value =  "listaProductoController")
@ViewScoped
public class ListaProductoController implements Serializable {

    @EJB
    private org.Adquisicion.Facade.ProductoFacade ejbProductoFacade;

    private List<Producto> catalogoItems;

    public ListaProductoController() {

    }

    public List<Producto> getbyAll() {

        catalogoItems = ejbProductoFacade.findTodos();
        return catalogoItems;
    }

    public Producto getProducto(Integer id) {
        return ejbProductoFacade.find(id);
    }

}
