package org.Adquisicion.Controller;

import java.io.IOException;
import org.Seguridades.Controller.*;
import org.Adquisicion.Entities.Bodega;
import org.Seguridades.Controller.util.JsfUtil;
import org.Adquisicion.Facade.BodegaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.apache.log4j.Logger;

/**
 *
 * @author nmartinez
 */
@Named(value = "bodegaController")
@ViewScoped
public class BodegaController implements Serializable {

    private Bodega current;
    @EJB
    private org.Adquisicion.Facade.BodegaFacade ejbFacade;
    @EJB
    private org.Adquisicion.Facade.CatalogoFacade ejbCatalogoFacade;
    @EJB
    private org.Adquisicion.Facade.DetalleAdquisicionFacade ejbDetalleAdquisicionFacade;
    private List<Bodega> allBodegaItems = null;
    private List<Bodega> sonFilteredPerfiles;

    static Logger log = Logger.getLogger(BodegaController.class.getName());

    @Inject
    private LoginController loginController;

    @Resource
    private UserTransaction utx;

    private boolean Editando;

    @EJB
    private org.Seguridades.Facade.SegAccionMenuPerfilFacade ejbSegAccionMenuPerfilFacade;

    private boolean permisoInsertar = false;
    private boolean permisoActualizar = false;
    private boolean permisoEliminar = false;
    private boolean permisoImprimir = false;
    private boolean permisoListarPagina = false;
    private boolean permisoBuscar = false;

    public BodegaController() {

    }

