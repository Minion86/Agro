package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.Adquisicion.Entities.Producto;

/**
 *
 * @author nmartinez
 */
@ManagedBean(name = "listaProductoController")
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
