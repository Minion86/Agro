package org.Seguridades.Controller;

import org.Seguridades.Entities.SegPerfil;
import org.Seguridades.Controller.util.JsfUtil;
import org.Seguridades.Facade.SegPerfilFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.Seguridades.DTO.SegAccionesDTO;
import org.Seguridades.DTO.SegMenuDTO;
import org.Seguridades.Entities.SegAccionMenu;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.Seguridades.Entities.SegMenu;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author nmartinez
 */
@Named(value =  "segPerfilController")
@ViewScoped
public class SegPerfilController implements Serializable {

    private SegPerfil current;
    @EJB
    private org.Seguridades.Facade.SegPerfilFacade ejbFacade;

    private List<SegPerfil> sonAllPerfilItems = null;
    private List<SegPerfil> sonFilteredPerfiles;

    static Logger log = Logger.getLogger(
            SegPerfilController.class.getName());

    @Inject
    private LoginController loginController;

    @Resource
    private UserTransaction utx;

    private boolean Editando;

    private TreeNode abuelo;
    @EJB
    private org.Seguridades.Facade.SegMenuFacade ejbSegMenuFacade;
    @EJB
    private org.Seguridades.Facade.SegAccionMenuFacade ejbSegAccionMenuFacade;
    @EJB
    private org.Seguridades.Facade.SegAccionMenuPerfilFacade ejbSegAccionMenuPerfilFacade;

    private boolean permisoInsertar = false;
    private boolean permisoActualizar = false;
    private boolean permisoEliminar = false;
    private boolean permisoImprimir = false;
    private boolean permisoListarPagina = false;
    private boolean permisoBuscar = false;

    public SegPerfilController() {

    }

    public SegPerfil getSelected() {
        if (current == null) {
        }
        return current;
    }

    public void setSelected(SegPerfil selected) {

        current = selected;

    }

