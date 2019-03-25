package org.Plantacion.Controller;

import java.io.IOException;
import org.Seguridades.Controller.*;
import org.Plantacion.Entities.Plantacion;
import org.Seguridades.Controller.util.JsfUtil;
import org.Plantacion.Facade.PlantacionFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.Plantacion.Entities.PlantacionDetalle;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import org.Plantacion.Entities.ControlPlantacion;
import org.Plantacion.util.FacesUtil;
import org.Plantacion.util.JasperReportUtil;

/**
 *
 * @author nmartinez
 */
@Named(value = "proyeccionController")
@ViewScoped
public class ProyeccionController implements Serializable {

    private PlantacionDetalle current;
    @Inject
    protected JasperReportUtil jasperReportUtil;
    @EJB
    private org.Plantacion.Facade.PlantacionFacade ejbFacade;
    @EJB
    private org.Adquisicion.Facade.DetalleAdquisicionFacade ejbDetalleAdquisicionFacade;
    @EJB
    private org.Adquisicion.Facade.UbicacionFacade ejbUbicacionFacade;
    @EJB
    private org.Plantacion.Facade.TipoSueloFacade ejbTipoSueloFacade;
    @EJB
    private org.Plantacion.Facade.ControlPlantacionFacade ejbControlPlantacionFacade;
    @EJB
    private org.Plantacion.Facade.PlantacionDetalleFacade ejbPlantacionDetalleFacade;

    private List<PlantacionDetalle> allPlantacionItems = null;
    private List<Plantacion> sonFilteredPerfiles;

    static Logger log = Logger.getLogger(ProyeccionController.class.getName());

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

    public ProyeccionController() {

    }

    public void preparePage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Plantacion/Proyeccion/List.jsf");
    }

    public PlantacionDetalle getSelected() {
        if (current == null) {
            current = new PlantacionDetalle();
        }
        return current;
    }

    public void setSelected(PlantacionDetalle selected) {

        current = selected;

    }

    @PostConstruct
    private void verificarPermisos() {

        List<SegAccionMenuPerfil> listaPermisos = ejbSegAccionMenuPerfilFacade.findbyMenuPerfil("Control", loginController.getRol());
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

    private PlantacionFacade getFacade() {
        return ejbFacade;
    }

    public String prepareView() {
        //current = (Plantacion) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareSearch(ActionEvent event) {
        current = new PlantacionDetalle();

    }

    public void search(ActionEvent event) {
        try {
            allPlantacionItems = new ArrayList();
            List<PlantacionDetalle> listaPlantacionTemp = ejbPlantacionDetalleFacade.findbyBusquedaAvanzada(current);
            for (PlantacionDetalle item : listaPlantacionTemp) {
                Boolean encontroProducto = false;
                if (current.getProducto() == null || current.getProducto().equals("")) {
                    allPlantacionItems.add(item);
                }
                item.setIdUbicacion(ejbUbicacionFacade.findbyId(item.getPlantacion().getIdUbicacionInt()));
                current.setIdDetalleAdquisicion(ejbDetalleAdquisicionFacade.findbyId(current.getIdDetalleAdquisicionInt()));
                if (current.getProducto() != null && !current.getProducto().equals("")) {
                    if (current.getIdDetalleAdquisicion().getIdBien().getNombreProducto().toUpperCase().contains(current.getProducto().toUpperCase())) {
                        encontroProducto = true;
                    }
                }

                if (current.getProducto() != null && !current.getProducto().equals("") && encontroProducto) {
                    allPlantacionItems.add(item);
                }
            }

            current = null;
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en encontrar producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en encontrar producto");
        }
    }

    public void prepareView(ActionEvent event) {

        setEditando(true);
        current = ejbPlantacionDetalleFacade.findbyId(current.getIdPlantacionDetalle());

    }

    public void cancelar() {
        current = null;
    }

    /**
     * @param allPlantacionItems the allPlantacionItems to set
     */
    public void setAllPlantacionItems(List<PlantacionDetalle> allPlantacionItems) {
        this.allPlantacionItems = allPlantacionItems;
    }

    /**
     * @return the allPlantacionItems
     */
    public List<PlantacionDetalle> getAllPlantacionItems() {

        if (allPlantacionItems == null) {
            allPlantacionItems = new ArrayList<>();
        }
        return allPlantacionItems;
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
    public List<Plantacion> getSonFilteredPerfiles() {
        return sonFilteredPerfiles;
    }

    /**
     * @param sonFilteredPerfiles the sonFilteredPerfiles to set
     */
    public void setSonFilteredPerfiles(List<Plantacion> sonFilteredPerfiles) {
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
