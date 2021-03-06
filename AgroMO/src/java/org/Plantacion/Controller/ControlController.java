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
@Named(value = "controlController")
@ViewScoped
public class ControlController implements Serializable {

    private Plantacion current;
    private PlantacionDetalle currentDetalle;
    private ControlPlantacion currentControl;
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

    private List<Plantacion> allPlantacionItems = null;
    private List<ControlPlantacion> allControlPlantacionItems = null;
    private List<Plantacion> sonFilteredPerfiles;

    static Logger log = Logger.getLogger(ControlController.class.getName());

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

    public ControlController() {

    }

    public void preparePage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Plantacion/Control/List.jsf");
    }

    public Plantacion getSelected() {
        if (current == null) {
            current = new Plantacion();
        }
        return current;
    }

    public void setSelected(Plantacion selected) {

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

    public void changeProducto() {
        if (currentDetalle != null && currentDetalle.getIdDetalleAdquisicionInt() != null) {
            currentDetalle.setIdDetalleAdquisicion(ejbDetalleAdquisicionFacade.findbyId(currentDetalle.getIdDetalleAdquisicionInt()));
            currentDetalle.setMaximo(currentDetalle.getIdDetalleAdquisicion().getCantidadBodegaDetalleAdquisicion());
            currentDetalle.setTipoCantidadPlantacionDetalle(currentDetalle.getIdDetalleAdquisicion().getTipoCantidad());
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

    public void prepareReport(ActionEvent event) {
        current = ejbFacade.findbyId(current.getIdPlantacion());
        try {
            this.jasperReportUtil.generarExtractoContrato(current.getIdPlantacion());
        } catch (ClassNotFoundException ex) {
            System.out.print("No se puede crear el estracto del reporte." + ex.getMessage());
            FacesUtil.mensajeWarnDialog("No se puede crear el pdf.");
        }
    }

    public void prepareCreate(ActionEvent event) {
        current = new Plantacion();
        current.setPlantacionDetalleList(new ArrayList<PlantacionDetalle>());

    }

    public void prepareCreateDetalle(ActionEvent event) {
        setCurrentControl(new ControlPlantacion());
    }

    public void prepareViewDetalle(ActionEvent event) {
        setCurrentControl(new ControlPlantacion());
        setAllControlPlantacionItems(ejbControlPlantacionFacade.findbyIdPlantacionDetalle(currentDetalle.getIdPlantacionDetalle()));
    }

    public void destroy(ActionEvent event) throws SystemException {

        try {
            utx.begin();

            //this.current.setEstadoPerfil(false);
            setEditando(false);
            getFacade().edit(current);
            allPlantacionItems.remove(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro actualizado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " producto actualizado exitosamente");
            current = null;
            currentDetalle = null;
        } catch (Exception e) {

            utx.rollback();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en actualizar producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en actualizar producto");
            current = null;
            currentDetalle = null;
        }

    }

    public void destroyCargaMasiva(ActionEvent event) throws SystemException {

        try {
            utx.begin();

            //this.current.setEstadoPerfil(false);
            setEditando(false);
            getFacade().edit(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro actualizado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " producto actualizado exitosamente");
            current = null;
            currentDetalle = null;
        } catch (Exception e) {

            utx.rollback();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en actualizar producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en actualizar producto");
            current = null;
            currentDetalle = null;
        }

    }

    public void checkAfeccion() {

    }

    public void checkTratamiento() {

    }

    public void checkPerdida() {

    }

    public void prepareSearch(ActionEvent event) {
        current = new Plantacion();

    }

    public void create(ActionEvent event) throws SystemException {
        try {
            utx.begin();

            current.setIdUbicacionInt(current.getIdUbicacion().getIdUbicacion());
            getFacade().create(current);
            getAllPlantacionItems().add(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro creado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " producto creado exitosamente");
            current = null;
            currentDetalle = null;
        } catch (Exception e) {

            utx.rollback();
            current = null;
            currentDetalle = null;
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en crear producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en crear producto");

        }
    }

    public void createDetalle(ActionEvent event) throws SystemException {
        try {
            currentDetalle.setPlantacion(getSelected());
            currentDetalle.setIdTipoSuelo(ejbTipoSueloFacade.findbyId(currentDetalle.getIdTipoSueloInt()));
            Random rnd = new Random();
            currentDetalle.setIdPlantacionDetalle(rnd.nextLong());
            getSelected().getPlantacionDetalleList().add(currentDetalle);
        } catch (Exception e) {

            currentDetalle = null;
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en crear producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en crear producto");

        }
    }

    public void createControlDetalle(ActionEvent event) throws SystemException {
        try {
            utx.begin();
            currentControl.setIdPlantacionDetalle(currentDetalle);
            currentControl.setFechaControlPlantacion(new Date());
            ejbControlPlantacionFacade.create(currentControl);

            if (currentControl.getPerdida() == true) {
                currentDetalle = ejbPlantacionDetalleFacade.findbyId(currentDetalle.getIdPlantacionDetalle());
                currentDetalle.setEstado(2);
                ejbPlantacionDetalleFacade.edit(currentDetalle);
            }
            utx.commit();
            JsfUtil.addSuccessMessage("Registro creado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " producto actualizado exitosamente");
            currentControl = null;
            currentDetalle = null;
        } catch (Exception e) {
            utx.rollback();
            currentDetalle = null;
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en crear producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en crear producto");

        }
    }

    public void updateDetalle() {

        for (PlantacionDetalle item : getSelected().getPlantacionDetalleList()) {
            if (item.getIdPlantacionDetalle().equals(currentDetalle.getIdPlantacionDetalle())) {
                if (getSelected().getPlantacionDetalleList().remove(item)) {
                    getSelected().getPlantacionDetalleList().add(currentDetalle);
                    break;
                }
            }
        }
    }

    public void destroyDetalle() {
        for (PlantacionDetalle item : getSelected().getPlantacionDetalleList()) {
            if (item.getIdPlantacionDetalle().equals(currentDetalle.getIdPlantacionDetalle())) {
                if (getSelected().getPlantacionDetalleList().remove(item)) {
                    break;
                }
            }
        }
    }

    public void search(ActionEvent event) {
        try {
            allPlantacionItems = new ArrayList();
            List<Plantacion> listaPlantacionTemp = getFacade().findbyBusquedaAvanzada(current);
            for (Plantacion item : listaPlantacionTemp) {
                Boolean encontroProducto = false;
                if (current.getProducto() == null || current.getProducto().equals("")) {
                    allPlantacionItems.add(item);
                }
                item.setIdUbicacion(ejbUbicacionFacade.findbyId(item.getIdUbicacionInt()));
                item.setPlantacionDetalleList(ejbPlantacionDetalleFacade.findbyPlantacionId(current.getIdPlantacion()));
                for (PlantacionDetalle itemDetalle : item.getPlantacionDetalleList()) {
                    itemDetalle.setIdDetalleAdquisicion(ejbDetalleAdquisicionFacade.findbyId(itemDetalle.getIdDetalleAdquisicionInt()));
                    if (current.getProducto() != null && !current.getProducto().equals("")) {
                        if (itemDetalle.getIdDetalleAdquisicion().getIdBien().getNombreProducto().toUpperCase().contains(current.getProducto().toUpperCase())) {
                            encontroProducto = true;
                        }
                    }
                }
                if (current.getProducto() != null && !current.getProducto().equals("") && encontroProducto) {
                    allPlantacionItems.add(item);
                }

            }
            current = null;
            currentDetalle = null;
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en encontrar producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en encontrar producto");
        }
    }

    public void prepareEdit(ActionEvent event) {

        setEditando(true);
        current = ejbFacade.findbyId(current.getIdPlantacion());
        current.setIdUbicacion(ejbUbicacionFacade.findbyId(current.getIdUbicacionInt()));
        current.setIdUbicacionPadre(current.getIdUbicacion().getPadreId());
        current.setPlantacionDetalleList(ejbPlantacionDetalleFacade.findbyPlantacionId(current.getIdPlantacion()));
        for (PlantacionDetalle itemDetalle : current.getPlantacionDetalleList()) {
            itemDetalle.setIdDetalleAdquisicion(ejbDetalleAdquisicionFacade.findbyId(itemDetalle.getIdDetalleAdquisicionInt()));
        }

    }

    public void prepareView(ActionEvent event) {

        setEditando(true);
        current = ejbFacade.findbyId(current.getIdPlantacion());
        current.setIdUbicacion(ejbUbicacionFacade.findbyId(current.getIdUbicacionInt()));
        current.setIdUbicacionPadre(current.getIdUbicacion().getPadreId());
        for (PlantacionDetalle itemDetalle : current.getPlantacionDetalleList()) {
            itemDetalle.setIdDetalleAdquisicion(ejbDetalleAdquisicionFacade.findbyId(itemDetalle.getIdDetalleAdquisicionInt()));
        }

    }

    public void prepareEditDetalle(ActionEvent event) {

        setEditando(true);
        currentDetalle.setIdTipoSueloInt(currentDetalle.getIdTipoSuelo().getIdTipoSuelo());

    }

    public void update(ActionEvent event) throws SystemException {
        try {
            utx.begin();

            getFacade().edit(current);

            utx.commit();

            for (Plantacion item : getAllPlantacionItems()) {
                if (item.getIdPlantacion().equals(current.getIdPlantacion())) {
                    if (getAllPlantacionItems().remove(item)) {
                        getAllPlantacionItems().add(current);
                        break;
                    }
                }
            }
            JsfUtil.addSuccessMessage("Registro actualizado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " producto actualizado exitosamente");
            current = null;
            currentDetalle = null;
        } catch (Exception e) {

            utx.rollback();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en actualizar producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en actualizar producto");
            current = null;
            currentDetalle = null;
        }
    }

    public void cancelar() {
        current = null;
        currentDetalle = null;
        setAllControlPlantacionItems(null);
    }

    public void cancelarDetalle(ActionEvent event) {
        currentDetalle = null;
    }

    public void cancelarControlDetalle(ActionEvent event) {
        currentDetalle = null;
        currentControl = null;
    }

    public Plantacion getPlantacion(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @param allPlantacionItems the allPlantacionItems to set
     */
    public void setAllPlantacionItems(List<Plantacion> allPlantacionItems) {
        this.allPlantacionItems = allPlantacionItems;
    }

    /**
     * @return the allPlantacionItems
     */
    public List<Plantacion> getAllPlantacionItems() {

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

    /**
     * @return the currentDetalle
     */
    public PlantacionDetalle getCurrentDetalle() {
        return currentDetalle;
    }

    /**
     * @param currentDetalle the currentDetalle to set
     */
    public void setCurrentDetalle(PlantacionDetalle currentDetalle) {
        this.currentDetalle = currentDetalle;
    }

    /**
     * @return the currentControl
     */
    public ControlPlantacion getCurrentControl() {
        return currentControl;
    }

    /**
     * @param currentControl the currentControl to set
     */
    public void setCurrentControl(ControlPlantacion currentControl) {
        this.currentControl = currentControl;
    }

    /**
     * @return the allControlPlantacionItems
     */
    public List<ControlPlantacion> getAllControlPlantacionItems() {
        return allControlPlantacionItems;
    }

    /**
     * @param allControlPlantacionItems the allControlPlantacionItems to set
     */
    public void setAllControlPlantacionItems(List<ControlPlantacion> allControlPlantacionItems) {
        this.allControlPlantacionItems = allControlPlantacionItems;
    }

}