    @PostConstruct
    private void cargarArbol() {
        try {
            arbolPersonalizado(null, null);
            verificarPermisos();
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " No se puede generar el menú dinámico", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + "No se puede generar el menú dinámico");
        }
    }

    private void verificarPermisos() {

        List<SegAccionMenuPerfil> listaPermisos = ejbSegAccionMenuPerfilFacade.findbyMenuPerfil("Perfiles", loginController.getRol());
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

    private void arbolPersonalizado(List<SegMenuDTO> listaMenu, TreeNode nodo) {
        try {
            if (listaMenu == null) {
                this.setAbuelo(new DefaultTreeNode(new SegMenuDTO(new SegMenu(), new ArrayList<>()), null));
                List<SegMenu> listaPadres = ejbSegMenuFacade.listarPadres();
                List<SegMenuDTO> listaPadresDTO = new ArrayList<>();
                for (SegMenu segMenu : listaPadres) {
                    listaPadresDTO.add(new SegMenuDTO(segMenu, llenaAllAcciones(segMenu)));

                }
                arbolPersonalizado(listaPadresDTO, this.getAbuelo());
            } else {
                for (SegMenuDTO m : listaMenu) {
                    TreeNode padres = new DefaultTreeNode(m, nodo);
                    List<SegMenu> listaHijos = ejbSegMenuFacade.listarHijos(m.getSegMenu().getIdMenu());
                    List<SegMenuDTO> listaHijosDTO = new ArrayList<>();
                    if (listaHijos != null) {
                        for (SegMenu segMenu : listaHijos) {
                            listaHijosDTO.add(new SegMenuDTO(segMenu, llenaAllAcciones(segMenu)));
                        }
                        arbolPersonalizado(listaHijosDTO, padres);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " No se puede generar el menú dinámico", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + "No se puede generar el menú dinámico");
        }
    }

    private List<SegAccionesDTO> llenaAllAcciones(SegMenu segMenu) {
        List<SegAccionesDTO> segAccionesDTOlist = new ArrayList<>();
        if (segMenu != null && current != null) {
            List<SegAccionMenu> segAcciones = ejbSegAccionMenuFacade.findbyMenu(segMenu);

            for (SegAccionMenu segAccion : segAcciones) {
                if (ejbSegAccionMenuPerfilFacade.findbyAccionMenuPerfil(segMenu, segAccion.getIdAcciones(), current).isEmpty()) {
                    segAccionesDTOlist.add(new SegAccionesDTO(segAccion.getIdAcciones(), false, false));
                } else {
                    segAccionesDTOlist.add(new SegAccionesDTO(segAccion.getIdAcciones(), true, false));
                }
            }

        }
        return segAccionesDTOlist;
    }

    public void cambiaAccionesDTO(SegMenu segMenu, SegAccionesDTO segAccionesDTO) {
        if (segAccionesDTO != null) {
            if (segAccionesDTO.isExisteAsignadoPerfil()) {
                List<SegAccionMenu> segAccionMenu = ejbSegAccionMenuFacade.findbyAccionMenu(segMenu, segAccionesDTO.getAccion());
                if (!segAccionMenu.isEmpty()) {
                    SegAccionMenuPerfil segAccionMenuPerfil = new SegAccionMenuPerfil();
                    segAccionMenuPerfil.setIdAccionOpcion(segAccionMenu.get(0));
                    segAccionMenuPerfil.setIdPerfil(current);
                    ejbSegAccionMenuPerfilFacade.create(segAccionMenuPerfil);
                }
            }

            if (segAccionesDTO.isExisteAsignadoPerfil() == false) {
                List<SegAccionMenuPerfil> segAccionMenuPerfil = ejbSegAccionMenuPerfilFacade.findbyAccionMenuPerfil(segMenu, segAccionesDTO.getAccion(), current);
                if (!segAccionMenuPerfil.isEmpty()) {
                    ejbSegAccionMenuPerfilFacade.remove(segAccionMenuPerfil.get(0));
                }
            }

            JsfUtil.addSuccessMessage("Registro actualizado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " permiso actualizado exitosamente");

        }
    }

    private SegPerfilFacade getFacade() {
        return ejbFacade;
    }

    public String prepareView() {
        //current = (SegPerfil) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate(ActionEvent event) {
        current = new SegPerfil();
        setEditando(false);
        cargarArbol();

    }

    public void destroy(ActionEvent event) throws SystemException {

        try {
            utx.begin();

            this.current.setEstadoPerfil(false);
            setEditando(false);
            getFacade().edit(current);
            sonAllPerfilItems.remove(current);

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
        current = new SegPerfil();

    }

    public void create(ActionEvent event) throws SystemException {
        try {
            utx.begin();

            this.current.setEstadoPerfil(true);
            getFacade().create(current);
            sonAllPerfilItems.add(current);

            utx.commit();

            JsfUtil.addSuccessMessage("Registro creado exitosamente");
            log.info("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " perfil creado exitosamente");
           
        } catch (Exception e) {

            utx.rollback();

            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en crear perfil", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en crear perfil");
            current = null;
        }
    }

    public void search(ActionEvent event) {
        try {
            sonAllPerfilItems = getFacade().findbyBusquedaAvanzada(current);
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en encontrar perfil", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en encontrar perfil");
        }
    }

    public void prepareEdit(ActionEvent event) {
        //current = (SegPerfil) getItems().getRowData();
        // selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        //return "Edit";
        cargarArbol();
        setEditando(true);
    }

    public void update(ActionEvent event) throws SystemException {
        try {
            utx.begin();

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

    public SegPerfil getSegPerfil(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @param sonAllPerfilItems the sonAllPerfilItems to set
     */
    public void setSonAllPerfilItems(List<SegPerfil> sonAllPerfilItems) {
        this.sonAllPerfilItems = sonAllPerfilItems;
    }

    /**
     * @return the sonAllPerfilItems
     */
    public List<SegPerfil> getSonAllPerfilItems() {

        if (sonAllPerfilItems == null) {
            sonAllPerfilItems = getFacade().findbyActivos();
        }
        return sonAllPerfilItems;
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
    public List<SegPerfil> getSonFilteredPerfiles() {
        return sonFilteredPerfiles;
    }

    /**
     * @param sonFilteredPerfiles the sonFilteredPerfiles to set
     */
    public void setSonFilteredPerfiles(List<SegPerfil> sonFilteredPerfiles) {
        this.sonFilteredPerfiles = sonFilteredPerfiles;
    }

    /**
     * @return the abuelo
     */
    public TreeNode getAbuelo() {
        return abuelo;
    }

    /**
     * @param abuelo the abuelo to set
     */
    public void setAbuelo(TreeNode abuelo) {
        this.abuelo = abuelo;
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
