package org.Adquisicion.Controller;

import java.io.IOException;
import org.Seguridades.Controller.*;
import org.Adquisicion.Entities.Adquisicion;
import org.Seguridades.Controller.util.JsfUtil;
import org.Adquisicion.Facade.AdquisicionFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.Adquisicion.Entities.DetalleAdquisicion;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nmartinez
 */
@Named(value =  "adquisicionController")
@ViewScoped
public class AdquisicionController implements Serializable {

    private Adquisicion current;
    private DetalleAdquisicion currentDetalle;
    @EJB
    private org.Adquisicion.Facade.AdquisicionFacade ejbFacade;

    private List<Adquisicion> allAdquisicionItems = null;
    private List<Adquisicion> sonFilteredPerfiles;

    static Logger log = Logger.getLogger(AdquisicionController.class.getName());

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

    public AdquisicionController() {

    }

    public void preparePage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Adquisicion/Adquisicion/List.jsf");
    }

    public Adquisicion getSelected() {
        if (current == null) {
        }
        return current;
    }

    public void setSelected(Adquisicion selected) {

        current = selected;

    }

    @PostConstruct
    private void verificarPermisos() {

        List<SegAccionMenuPerfil> listaPermisos = ejbSegAccionMenuPerfilFacade.findbyMenuPerfil("Adquisicion", loginController.getRol());
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
        if (currentDetalle != null) {
            currentDetalle.setValorDetalleAdquisicion(currentDetalle.getIdBien().getValorProducto());
            currentDetalle.setValorLibrosDetalleAdquisicion(currentDetalle.getIdBien().getValorProducto());
        }
    }

    public void changeTipoProducto() {
        if (currentDetalle != null) {
            currentDetalle.setIdBien(null);
        }
    }


    private AdquisicionFacade getFacade() {
        return ejbFacade;
    }

    public String prepareView() {
        //current = (Adquisicion) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate(ActionEvent event) {
        current = new Adquisicion();
        current.setDetalleAdquisicionList(new ArrayList<DetalleAdquisicion>());

    }

    public void prepareCreateDetalle(ActionEvent event) {
        currentDetalle = new DetalleAdquisicion();

    }

    public void imprimeDetalleBarras(ActionEvent event) throws DocumentException {
        try {

         
            if (currentDetalle.getIdBien() != null) {
                currentDetalle.setNombreBien(currentDetalle.getIdBien().getNombreProducto());
            }

            Document document = new Document(new Rectangle(PageSize.A6.rotate()));

            String path = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getServletContext().getRealPath("/upload") + "/" + currentDetalle.getCodigoBien() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));

            document.open();
            Font fontTexto = new Font(Font.FontFamily.TIMES_ROMAN, 15);
            document.add(new Paragraph("MCDS", fontTexto));
            Barcode128 code128 = new Barcode128();
            code128.setGenerateChecksum(true);
            code128.setCode(currentDetalle.getCodigoBien().toString());
            Image code128Image = code128.createImageWithBarcode(writer.getDirectContent(), null, null);
            code128Image.scalePercent(450f);
            document.addTitle("MCDS");
            document.add(code128Image);
            document.close();

            File descarga = new File(path);
            HttpServletResponse response = ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse());

            FileInputStream fichero = new FileInputStream(descarga);
            byte[] contenido = new byte[fichero.available()];
            fichero.read(contenido);
            response.setContentLength(contenido.length);
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=codigoBarras.pdf");

            ServletOutputStream out = response.getOutputStream();
            out.write(contenido);
            out.flush();
            out.close();
            descarga.delete();
            FacesContext.getCurrentInstance().responseComplete();
            System.out.println("Document Generated...!!!!!!");

        } catch (Exception ex) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en encontrar existencias", ex);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en encontrar existencias");

        }

    }

    public void destroy(ActionEvent event) throws SystemException {

        try {
            utx.begin();

            //this.current.setEstadoPerfil(false);
            setEditando(false);
            getFacade().edit(current);
            allAdquisicionItems.remove(current);

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

    public void prepareSearch(ActionEvent event) {
        current = new Adquisicion();

    }

    public void create(ActionEvent event) throws SystemException {
        try {
            utx.begin();

            //this.current.setEstadoAdquisicion(true);
            getFacade().create(current);
            getAllAdquisicionItems().add(current);

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

    public void createCargaMasiva(ActionEvent event) throws SystemException {
        try {
            utx.begin();

            //this.current.setEstadoAdquisicion(true);
            getFacade().create(current);

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
            currentDetalle.setIdAdquisicion(getSelected());
            Random rnd = new Random();
            currentDetalle.setIdDetalleAdquisicion(rnd.nextInt());
            currentDetalle.setEstadoDetalle(true);
            currentDetalle.setCantidadBodegaDetalleAdquisicion(currentDetalle.getCantidadDetalleAdquisicion());
            getSelected().getDetalleAdquisicionList().add(currentDetalle);
        } catch (Exception e) {

            currentDetalle = null;
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en crear producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en crear producto");

        }
    }

    public void updateDetalle() {
        for (DetalleAdquisicion item : getSelected().getDetalleAdquisicionList()) {
            if (item.getIdDetalleAdquisicion().equals(currentDetalle.getIdDetalleAdquisicion())) {
                if (getSelected().getDetalleAdquisicionList().remove(item)) {
                    getSelected().getDetalleAdquisicionList().add(currentDetalle);
                    break;
                }
            }
        }
    }

    public void destroyDetalle() {
        for (DetalleAdquisicion item : getSelected().getDetalleAdquisicionList()) {
            if (item.getIdDetalleAdquisicion().equals(currentDetalle.getIdDetalleAdquisicion())) {
                if (getSelected().getDetalleAdquisicionList().remove(item)) {
                    break;
                }
            }
        }
    }

    public void search(ActionEvent event) {
        try {
            allAdquisicionItems = getFacade().findbyBusquedaAvanzada(current);
            current = null;
            currentDetalle = null;
        } catch (Exception e) {
            log.error("Usuario:" + getLoginController().getUser().getUsernameUsuario() + " error en encontrar producto", e);
            JsfUtil.addErrorMessage(getLoginController().getUser().getUsernameUsuario() + " error en encontrar producto");
        }
    }

    public void prepareEdit(ActionEvent event) {

        setEditando(true);
        current = ejbFacade.findbyId(current.getIdAdquisicion()).get(0);
    }

    public void prepareEditDetalle(ActionEvent event) {

        setEditando(true);

    }

    public void update(ActionEvent event) throws SystemException {
        try {
            utx.begin();

            getFacade().edit(current);

            utx.commit();

            for (Adquisicion item : getAllAdquisicionItems()) {
                if (item.getIdAdquisicion().equals(current.getIdAdquisicion())) {
                    if (getAllAdquisicionItems().remove(item)) {
                        getAllAdquisicionItems().add(current);
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
    }

    public void cancelarDetalle(ActionEvent event) {
        currentDetalle = null;
    }

    public void updateCargaMasiva(ActionEvent event) throws SystemException {
        try {
            utx.begin();

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

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Adquisicion getAdquisicion(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @param allAdquisicionItems the allAdquisicionItems to set
     */
    public void setAllAdquisicionItems(List<Adquisicion> allAdquisicionItems) {
        this.allAdquisicionItems = allAdquisicionItems;
    }

    /**
     * @return the allAdquisicionItems
     */
    public List<Adquisicion> getAllAdquisicionItems() {

        if (allAdquisicionItems == null) {
            allAdquisicionItems = new ArrayList<>();
        }
        return allAdquisicionItems;
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
    public List<Adquisicion> getSonFilteredPerfiles() {
        return sonFilteredPerfiles;
    }

    /**
     * @param sonFilteredPerfiles the sonFilteredPerfiles to set
     */
    public void setSonFilteredPerfiles(List<Adquisicion> sonFilteredPerfiles) {
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
    public DetalleAdquisicion getCurrentDetalle() {
        return currentDetalle;
    }

    /**
     * @param currentDetalle the currentDetalle to set
     */
    public void setCurrentDetalle(DetalleAdquisicion currentDetalle) {
        this.currentDetalle = currentDetalle;
    }


}
