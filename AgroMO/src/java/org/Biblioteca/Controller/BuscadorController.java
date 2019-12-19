package org.Biblioteca.Controller;

import com.google.gson.Gson;
import java.io.IOException;
import org.Seguridades.Controller.*;
import org.Seguridades.Controller.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import javax.faces.context.FacesContext;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import org.Biblioteca.Dto.DiccionarioBusquedaDto;
import org.Biblioteca.Entities.Accion;
import org.Biblioteca.Entities.DiccionarioBusqueda;
import org.Biblioteca.Facade.AccionFacade;
import org.Biblioteca.Facade.DiccionarioBusquedaFacade;

/**
 *
 * @author nmartinez
 */
@Named(value = "buscadorController")
@SessionScoped
public class BuscadorController implements Serializable {

    static Logger log = Logger.getLogger(BuscadorController.class.getName());

    @Inject
    private LoginController loginController;

    private boolean Editando;

    @EJB
    private org.Seguridades.Facade.SegAccionMenuPerfilFacade ejbSegAccionMenuPerfilFacade;
    @EJB
    private DiccionarioBusquedaFacade diccionarioBusquedaFacade;
    @EJB
    private AccionFacade accionFacade;

    private boolean permisoInsertar = false;
    private boolean permisoActualizar = false;
    private boolean permisoEliminar = false;
    private boolean permisoImprimir = false;
    private boolean permisoListarPagina = false;
    private boolean permisoBuscar = false;

    private List<DiccionarioBusquedaDto> diccionarioBusquedaList;

    private String diccionarioBusquedaListJson;

    private String buscarText;

    private List<Accion> allAccionItems;

    public BuscadorController() {

    }

    public void preparePage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Biblioteca/List.jsf");
    }

    @PostConstruct
    private void verificarPermisos() {

        List<SegAccionMenuPerfil> listaPermisos = ejbSegAccionMenuPerfilFacade.findbyMenuPerfil("Biblioteca", loginController.getRol());
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
     * @return the diccionarioBusquedaList
     */
    public List<DiccionarioBusquedaDto> getDiccionarioBusquedaList() {
        if (diccionarioBusquedaList == null) {
            diccionarioBusquedaList = diccionarioBusquedaFacade.getDiccionariobyUserId(this.loginController.getUser().getIdUsuario());
        }
        return diccionarioBusquedaList;
    }

    /*
    Busco el texto y guardo la búsqueda del usuario
     */
    public void buscar() {
        diccionarioBusquedaList = null;
        diccionarioBusquedaListJson = null;
        DiccionarioBusqueda diccionarioBusqueda = new DiccionarioBusqueda();
        diccionarioBusqueda.setIdUsuario(loginController.getUser().getIdUsuario());
        diccionarioBusqueda.setPalabra(getBuscarText());
        diccionarioBusquedaFacade.create(diccionarioBusqueda);
        setAllAccionItems(null);

    }

    /**
     * @param diccionarioBusquedaList the diccionarioBusquedaList to set
     */
    public void setDiccionarioBusquedaList(List<DiccionarioBusquedaDto> diccionarioBusquedaList) {
        this.diccionarioBusquedaList = diccionarioBusquedaList;
    }

    /**
     * @return the diccionarioBusquedaListJson
     */
    public String getDiccionarioBusquedaListJson() {
        if (diccionarioBusquedaListJson == null) {
            if (getDiccionarioBusquedaList() == null) {
                diccionarioBusquedaListJson = "";
            } else {
                Gson variable = new Gson();
                diccionarioBusquedaListJson = variable.toJson(getDiccionarioBusquedaList());
            }
        }
        return diccionarioBusquedaListJson;
    }

    /**
     * @param diccionarioBusquedaListJson the DiccionarioBusquedaListJson to set
     */
    public void setDiccionarioBusquedaListJson(String diccionarioBusquedaListJson) {
        this.diccionarioBusquedaListJson = diccionarioBusquedaListJson;
    }

    /**
     * @return the buscarText
     */
    public String getBuscarText() {
        return buscarText;
    }

    /**
     * @param buscarText the buscarText to set
     */
    public void setBuscarText(String buscarText) {
        this.buscarText = buscarText;
    }

    /**
     * @return the allAccionItems
     */
    public List<Accion> getAllAccionItems() {
        if (allAccionItems == null) {
            if (getBuscarText() != null) {
                allAccionItems = accionFacade.getAccionbyTextoBusqueda(getBuscarText());
            } else {
                allAccionItems = new ArrayList<>();
            }
        } else {
            allAccionItems = new ArrayList<>();
        }
        return allAccionItems;
    }

    /**
     * @param allAccionItems the allAccionItems to set
     */
    public void setAllAccionItems(List<Accion> allAccionItems) {
        this.allAccionItems = allAccionItems;
    }

}
