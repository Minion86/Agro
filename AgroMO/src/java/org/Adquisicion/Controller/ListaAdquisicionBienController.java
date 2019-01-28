package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.Adquisicion.Entities.DetalleAdquisicion;

/**
 *
 * @author nmartinez
 */
@ManagedBean(name = "listaAdquisicionBienController")
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
