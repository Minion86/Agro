package org.Adquisicion.Controller;

import java.io.IOException;
import org.Seguridades.Controller.*;
import org.Seguridades.Controller.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.transaction.UserTransaction;
import org.Adquisicion.Controller.util.UtilReporte;
import org.Adquisicion.Entities.Producto;
import org.Adquisicion.Entities.DetalleAdquisicion;
import org.Adquisicion.Facade.DetalleAdquisicionFacade;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author nmartinez
 */
@ManagedBean(name = "existenciasController")
@ViewScoped
public class ExistenciasController extends UtilReporte implements Serializable {

    private DetalleAdquisicion current;
    @EJB
    private org.Adquisicion.Facade.DetalleAdquisicionFacade ejbFacade;

    private List<DetalleAdquisicion> allDetalleAdquisicionItems = null;

    static Logger log = Logger.getLogger(ExistenciasController.class.getName());

    @ManagedProperty(value = "#{loginController}")
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

    private TreeNode model;

    public ExistenciasController() {

    }

    public void preparePage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Adquisicion/AdquisicionCodigoBarras/List.jsf");
    }

    public DetalleAdquisicion getSelected() {
        if (current == null) {
        }
        return current;
    }

    public void setSelected(DetalleAdquisicion selected) {

        current = selected;

    }

    public void verFlujo() {
        if (current != null) {
            setModel(new DefaultTreeNode());
        }
    }

    public void reporteBusqueda() {
        if (current != null) {

        }
    }

    @PostConstruct
    private void verificarPermisos() {

        List<SegAccionMenuPerfil> listaPermisos = ejbSegAccionMenuPerfilFacade.findbyMenuPerfil("Reporte Existencias con Códigos de Barras", loginController.getRol());
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

    private DetalleAdquisicionFacade getFacade() {
        return ejbFacade;
    }

    public String prepareView() {
        //current = (DetalleAdquisicion) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

   
    public void prepareSearch(ActionEvent event) {
        current = new DetalleAdquisicion();
        current.setIdBien(new Producto());
    }

    public void search(ActionEvent event) {
        try {
            allDetalleAdquisicionItems = getFacade().findbyBusquedaAvanzada(current);
            current = null;
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en encontrar existencias", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en encontrar existencias");
        }
    }

    public DetalleAdquisicion getDetalleAdquisicion(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @param allDetalleAdquisicionItems the allDetalleAdquisicionItems to set
     */
    public void setAllDetalleAdquisicionItems(List<DetalleAdquisicion> allDetalleAdquisicionItems) {
        this.allDetalleAdquisicionItems = allDetalleAdquisicionItems;
    }

    /**
     * @return the allDetalleAdquisicionItems
     */
    public List<DetalleAdquisicion> getAllDetalleAdquisicionItems() {

        if (allDetalleAdquisicionItems == null) {
            allDetalleAdquisicionItems = new ArrayList<>();
        }
        return allDetalleAdquisicionItems;
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
     * @return the model
     */
    public TreeNode getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(TreeNode model) {
        this.model = model;
    }

}
