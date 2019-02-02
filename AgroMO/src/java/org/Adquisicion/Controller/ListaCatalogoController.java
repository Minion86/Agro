package org.Adquisicion.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.Adquisicion.Entities.Catalogo;

/**
 *
 * @author nmartinez
 */
@Named(value = "listaCatalogoController")
@ViewScoped
public class ListaCatalogoController implements Serializable {

    @EJB
    private org.Adquisicion.Facade.CatalogoFacade ejbCatalogoFacade;

    private List<SelectItem> catalogoItemsTipoProducto;

    private List<SelectItem> catalogoItemsEstadoBien;

    private List<SelectItem> catalogoItemsTipoBodega;

    private List<SelectItem> catalogoItemsClaseBodega;

    public ListaCatalogoController() {

    }

    public List<SelectItem> getbyGrupoCatalogoNemonicoTipoProducto(String grupoCatalogoNemonico) {

        if (catalogoItemsTipoProducto == null) {
            List<Catalogo> lista = ejbCatalogoFacade.findbyGrupoCatalogoNemonico(grupoCatalogoNemonico);
            catalogoItemsTipoProducto = new ArrayList<>();
            catalogoItemsTipoProducto.add(new SelectItem(null, "Por favor escoja un valor"));

            for (Catalogo c : lista) {
                catalogoItemsTipoProducto.add(new SelectItem(c.getIdCatalogo(), c.getNombreCatalogo()));
            }
        }
        return catalogoItemsTipoProducto;
    }

    public List<SelectItem> getbyGrupoCatalogoNemonicoEstadoBien(String grupoCatalogoNemonico) {

        if (catalogoItemsEstadoBien == null) {
            List<Catalogo> lista = ejbCatalogoFacade.findbyGrupoCatalogoNemonico(grupoCatalogoNemonico);
            catalogoItemsEstadoBien = new ArrayList<>();
            catalogoItemsEstadoBien.add(new SelectItem(null, "Por favor escoja un valor"));

            for (Catalogo c : lista) {
                catalogoItemsEstadoBien.add(new SelectItem(c.getIdCatalogo(), c.getNombreCatalogo()));
            }
        }
        return catalogoItemsEstadoBien;
    }

    public List<SelectItem> getbyGrupoCatalogoNemonicoTipoBodega(String grupoCatalogoNemonico) {

        if (catalogoItemsTipoBodega == null) {
            List<Catalogo> lista = ejbCatalogoFacade.findbyGrupoCatalogoNemonico(grupoCatalogoNemonico);
            catalogoItemsTipoBodega = new ArrayList<>();
            catalogoItemsTipoBodega.add(new SelectItem(null, "Por favor escoja un valor"));

            for (Catalogo c : lista) {
                catalogoItemsTipoBodega.add(new SelectItem(c.getIdCatalogo(), c.getNombreCatalogo()));
            }
        }
        return catalogoItemsTipoBodega;
    }

    public List<SelectItem> getbyGrupoCatalogoNemonicoClaseBodega(String grupoCatalogoNemonico) {

        if (catalogoItemsClaseBodega == null) {
            List<Catalogo> lista = ejbCatalogoFacade.findbyGrupoCatalogoNemonico(grupoCatalogoNemonico);
            catalogoItemsClaseBodega = new ArrayList<>();
            catalogoItemsClaseBodega.add(new SelectItem(null, "Por favor escoja un valor"));

            for (Catalogo c : lista) {
                catalogoItemsClaseBodega.add(new SelectItem(c.getIdCatalogo(), c.getNombreCatalogo()));
            }
        }
        return catalogoItemsClaseBodega;
    }

    public Catalogo getCatalogo(Integer id) {
        return ejbCatalogoFacade.find(id);
    }

}
