package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.Adquisicion.Entities.Ubicacion;

/**
 *
 * @author nmartinez
 */
@ManagedBean(name = "listaUbicacionController")
@ViewScoped
public class ListaUbicacionController implements Serializable {

    @EJB
    private org.Adquisicion.Facade.UbicacionFacade ejbUbicacionFacade;

    private List<Ubicacion> catalogoItems;

    public ListaUbicacionController() {

    }

    public List<Ubicacion> getbyPadreUbicacion(Ubicacion ubicacionPadre) {
        if (ubicacionPadre != null) {
            catalogoItems = ejbUbicacionFacade.findbyPadreUbicacion(ubicacionPadre);
        } else {
            catalogoItems = new ArrayList<>();
        }
        return catalogoItems;
    }

    public List<Ubicacion> getbyNivelUbicacion(Integer nivel) {

        catalogoItems = ejbUbicacionFacade.findbyNivelUbicacion(nivel);
        return catalogoItems;
    }

    public Ubicacion getUbicacion(Integer id) {
        return ejbUbicacionFacade.find(id);
    }

}