    public void preparePage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Adquisicion/Bodega/List.jsf");
    }

    public Bodega getSelected() {
        if (current == null) {
            current=new Bodega();
        }
        return current;
    }

    public void setSelected(Bodega selected) {

        current = selected;

    }

    @PostConstruct
    private void verificarPermisos() {

        List<SegAccionMenuPerfil> listaPermisos = ejbSegAccionMenuPerfilFacade.findbyMenuPerfil("Bodegas", loginController.getRol());
        for (SegAccionMenuPerfil segAccionMenuPerfil : listaPermisos) {
            if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("insertar")) {
                permisoInsertar = true;
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("actualizar")) {
                permisoActualizar = true;
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("eliminar")) {
                permisoEliminar = true;
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("imprimir")) {
                permisoImprimir = true;
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("listar pagina")) {
                permisoListarPagina = true;
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("buscar")) {
                permisoBuscar = true;
            }
        }

    }

    private BodegaFacade getFacade() {
        return ejbFacade;
    }

    public String prepareView() {
        //current = (Bodega) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate(ActionEvent event) {
        current = new Bodega();
        setEditando(false);
        if (current != null) {
            //current.setIdUbicacionPadre(current.getIdUbicacion().getPadreId());
        }

    }

    public void cancelar(ActionEvent event) {
        current = null;
    }

    public void destroy(ActionEvent event) throws SystemException {

        try {
            utx.begin();

            this.current.setEstadoBodega(false);
            setEditando(false);
            getFacade().edit(current);
            allBodegaItems.remove(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro actualizado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " bodega actualizado exitosamente");
            current = null;
        } catch (Exception e) {

            utx.rollback();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en actualizar bodega", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en actualizar bodega");
            current = null;
        }

    }

    public void prepareSearch(ActionEvent event) {
        current = new Bodega();

    }

    public void create(ActionEvent event) throws SystemException {
        try {
            utx.begin();
            current.setClaseBodega(ejbCatalogoFacade.findbyId(current.getClaseBodegaInt()));
            current.setTipoBodega(ejbCatalogoFacade.findbyId(current.getTipoBodegaInt()));
            this.current.setEstadoBodega(true);
            getFacade().create(current);
            getAllBodegaItems().add(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro creado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " bodega creado exitosamente");
            current = null;
        } catch (Exception e) {

            utx.rollback();
            current = null;
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en crear bodega", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en crear bodega");

        }
    }

    public void search(ActionEvent event) {
        try {
            allBodegaItems = getFacade().findbyBusquedaAvanzada(current);
            current = null;
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en encontrar bodega", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en encontrar bodega");
        }
    }

    public void prepareEdit(ActionEvent event) {

        setEditando(true);
        if (current != null) {
            current.setClaseBodegaInt(current.getClaseBodega().getIdCatalogo());
            current.setTipoBodegaInt(current.getTipoBodega().getIdCatalogo());
            current.setIdUbicacionPadre(current.getIdUbicacion().getPadreId());
            current.setListaDetalleAdquisicion(ejbDetalleAdquisicionFacade.findPorBodega(current));
        }
    }

    public void update(ActionEvent event) throws SystemException {
        try {
            utx.begin();
            current.setClaseBodega(ejbCatalogoFacade.findbyId(current.getClaseBodegaInt()));
            current.setTipoBodega(ejbCatalogoFacade.findbyId(current.getTipoBodegaInt()));

            getFacade().edit(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro actualizado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " bodega actualizado exitosamente");
            current = null;
        } catch (Exception e) {

            utx.rollback();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en actualizar bodega", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en actualizar bodega");
            current = null;
        }
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Bodega getBodega(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @param allBodegaItems the allBodegaItems to set
     */
    public void setAllBodegaItems(List<Bodega> allBodegaItems) {
        this.allBodegaItems = allBodegaItems;
    }

    /**
     * @return the allBodegaItems
     */
    public List<Bodega> getAllBodegaItems() {

        if (allBodegaItems == null) {
            allBodegaItems = new ArrayList<>();
        }
        return allBodegaItems;
    }

    /**
     * @return the loginController
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * @param loginController the loginController to set
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * @return the Editando
     */
    public boolean isEditando() {
        return Editando;
    }

    /**
     * @param Editando the Editando to set
     */
    public void setEditando(boolean Editando) {
        this.Editando = Editando;
    }

    /**
     * @return the sonFilteredPerfiles
     */
    public List<Bodega> getSonFilteredPerfiles() {
        return sonFilteredPerfiles;
    }

    /**
     * @param sonFilteredPerfiles the sonFilteredPerfiles to set
     */
    public void setSonFilteredPerfiles(List<Bodega> sonFilteredPerfiles) {
        this.sonFilteredPerfiles = sonFilteredPerfiles;
    }

    /**
     * @return the permisoInsertar
     */
    public boolean isPermisoInsertar() {
        return permisoInsertar;
    }

    /**
     * @param permisoInsertar the permisoInsertar to set
     */
    public void setPermisoInsertar(boolean permisoInsertar) {
        this.permisoInsertar = permisoInsertar;
    }

    /**
     * @return the permisoActualizar
     */
    public boolean isPermisoActualizar() {
        return permisoActualizar;
    }

    /**
     * @param permisoActualizar the permisoActualizar to set
     */
    public void setPermisoActualizar(boolean permisoActualizar) {
        this.permisoActualizar = permisoActualizar;
    }

    /**
     * @return the permisoEliminar
     */
    public boolean isPermisoEliminar() {
        return permisoEliminar;
    }

    /**
     * @param permisoEliminar the permisoEliminar to set
     */
    public void setPermisoEliminar(boolean permisoEliminar) {
        this.permisoEliminar = permisoEliminar;
    }

    /**
     * @return the permisoImprimir
     */
    public boolean isPermisoImprimir() {
        return permisoImprimir;
    }

    /**
     * @param permisoImprimir the permisoImprimir to set
     */
    public void setPermisoImprimir(boolean permisoImprimir) {
        this.permisoImprimir = permisoImprimir;
    }

    /**
     * @return the permisoListarPagina
     */
    public boolean isPermisoListarPagina() {
        return permisoListarPagina;
    }

    /**
     * @param permisoListarPagina the permisoListarPagina to set
     */
    public void setPermisoListarPagina(boolean permisoListarPagina) {
        this.permisoListarPagina = permisoListarPagina;
    }

    /**
     * @return the permisoBuscar
     */
    public boolean isPermisoBuscar() {
        return permisoBuscar;
    }

    /**
     * @param permisoBuscar the permisoBuscar to set
     */
    public void setPermisoBuscar(boolean permisoBuscar) {
        this.permisoBuscar = permisoBuscar;
    }

}
