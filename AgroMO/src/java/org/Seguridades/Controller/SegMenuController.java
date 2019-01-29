package org.Seguridades.Controller;

import java.io.IOException;
import org.Seguridades.Entities.SegMenu;
import org.Seguridades.Controller.util.JsfUtil;
import org.Seguridades.Facade.SegMenuFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
import org.Seguridades.DTO.SegAccionesDTO;
import org.Seguridades.Entities.SegAccionMenu;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.Seguridades.Entities.SegAcciones;
import org.apache.log4j.Logger;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author nmartinez
 */
@Named(value =  "segMenuController")
@ViewScoped
public class SegMenuController implements Serializable {

    private SegMenu current;
    @EJB
    private org.Seguridades.Facade.SegMenuFacade ejbFacade;

    private List<SegMenu> sonAllMenuItems = null;
    private List<SegMenu> sonFilteredMenues;

    static Logger log = Logger.getLogger(SegMenuController.class.getName());

    @Inject
    private LoginController loginController;

    @Resource
    private UserTransaction utx;

    private boolean Editando;

    private TreeNode abuelo;
    private List<SelectItem> listaMenus = new ArrayList<SelectItem>();

    private List<SegAccionesDTO> allAcciones;
    private List<SegAccionesDTO> accionesMenu;
    @EJB
    private org.Seguridades.Facade.SegAccionesFacade ejbSegAccionesFacade;
    @EJB
    private org.Seguridades.Facade.SegAccionMenuPerfilFacade ejbSegAccionMenuPerfilFacade;
    @EJB
    private org.Seguridades.Facade.SegAccionMenuFacade ejbSegAccionMenuFacade;

    private boolean permisoInsertar = false;
    private boolean permisoActualizar = false;
    private boolean permisoEliminar = false;
    private boolean permisoImprimir = false;
    private boolean permisoListarPagina = false;
    private boolean permisoBuscar = false;

    public SegMenuController() {

    }

    public void preparePage() throws IOException {
        cargarArbol();
        FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Seguridades/segMenu/List.jsf");
    }

