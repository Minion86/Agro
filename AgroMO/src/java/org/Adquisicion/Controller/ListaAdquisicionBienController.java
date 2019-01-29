package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.Adquisicion.Entities.DetalleAdquisicion;

/**
 *
 * @author nmartinez
 */
@Named(value =  "listaAdquisicionBienController")
@ViewScoped
public class ListaAdquisicionBienController implements Serializable {

    @EJB
    private org.Adquisicion.Facade.DetalleAdquisicionFacade ejbDetalleAdquisicionFacade;

    private List<DetalleAdquisicion> catalogoItems;

    public ListaAdquisicionBienController() {

    }

    public List<DetalleAdquisicion> getbyAll() {

        catalogoItems = ejbDetalleAdquisicionFacade.findTodos();
        return catalogoItems;
    }

    public DetalleAdquisicion getDetalleAdquisicion(Integer id) {
        return ejbDetalleAdquisicionFacade.find(id);
    }

}
