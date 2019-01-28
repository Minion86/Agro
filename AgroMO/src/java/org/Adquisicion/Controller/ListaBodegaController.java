package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.Adquisicion.Entities.Bodega;

/**
 *
 * @author nmartinez
 */
@ManagedBean(name = "listaBodegaController")
@ViewScoped
public class ListaBodegaController implements Serializable {

    @EJB
    private org.Adquisicion.Facade.BodegaFacade ejbBodegaFacade;

    private List<Bodega> catalogoItems;

    public ListaBodegaController() {

    }

    public List<Bodega> getbyAll() {

        catalogoItems = ejbBodegaFacade.findTodos();
        return catalogoItems;
    }

    public Bodega getBodega(Integer id) {
        return ejbBodegaFacade.find(id);
    }

}
