/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
//import javax.faces.bean.RequestScoped;
//import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.General.util.PaginadorUtil;
import org.Plantacion.Dto.AnalisisClimaControl;
import org.Plantacion.Entities.ControlPlantacion;
import org.Plantacion.Entities.Plantacion;
import org.Plantacion.Entities.PlantacionDetalle;
import org.Plantacion.Entities.WeatherMap;
import org.Plantacion.Facade.ControlPlantacionFacade;
import org.Plantacion.Facade.PlantacionDetalleFacade;
import org.Plantacion.Facade.PlantacionFacade;
import org.Plantacion.Facade.WeatherMapFacade;
import org.apache.log4j.Logger;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 * The Class LoginBean.
 */
@Named(value = "dashboardController")
@SessionScoped
public class DashboardController
        implements Serializable {

    static Logger log = Logger.getLogger(DashboardController.class.getName());

    @EJB
    private org.Seguridades.Facade.SegAccionMenuPerfilFacade ejbSegAccionMenuPerfilFacade;

    @EJB
    private PlantacionFacade ejbPlantacionFacade;

    @EJB
    private ControlPlantacionFacade ejbControlPlantacionFacade;

    @EJB
    private PlantacionDetalleFacade ejbPlantacionDetalleFacade;

    @EJB
    private WeatherMapFacade ejbWeatherMapFacade;

    @EJB
    private org.Adquisicion.Facade.DetalleAdquisicionFacade ejbDetalleAdquisicionFacade;

    private boolean permisoListarPagina = false;

    @Inject
    private LoginController loginController;

    private Integer cantidadPlantacionesActivas;

    private Integer cantidadControlesPlantacionesActivas;

    private Integer cantidadAfeccionesControlesPlantacionesActivas;

    private Integer cantidadAfeccionesTratadasControlesPlantacionesActivas;

    private Double porcentajeAfeccionesControlesPlantacionesActivas;

    private Double porcentajeAfeccionesTratadasControlesPlantacionesActivas;

    private List<Plantacion> listaPlantacionesActivas;

    private PaginadorUtil paginacionPlantacion;

    /**
     *
     */
    public DashboardController() {
    }

    @PostConstruct
    private void verificarPermisos() {

        if (getLoginController().getRol() != null) {
            permisoListarPagina = true;
            cantidadPlantacionesActivas = ejbPlantacionFacade.getPlantacionbyBusquedaTotal(null).intValue();
            cantidadControlesPlantacionesActivas = ejbControlPlantacionFacade.findControlesTotal().intValue();
            cantidadAfeccionesControlesPlantacionesActivas = ejbControlPlantacionFacade.findAfeccionesTotal().intValue();
            cantidadAfeccionesTratadasControlesPlantacionesActivas = ejbControlPlantacionFacade.findTratamientosTotal().intValue();
            porcentajeAfeccionesControlesPlantacionesActivas = (cantidadAfeccionesControlesPlantacionesActivas.doubleValue() * 100) / cantidadControlesPlantacionesActivas;
            porcentajeAfeccionesTratadasControlesPlantacionesActivas = (cantidadAfeccionesTratadasControlesPlantacionesActivas.doubleValue() * 100) / cantidadAfeccionesControlesPlantacionesActivas;

        }
    }

    private void analisisClima() {
        Calendar hoy = Calendar.getInstance();

        listaPlantacionesActivas.forEach((Plantacion item) -> {
            List<WeatherMap> weatherMapList = new ArrayList<WeatherMap>();
            weatherMapList = ejbWeatherMapFacade.findbyIdPlantacion(item.getIdPlantacion());

            List<WeatherMap> climaCruceList = ejbWeatherMapFacade.findbyIdPlantacionFecha(item.getIdPlantacion(), hoy.getTime());
            for(PlantacionDetalle item2: item.getPlantacionDetalleList()) {

                item2.setHumedadModel(new LineChartModel());
                item2.setTemperaturaModel(new LineChartModel());
                SimpleDateFormat formatoFechaDB = new SimpleDateFormat("yyyy-MM-dd");
                Double totalTemperatura = 0.0;
                Double totalHumedad = 0.0;
                LineChartSeries temp = new LineChartSeries();
                LineChartSeries hum = new LineChartSeries();
                for (WeatherMap itemWeather : weatherMapList) {

                    temp.setFill(true);
                    temp.setLabel("Temperatura");
                    temp.set(formatoFechaDB.format(itemWeather.getFechaRegistro()), itemWeather.getTemp() - 273);

                    hum.setFill(true);
                    hum.setLabel("Humedad %");
                    hum.set(formatoFechaDB.format(itemWeather.getFechaRegistro()), itemWeather.getHumidity());

                    totalTemperatura += itemWeather.getTemp();
                    totalHumedad += itemWeather.getHumidity();

                }
                item2.getTemperaturaModel().addSeries(temp);
                item2.getHumedadModel().addSeries(hum);

                item2.getTemperaturaModel().setTitle("Niveles de temperatura en grados centígrados");
                item2.getTemperaturaModel().setLegendPosition("ne");
                item2.getTemperaturaModel().setStacked(true);
                item2.getTemperaturaModel().setShowPointLabels(true);

                item2.getHumedadModel().setTitle("Niveles de humedad en porcentaje");
                item2.getHumedadModel().setLegendPosition("ne");
                item2.getHumedadModel().setStacked(true);
                item2.getHumedadModel().setShowPointLabels(true);

                Axis xAxisTemp = new CategoryAxis("Fecha");
                Axis xAxisHum = new CategoryAxis("Fecha");
                item2.getTemperaturaModel().getAxes().put(AxisType.X, xAxisTemp);
                item2.getHumedadModel().getAxes().put(AxisType.X, xAxisHum);

                Axis yAxisTemp = item2.getTemperaturaModel().getAxis(AxisType.Y);
                Axis yAxisHum = item2.getHumedadModel().getAxis(AxisType.Y);
                yAxisTemp.setLabel("Grados");
                yAxisTemp.setMin(0);
                yAxisTemp.setMax(100);

                yAxisHum.setLabel("%");
                yAxisHum.setMin(0);
                yAxisHum.setMax(100);

                List<ControlPlantacion> controlPlantacionList = new ArrayList<>();
                controlPlantacionList = ejbControlPlantacionFacade.findbyIdPlantacionDetalle(item2.getIdPlantacionDetalle());
                item2.setAnalisisClimaControlList(new ArrayList());
                item2.setIdDetalleAdquisicion(ejbDetalleAdquisicionFacade.findbyId(item2.getIdDetalleAdquisicionInt()));
                item2.setProducto(item2.getIdDetalleAdquisicion().getIdBien().getNombreProducto());

                for (ControlPlantacion item3 : controlPlantacionList) {
                    Double totalTemperaturaCruce = 0.0;
                    Double totalHumedadCruce = 0.0;

                    for (WeatherMap item4 : climaCruceList) {
                        totalTemperaturaCruce += item4.getTemp();
                        totalHumedadCruce += item4.getHumidity();
                    }
                    AnalisisClimaControl analisisClimaControlItem = new AnalisisClimaControl();
                    analisisClimaControlItem.setTotalTemperatura(totalTemperaturaCruce - 273);
                    analisisClimaControlItem.setTotalHumedad(totalHumedadCruce);
                    analisisClimaControlItem.setMediaTemperatura((totalTemperaturaCruce / climaCruceList.size()) - 273);
                    analisisClimaControlItem.setMediaHumedad(totalHumedadCruce / climaCruceList.size());
                    analisisClimaControlItem.setAfeccion(item3.getAfeccion());
                    analisisClimaControlItem.setTratamiento(item3.getTratamiento());
                    item2.getAnalisisClimaControlList().add(analisisClimaControlItem);
                }
            }

        });

    }

    /**
     *
     * Obtengo la lista de Plantaciones paginadas a 10 registros
     */
    protected void buscarListaPlantacion() {
        setPaginacionPlantacion(new PaginadorUtil(10) {
            @Override
            public long getItemsCount() {
                return ejbPlantacionFacade.getPlantacionbyBusquedaTotal(null);
            }

            @Override
            public List listar() {
                return ejbPlantacionFacade.getPlantacionbyBusquedaPaginado(null, getPageFirstItem(), getPageSize());
            }
        });

    }

    /**
     * Reinicializa la lista de períodos
     */
    public void recreateModelPlantacion() {
        this.getListaPlantacionesActivas().clear();
        this.getListaPlantacionesActivas().addAll(getPaginacionPlantacion().listar());
        analisisClima();
    }

    /**
     * Paginador acción anterior
     */
    public void firstPlantacion() {
        getPaginacionPlantacion().firstPage();
        recreateModelPlantacion();
    }

    /**
     * Paginador acción anterior
     */
    public void previousPlantacion() {
        getPaginacionPlantacion().previousPage();
        recreateModelPlantacion();
    }

    /**
     * Paginador acción anterior
     */
    public void lastPlantacion() {
        getPaginacionPlantacion().lastPage();
        recreateModelPlantacion();
    }

    /**
     * Paginador acción siguiente
     */
    public void nextPlantacion() {
        getPaginacionPlantacion().nextPage();
        recreateModelPlantacion();
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
     * @return the cantidadPlantacionesActivas
     */
    public Integer getCantidadPlantacionesActivas() {
        return cantidadPlantacionesActivas;
    }

    /**
     * @param cantidadPlantacionesActivas the cantidadPlantacionesActivas to set
     */
    public void setCantidadPlantacionesActivas(Integer cantidadPlantacionesActivas) {
        this.cantidadPlantacionesActivas = cantidadPlantacionesActivas;
    }

    /**
     * @return the cantidadControlesPlantacionesActivas
     */
    public Integer getCantidadControlesPlantacionesActivas() {
        return cantidadControlesPlantacionesActivas;
    }

    /**
     * @param cantidadControlesPlantacionesActivas the
     * cantidadControlesPlantacionesActivas to set
     */
    public void setCantidadControlesPlantacionesActivas(Integer cantidadControlesPlantacionesActivas) {
        this.cantidadControlesPlantacionesActivas = cantidadControlesPlantacionesActivas;
    }

    /**
     * @return the cantidadAfeccionesControlesPlantacionesActivas
     */
    public Integer getCantidadAfeccionesControlesPlantacionesActivas() {
        return cantidadAfeccionesControlesPlantacionesActivas;
    }

    /**
     * @param cantidadAfeccionesControlesPlantacionesActivas the
     * cantidadAfeccionesControlesPlantacionesActivas to set
     */
    public void setCantidadAfeccionesControlesPlantacionesActivas(Integer cantidadAfeccionesControlesPlantacionesActivas) {
        this.cantidadAfeccionesControlesPlantacionesActivas = cantidadAfeccionesControlesPlantacionesActivas;
    }

    /**
     * @return the cantidadAfeccionesTratadasControlesPlantacionesActivas
     */
    public Integer getCantidadAfeccionesTratadasControlesPlantacionesActivas() {
        return cantidadAfeccionesTratadasControlesPlantacionesActivas;
    }

    /**
     * @param cantidadAfeccionesTratadasControlesPlantacionesActivas the
     * cantidadAfeccionesTratadasControlesPlantacionesActivas to set
     */
    public void setCantidadAfeccionesTratadasControlesPlantacionesActivas(Integer cantidadAfeccionesTratadasControlesPlantacionesActivas) {
        this.cantidadAfeccionesTratadasControlesPlantacionesActivas = cantidadAfeccionesTratadasControlesPlantacionesActivas;
    }

    /**
     * @return the porcentajeAfeccionesControlesPlantacionesActivas
     */
    public Double getPorcentajeAfeccionesControlesPlantacionesActivas() {
        return porcentajeAfeccionesControlesPlantacionesActivas;
    }

    /**
     * @param porcentajeAfeccionesControlesPlantacionesActivas the
     * porcentajeAfeccionesControlesPlantacionesActivas to set
     */
    public void setPorcentajeAfeccionesControlesPlantacionesActivas(Double porcentajeAfeccionesControlesPlantacionesActivas) {
        this.porcentajeAfeccionesControlesPlantacionesActivas = porcentajeAfeccionesControlesPlantacionesActivas;
    }

    /**
     * @return the porcentajeAfeccionesTratadasControlesPlantacionesActivas
     */
    public Double getPorcentajeAfeccionesTratadasControlesPlantacionesActivas() {
        return porcentajeAfeccionesTratadasControlesPlantacionesActivas;
    }

    /**
     * @param porcentajeAfeccionesTratadasControlesPlantacionesActivas the
     * porcentajeAfeccionesTratadasControlesPlantacionesActivas to set
     */
    public void setPorcentajeAfeccionesTratadasControlesPlantacionesActivas(Double porcentajeAfeccionesTratadasControlesPlantacionesActivas) {
        this.porcentajeAfeccionesTratadasControlesPlantacionesActivas = porcentajeAfeccionesTratadasControlesPlantacionesActivas;
    }

    /**
     * @return the listaPlantacionesActivas
     */
    public List<Plantacion> getListaPlantacionesActivas() {
        if (listaPlantacionesActivas == null) {
            listaPlantacionesActivas = new ArrayList<>();
            this.listaPlantacionesActivas.clear();
            buscarListaPlantacion();
            this.listaPlantacionesActivas.addAll(getPaginacionPlantacion().listar());
            analisisClima();
        }
        return listaPlantacionesActivas;
    }

    /**
     * @param listaPlantacionesActivas the listaPlantacionesActivas to set
     */
    public void setListaPlantacionesActivas(List<Plantacion> listaPlantacionesActivas) {
        this.listaPlantacionesActivas = listaPlantacionesActivas;
    }

    /**
     * @return the paginacionPlantacion
     */
    public PaginadorUtil getPaginacionPlantacion() {
        return paginacionPlantacion;
    }

    /**
     * @param paginacionPlantacion the paginacionPlantacion to set
     */
    public void setPaginacionPlantacion(PaginadorUtil paginacionPlantacion) {
        this.paginacionPlantacion = paginacionPlantacion;
    }

}
