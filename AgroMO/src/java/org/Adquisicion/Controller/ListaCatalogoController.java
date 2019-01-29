package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.Adquisicion.Entities.Catalogo;

/**
 *
 * @author nmartinez
 */
@Named(value =  "listaCatalogoController")
@ViewScoped
public class ListaCatalogoController implements Serializable {

    @EJB
    private org.Adquisicion.Facade.CatalogoFacade ejbCatalogoFacade;

    private List<Catalogo> catalogoItems;

    public ListaCatalogoController() {

    }

    public List<Catalogo> getbyGrupoCatalogoNemonico(String grupoCatalogoNemonico) {

        catalogoItems = ejbCatalogoFacade.findbyGrupoCatalogoNemonico(grupoCatalogoNemonico);
        return catalogoItems;
    }

    public Catalogo getCatalogo(Integer id) {
        return ejbCatalogoFacade.find(id);
    }

}
