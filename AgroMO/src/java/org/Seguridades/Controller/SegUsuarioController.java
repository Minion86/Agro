package org.Seguridades.Controller;

import java.io.IOException;
import org.Seguridades.Entities.SegUsuario;
import org.Seguridades.Controller.util.JsfUtil;
import org.Seguridades.Facade.SegUsuarioFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import org.Seguridades.Entities.SegPerfil;
import org.Seguridades.Entities.SegUsuarioPerfil;
import org.apache.log4j.Logger;

/**
 *
 * @author nmartinez
 */
@Named(value =  "segUsuarioController")
@ViewScoped
public class SegUsuarioController implements Serializable {

    private SegUsuario current;
    @EJB
    private org.Seguridades.Facade.SegUsuarioFacade ejbFacade;

    private List<SegUsuario> sonAllUsuarioItems = null;
    private List<SegUsuario> sonFilteredUsuarios;

    static Logger log = Logger.getLogger(SegUsuarioController.class.getName());

    @Inject
    private LoginController loginController;

    @Resource
    private UserTransaction utx;

    private boolean Editando;

    @EJB
    private org.Seguridades.Facade.SegAccionMenuPerfilFacade ejbSegAccionMenuUsuarioFacade;

    private boolean permisoInsertar = false;
    private boolean permisoActualizar = false;
    private boolean permisoEliminar = false;
    private boolean permisoImprimir = false;
    private boolean permisoListarPagina = false;
    private boolean permisoBuscar = false;

    private String new_password;
    private String confirm_password;

    /**
     * El rol .
     */
    private SegPerfil rol;

    public SegUsuarioController() {

    }

    public SegUsuario getSelected() {
        if (current == null) {
        }
        return current;
    }

    public void setSelected(SegUsuario selected) {

        current = selected;

    }

    @PostConstruct
    private void verificarPermisos() {

        List<SegAccionMenuPerfil> listaPermisos = ejbSegAccionMenuUsuarioFacade.findbyMenuPerfil("Usuarios", loginController.getRol());
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

    public void preparePage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Seguridades/segUsuario/List.jsf");
    }

    private SegUsuarioFacade getFacade() {
        return ejbFacade;
    }

    /**
     * addPerfil.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void addPerfil(ActionEvent event) throws IOException {
        try {

            if (current.getSegUsuarioPerfilList() == null) {
                current.setSegUsuarioPerfilList(new ArrayList<>());
            }

            SegUsuarioPerfil segUsuarioPerfil = new SegUsuarioPerfil();
            segUsuarioPerfil.setIdPerfil(rol);
            segUsuarioPerfil.setIdUsuario(current);
            boolean encontro = false;
            for (SegUsuarioPerfil item : current.getSegUsuarioPerfilList()) {
                if (item.getIdPerfil().equals(rol)) {
                    encontro = true;
                    break;
                }
            }
            if (encontro == false) {
                current.getSegUsuarioPerfilList().add(segUsuarioPerfil);
            }

        } catch (Exception e) {
            // e.printStackTrace();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en escoger perfil", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en escoger perfil");
        }
        // return "secured";
    }

    /**
     * delPerfil.
     *
     * @param segUsuarioPerfil
     * @throws java.io.IOException
     */
    public void delPerfil(SegUsuarioPerfil segUsuarioPerfil) throws IOException {
        try {

            current.getSegUsuarioPerfilList().remove(segUsuarioPerfil);

        } catch (Exception e) {
            // e.printStackTrace();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en eliminar perfil", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en eliminar perfil");
        }
        // return "secured";
    }

    public String prepareView() {
        //current = (SegUsuario) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate(ActionEvent event) {
        current = new SegUsuario();
        setEditando(false);
        setNew_password("");
        setConfirm_password("");

    }