    @PostConstruct
    private void cargarArbol() {
        try {
            arbolPersonalizado(null, null);
            cargarComboMenu();
            verificarPermisos();

        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " No se puede generar el menú dinámico", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + "No se puede generar el menú dinámico");
        }
    }

    private void verificarPermisos() {

        List<SegAccionMenuPerfil> listaPermisos = ejbSegAccionMenuPerfilFacade.findbyMenuPerfil("Menu", loginController.getRol());
        for (SegAccionMenuPerfil segAccionMenuPerfil : listaPermisos) {
            if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("insertar")) {
                setPermisoInsertar(true);
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("actualizar")) {
                setPermisoActualizar(true);
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("eliminar")) {
                setPermisoEliminar(true);
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("imprimir")) {
                setPermisoImprimir(true);
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("listar pagina")) {
                setPermisoListarPagina(true);
            } else if (segAccionMenuPerfil.getIdAccionOpcion().getIdAcciones().getNombreAccion().equals("buscar")) {
                setPermisoBuscar(true);
            }
        }

    }

    private void cargarComboMenu() {
        try {
            this.setListaMenus(new ArrayList<SelectItem>());
            this.getListaMenus().add(new SelectItem(0, "Seleccione"));
            List<SegMenu> listaMenu = ejbFacade.listarNodosConHijos();
            for (SegMenu m : listaMenu) {
                this.getListaMenus().add(new SelectItem(m.getIdMenu(), m.getTextoMenu()));
            }
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " No se puede generar el menú dinámico", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + "No se puede generar el menú dinámico");
        }
    }

    public SegMenu getSelected() {
        if (current == null) {
            //current = new SegMenu();
        }
        return current;
    }

    public void setSelected(SegMenu selected) {

        current = selected;

    }

    public void onNodeSelect(NodeSelectEvent event) {
        if (event.getTreeNode() != null) {
            try {

                current = ejbFacade.listarbyId(((SegMenu) event.getTreeNode().getData()).getIdMenu());
                accionesMenu = new ArrayList<>();
                llenaAllAcciones();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(SegMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private SegMenuFacade getFacade() {
        return ejbFacade;
    }

    public String prepareView() {
        //current = (SegMenu) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate(ActionEvent event) {
        current = new SegMenu();
        setEditando(false);
        accionesMenu = new ArrayList<>();
        llenaAllAcciones();

    }

    private void arbolPersonalizado(List<SegMenu> listaMenu, TreeNode nodo) {
        try {
            if (listaMenu == null) {
                this.abuelo = new DefaultTreeNode(new SegMenu(), null);
                List<SegMenu> listaPadres = ejbFacade.listarPadres();
                arbolPersonalizado(listaPadres, this.getAbuelo());
            } else {
                for (SegMenu m : listaMenu) {
                    TreeNode padres = new DefaultTreeNode(m, nodo);
                    List<SegMenu> listaHijos = ejbFacade.listarHijos(m.getIdMenu());
                    if (listaHijos != null) {
                        arbolPersonalizado(listaHijos, padres);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " No se puede generar el menú dinámico", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + "No se puede generar el menú dinámico");
        }
    }

    public void destroy(ActionEvent event) throws SystemException {

        try {
            utx.begin();
            setEditando(false);
            getFacade().remove(current);
            cargarArbol();
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
        current = new SegMenu();

    }

    public void create(ActionEvent event) throws SystemException {
        try {
            utx.begin();

            getFacade().create(current);

            utx.commit();
            cargarArbol();

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
            //sonAllMenuItems = getFacade().findbyBusquedaAvanzada(current);
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en encontrar perfil", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en encontrar perfil");
        }
    }

    public void prepareEdit(ActionEvent event) {
        //current = (SegMenu) getItems().getRowData();
        // selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        //return "Edit";
        setEditando(true);
    }

    public void update(ActionEvent event) throws SystemException {
        try {
            utx.begin();

            getFacade().edit(current);

            for (SegAccionesDTO segAccionMenu : allAcciones) {
                if (segAccionMenu.isExisteAsignadoMenu() == true && ejbSegAccionMenuFacade.findbyAccionMenu(current, segAccionMenu.getAccion()).isEmpty()) {
                    SegAccionMenu segAccionMenuTemp = new SegAccionMenu();
                    segAccionMenuTemp.setIdAcciones(segAccionMenu.getAccion());
                    segAccionMenuTemp.setIdMenu(current);
                    ejbSegAccionMenuFacade.create(segAccionMenuTemp);

                }
                if (segAccionMenu.isExisteAsignadoMenu() == false) {
                    List<SegAccionMenu> segAccionMenuTemp = ejbSegAccionMenuFacade.findbyAccionMenu(current, segAccionMenu.getAccion());
                    if (!segAccionMenuTemp.isEmpty()) {
                        ejbSegAccionMenuFacade.remove(segAccionMenuTemp.get(0));
                    }

                }

            }

            utx.commit();
            cargarArbol();

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

    public SegMenu getSegMenu(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @param sonAllMenuItems the sonAllMenuItems to set
     */
    public void setSonAllMenuItems(List<SegMenu> sonAllMenuItems) {
        this.sonAllMenuItems = sonAllMenuItems;
    }

    /**
     * @return the sonAllMenuItems
     */
    public List<SegMenu> getSonAllMenuItems() {

        if (sonAllMenuItems == null) {
            //sonAllMenuItems = getFacade().findbyAdquisicion();
        }
        return sonAllMenuItems;
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
     * @return the sonFilteredMenues
     */
    public List<SegMenu> getSonFilteredMenues() {
        return sonFilteredMenues;
    }

    /**
     * @param sonFilteredMenues the sonFilteredMenues to set
     */
    public void setSonFilteredMenues(List<SegMenu> sonFilteredMenues) {
        this.sonFilteredMenues = sonFilteredMenues;
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
     * @return the listaMenus
     */
    public List<SelectItem> getListaMenus() {
        return listaMenus;
    }

    /**
     * @param listaMenus the listaMenus to set
     */
    public void setListaMenus(List<SelectItem> listaMenus) {
        this.listaMenus = listaMenus;
    }

    /**
     * @return the allAcciones
     */
    public List<SegAccionesDTO> getAllAcciones() {
        if (allAcciones == null) {
            allAcciones = new ArrayList<>();

        }

        return allAcciones;
    }

    private void llenaAllAcciones() {
        if (current != null) {
            allAcciones = new ArrayList<>();
            List<SegAcciones> segAcciones = ejbSegAccionesFacade.findAll();
            for (SegAcciones segAccion : segAcciones) {
                if (ejbSegAccionMenuPerfilFacade.findbyAccionMenu(current, segAccion).isEmpty()) {
                    allAcciones.add(new SegAccionesDTO(segAccion, false, false));
                } else {
                    allAcciones.add(new SegAccionesDTO(segAccion, true, false));
                }
            }
            for (SegAccionesDTO segAccion : allAcciones) {
                if (current.getSegAccionMenuList() != null) {
                    for (SegAccionMenu segAccionMenu : current.getSegAccionMenuList()) {
                        if (segAccionMenu.getIdAcciones().getIdAcciones().equals(segAccion.getAccion().getIdAcciones())) {
                            segAccion.setExisteAsignadoMenu(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * @param allAcciones the allAcciones to set
     */
    public void setAllAcciones(List<SegAccionesDTO> allAcciones) {
        this.allAcciones = allAcciones;
    }

    /**
     * @return the accionesMenu
     */
    public List<SegAccionesDTO> getAccionesMenu() {
        if (accionesMenu == null) {
            accionesMenu = new ArrayList<>();
        }
        return accionesMenu;
    }

    /**
     * @param accionesMenu the accionesMenu to set
     */
    public void setAccionesMenu(List<SegAccionesDTO> accionesMenu) {
        this.accionesMenu = accionesMenu;
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
