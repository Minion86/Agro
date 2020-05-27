package org.Plantacion.Controller;

import java.io.IOException;
import org.Seguridades.Controller.*;
import org.Plantacion.Entities.Plantacion;
import org.Seguridades.Controller.util.JsfUtil;
import org.Plantacion.Facade.PlantacionFacade;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.transaction.UserTransaction;
import org.Plantacion.Entities.PlantacionDetalle;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import org.Plantacion.Dto.AnalisisClimaControl;
import org.Plantacion.Entities.ControlPlantacion;
import org.Plantacion.Entities.WeatherMap;
import org.Plantacion.Facade.WeatherMapFacade;
import org.Plantacion.util.JasperReportUtil;
import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author nmartinez
 */
@Named(value = "forecastController")
@ViewScoped
public class ForecastController implements Serializable {

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
    @EJB
    private WeatherMapFacade ejbWeatherMapFacade;

    private List<PlantacionDetalle> allPlantacionItems = null;
    private List<Plantacion> sonFilteredPerfiles;

    private String analisis;

    static Logger log = Logger.getLogger(ForecastController.class.getName());

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

    private LineChartModel temperaturaModel = new LineChartModel();
    private LineChartModel humedadModel = new LineChartModel();

    private List<AnalisisClimaControl> analisisClimaControlList;

    public ForecastController() {

    }

    public void preparePage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(JsfUtil.GetContextPath() + "/pages/secure/Plantacion/Forecast/List.jsf");
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
                item.setIdDetalleAdquisicion(ejbDetalleAdquisicionFacade.findbyId(item.getIdDetalleAdquisicionInt()));
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
        Calendar hoy = Calendar.getInstance();
        current = ejbPlantacionDetalleFacade.findbyId(current.getIdPlantacionDetalle());
        current.setIdUbicacion(ejbUbicacionFacade.findbyId(current.getPlantacion().getIdUbicacionInt()));
        current.setIdUbicacionPadre(current.getIdUbicacion().getPadreId());
        current.setIdDetalleAdquisicion(ejbDetalleAdquisicionFacade.findbyId(current.getIdDetalleAdquisicionInt()));
        setHumedadModel(new LineChartModel());
        setTemperaturaModel(new LineChartModel());
        List<WeatherMap> weatherMapList = new ArrayList<WeatherMap>();
        weatherMapList = ejbWeatherMapFacade.findbyIdPlantacion(current.getPlantacion().getIdPlantacion());
        SimpleDateFormat formatoFechaDB = new SimpleDateFormat("yyyy-MM-dd");
        Double totalTemperatura = 0.0;
        Double totalHumedad = 0.0;
        LineChartSeries temp = new LineChartSeries();
        LineChartSeries hum = new LineChartSeries();
        for (WeatherMap item : weatherMapList) {

            temp.setFill(true);
            temp.setLabel("Temperatura");
            temp.set(formatoFechaDB.format(item.getFechaRegistro()), item.getTemp() - 273);

            hum.setFill(true);
            hum.setLabel("Humedad %");
            hum.set(formatoFechaDB.format(item.getFechaRegistro()), item.getHumidity());

            totalTemperatura += item.getTemp();
            totalHumedad += item.getHumidity();

        }
        temperaturaModel.addSeries(temp);
        humedadModel.addSeries(hum);

        temperaturaModel.setTitle("Niveles de temperatura en grados centígrados");
        temperaturaModel.setLegendPosition("ne");
        temperaturaModel.setStacked(true);
        temperaturaModel.setShowPointLabels(true);

        humedadModel.setTitle("Niveles de humedad en porcentaje");
        humedadModel.setLegendPosition("ne");
        humedadModel.setStacked(true);
        humedadModel.setShowPointLabels(true);

        Axis xAxisTemp = new CategoryAxis("Fecha");
        Axis xAxisHum = new CategoryAxis("Fecha");
        temperaturaModel.getAxes().put(AxisType.X, xAxisTemp);
        humedadModel.getAxes().put(AxisType.X, xAxisHum);