    public void destroy(ActionEvent event) throws SystemException {

        try {
            utx.begin();

            this.current.setActivoUsuario(false);
            setEditando(false);
            getFacade().edit(current);
            sonAllUsuarioItems.remove(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro actualizado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " perfil actualizado exitosamente");
            current = null;
        } catch (Exception e) {

            utx.rollback();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en actualizar perfil", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en actualizar perfil");
            current = null;
        }

    }

    public void prepareSearch(ActionEvent event) {
        current = new SegUsuario();

    }

    public void create(ActionEvent event) throws SystemException {
        try {
            utx.begin();

            this.current.setActivoUsuario(true);
            current.setFechaCreacionUsuario(new Date());
            current.setFechaModificacionUsuario(new Date());
            current.setUsuarioModificacionUsuario(loginController.getUser().getIdUsuario());
            if (getNew_password().equals(getConfirm_password())) {
                current.setPasswordUsuario(JsfUtil.getMD5(getNew_password()));

            } else {
                utx.rollback();
                JsfUtil.addSuccessMessage("Passwords no son iguales");
                throw new Exception();
            }
            setNew_password("");
            setConfirm_password("");
            getFacade().create(current);
            getSonAllUsuarioItems().add(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro creado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " perfil creado exitosamente");
            current = null;
        } catch (Exception e) {

            utx.rollback();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en crear perfil", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en crear perfil");
            current = null;
        }
    }

    public void search(ActionEvent event) {
        try {
            sonAllUsuarioItems = getFacade().findbyBusquedaAvanzada(current);
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en encontrar perfil", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en encontrar perfil");
        }
    }

    public void prepareEdit(ActionEvent event) {
        //current = (SegUsuario) getItems().getRowData();
        // selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        //return "Edit";
        setNew_password("");
        setConfirm_password("");
        setEditando(true);
    }

    public void update(ActionEvent event) throws SystemException {
        try {
            utx.begin();
            if (!"".equals(getNew_password()) && !"".equals(getConfirm_password())) {
                if (getNew_password().equals(getConfirm_password())) {
                    current.setPasswordUsuario(JsfUtil.getMD5(getNew_password()));
                    JsfUtil.addSuccessMessage("Cambio de password correcto");
                }
            }
            current.setFechaModificacionUsuario(new Date());
            current.setUsuarioModificacionUsuario(loginController.getUser().getIdUsuario());
            getFacade().edit(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro actualizado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " perfil actualizado exitosamente");
            current = null;
        } catch (Exception e) {

            utx.rollback();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en actualizar perfil", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en actualizar perfil");
            current = null;
        }
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SegUsuario getSegUsuario(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @param sonAllUsuarioItems the sonAllUsuarioItems to set
     */
    public void setSonAllUsuarioItems(List<SegUsuario> sonAllUsuarioItems) {
        this.sonAllUsuarioItems = sonAllUsuarioItems;
    }

    /**
     * @return the sonAllUsuarioItems
     */
    public List<SegUsuario> getSonAllUsuarioItems() {

        if (sonAllUsuarioItems == null) {
            sonAllUsuarioItems = new ArrayList<>();
        }
        return sonAllUsuarioItems;
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
     * @return the sonFilteredUsuarios
     */
    public List<SegUsuario> getSonFilteredUsuarios() {
        return sonFilteredUsuarios;
    }

    /**
     * @param sonFilteredUsuarios the sonFilteredUsuarios to set
     */
    public void setSonFilteredUsuarios(List<SegUsuario> sonFilteredUsuarios) {
        this.sonFilteredUsuarios = sonFilteredUsuarios;
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
     * @return the new_password
     */
    public String getNew_password() {
        return new_password;
    }

    /**
     * @param new_password the new_password to set
     */
    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    /**
     * @return the confirm_password
     */
    public String getConfirm_password() {
        return confirm_password;
    }

    /**
     * @param confirm_password the confirm_password to set
     */
    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    /**
     * @return the rol
     */
    public SegPerfil getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(SegPerfil rol) {
        this.rol = rol;
    }

}