        Axis yAxisTemp = temperaturaModel.getAxis(AxisType.Y);
        Axis yAxisHum = humedadModel.getAxis(AxisType.Y);
        yAxisTemp.setLabel("Grados");
        yAxisTemp.setMin(0);
        yAxisTemp.setMax(100);

        yAxisHum.setLabel("%");
        yAxisHum.setMin(0);
        yAxisHum.setMax(100);

        List<ControlPlantacion> controlPlantacionList = new ArrayList<>();
        setAnalisisClimaControlList(new ArrayList<>());
        controlPlantacionList = ejbControlPlantacionFacade.findbyIdPlantacionDetalle(current.getIdPlantacionDetalle());

        for (ControlPlantacion item : controlPlantacionList) {
            List<WeatherMap> climaCruceList = ejbWeatherMapFacade.findbyIdPlantacionFecha(current.getPlantacion().getIdPlantacion(), hoy.getTime());
            Double totalTemperaturaCruce = 0.0;
            Double totalHumedadCruce = 0.0;
            for (WeatherMap item2 : climaCruceList) {
                totalTemperaturaCruce += item2.getTemp();
                totalHumedadCruce += item2.getHumidity();
            }
            AnalisisClimaControl analisisClimaControlItem = new AnalisisClimaControl();
            analisisClimaControlItem.setTotalTemperatura(totalTemperaturaCruce - 273);
            analisisClimaControlItem.setTotalHumedad(totalHumedadCruce);
            analisisClimaControlItem.setMediaTemperatura((totalTemperatura / climaCruceList.size()) - 273);
            analisisClimaControlItem.setMediaHumedad(totalHumedadCruce / climaCruceList.size());
            analisisClimaControlItem.setAfeccion(item.getAfeccion());
            analisisClimaControlItem.setTratamiento(item.getTratamiento());
            if (item.getAfeccion() == true) {
                analisisClimaControlItem.setCondicionesCorrectas(true);
            } else {
                analisisClimaControlItem.setCondicionesCorrectas(false);
                analisis += "\nLa plantación presenta problemas con una media de humedad de " + analisisClimaControlItem.getMediaHumedad() + " y con una media de Tempreatura de " + analisisClimaControlItem.getMediaTemperatura() + " grados centígrados";
            }
            getAnalisisClimaControlList().add(analisisClimaControlItem);

            PrimeFaces.current().executeScript("APICall('" + current.getIdUbicacion().getNombreUbicacion() + "');");
            PrimeFaces.current().executeScript("JQUERY('.carousel').carousel();");

        }

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

    /**
     * @return the temperaturaModel
     */
    public LineChartModel getTemperaturaModel() {
        return temperaturaModel;
    }

    /**
     * @param temperaturaModel the temperaturaModel to set
     */
    public void setTemperaturaModel(LineChartModel temperaturaModel) {
        this.temperaturaModel = temperaturaModel;
    }

    /**
     * @return the humedadModel
     */
    public LineChartModel getHumedadModel() {
        return humedadModel;
    }

    /**
     * @param humedadModel the humedadModel to set
     */
    public void setHumedadModel(LineChartModel humedadModel) {
        this.humedadModel = humedadModel;
    }

    /**
     * @return the analisisClimaControlList
     */
    public List<AnalisisClimaControl> getAnalisisClimaControlList() {
        return analisisClimaControlList;
    }

    /**
     * @param analisisClimaControlList the analisisClimaControlList to set
     */
    public void setAnalisisClimaControlList(List<AnalisisClimaControl> analisisClimaControlList) {
        this.analisisClimaControlList = analisisClimaControlList;
    }

    /**
     * @return the analisis
     */
    public String getAnalisis() {
        return analisis;
    }

    /**
     * @param analisis the analisis to set
     */
    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

}
