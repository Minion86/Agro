/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Cron;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Stateless;
import org.Adquisicion.Entities.Ubicacion;
import org.Adquisicion.Facade.UbicacionFacade;
import org.Biblioteca.Entities.Accion;
import org.Biblioteca.Facade.AccionFacade;
import org.General.util.SendEmailTLS;
import org.Plantacion.Dto.AnalisisClimaControl;
import org.Plantacion.Dto.Clima;
import org.Plantacion.Dto.WeatherMap;
import org.Plantacion.Entities.ControlPlantacion;
import org.Plantacion.Entities.Plantacion;
import org.Plantacion.Entities.PlantacionDetalle;
import org.Plantacion.Entities.Weather;
import org.Plantacion.Facade.ControlPlantacionFacade;
import org.Plantacion.Facade.PlantacionFacade;
import org.Plantacion.Facade.WeatherMapFacade;
import org.Plantacion.Services.WeatherMapClient;
import org.Plantacion.util.FacesUtil;
import org.Seguridades.Entities.SegUsuario;

/**
 *
 * @author nmartinez
 */
@Stateless(name = "ProcesoAnalisisPeriodicoCron")
public class ProcesoAnalisisPeriodicoImpl implements ProcesoAnalisisPeriodicoCron {

    @EJB
    private PlantacionFacade ejbPlantacionFacade;
    @EJB
    private UbicacionFacade ejbUbicacionFacade;
    @EJB
    private WeatherMapFacade ejbWeatherMapFacade;
    @EJB
    private org.Seguridades.Facade.SegUsuarioFacade ejbSegUsuarioFacade;
    @EJB
    private ControlPlantacionFacade ejbControlPlantacionFacade;

    @EJB
    private org.Adquisicion.Facade.DetalleAdquisicionFacade ejbDetalleAdquisicionFacade;
    @EJB
    private AccionFacade accionFacade;

    @Schedule(minute = "01", hour = "*", persistent = false)
    @Override
    public void analizar() {

        Calendar cal = Calendar.getInstance();
        List<Plantacion> plantacionesLista = ejbPlantacionFacade.findActivos(cal.getTime());
        System.out.print("Inicio proceso");
        for (Plantacion item : plantacionesLista) {
            try {
                Ubicacion idUbicacion = ejbUbicacionFacade.findbyId(item.getIdUbicacionInt());
                WeatherMapClient WeatherMapClient = new WeatherMapClient();
                WeatherMap weatherMap = WeatherMapClient.getJsonWeatherMap(idUbicacion.getNombreUbicacion(), "ec");
                org.Plantacion.Entities.WeatherMap weatherMapEntity = new org.Plantacion.Entities.WeatherMap();
                weatherMapEntity.setFechaRegistro(cal.getTime());
                weatherMapEntity.setHumidity(weatherMap.getMain().getHumidity());
                weatherMapEntity.setLat(weatherMap.getCoord().getLat());
                weatherMapEntity.setLon(weatherMap.getCoord().getLon());
                weatherMapEntity.setPressure(weatherMap.getMain().getPressure());
                weatherMapEntity.setTemp(weatherMap.getMain().getTemp());
                weatherMapEntity.setTempMax(weatherMap.getMain().getTemp_max());
                weatherMapEntity.setTempMin(weatherMap.getMain().getTemp_min());
                weatherMapEntity.setIdPlantacion(item);
                List<org.Plantacion.Entities.Weather> weatherList = new ArrayList<>();
                for (Clima itemWeather : weatherMap.getWeather()) {
                    org.Plantacion.Entities.Weather weatherEntity = new Weather();
                    weatherEntity.setIdRest(itemWeather.getId());
                    weatherEntity.setDescription(itemWeather.getDescription());
                    weatherEntity.setMain(itemWeather.getMain());
                    weatherEntity.setIdWeatherMap(weatherMapEntity);
                    weatherList.add(weatherEntity);
                }
                weatherMapEntity.setWeatherList(weatherList);
                ejbWeatherMapFacade.create(weatherMapEntity);

            } catch (Exception ex) {
                System.out.print("Error en Plantación: " + item.getIdPlantacion() + " " + ex.getMessage());
            }
        }

        System.out.print("Fin proceso");
    }

    @Schedules({
        @Schedule(dayOfWeek = "*", hour = "10", minute = "00")
        ,
          @Schedule(dayOfWeek = "*", hour = "15", minute = "00")
        ,
          @Schedule(dayOfWeek = "*", hour = "22", minute = "00")
    })
    @Override
    public void notificaciones() {
        try {
            analisisClima();
        } catch (Exception ex) {
            Logger.getLogger(ProcesoAnalisisPeriodicoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void analisisClima() throws Exception {
        Calendar hoy = Calendar.getInstance();
        List<Plantacion> listaPlantacionesActivas = ejbPlantacionFacade.getPlantacionesActivas();
        StringBuilder textoAnalisisCompleto = new StringBuilder();
        for (Plantacion item : listaPlantacionesActivas) {
            String textoAnalisisPlantacion = "<table><tbody><tr><td style=\"width: 98%;\" colspan=\"3\"><strong>Plantaci&oacute;n: " + item.getNombrePlantacion() + "</strong></td><tr>\n"
                    + "<td style=\"text-align: justify;\" valign=\"top\" width=\"100%\">";
            List<org.Plantacion.Entities.WeatherMap> weatherMapList = new ArrayList<org.Plantacion.Entities.WeatherMap>();
            weatherMapList = ejbWeatherMapFacade.findbyIdPlantacion(item.getIdPlantacion());

            List<org.Plantacion.Entities.WeatherMap> climaCruceList = ejbWeatherMapFacade.findbyIdPlantacionFecha(item.getIdPlantacion(), hoy.getTime());
            for (PlantacionDetalle item2 : item.getPlantacionDetalleList()) {
                item2.setAnalisisClimaControlList(new ArrayList());
                item2.setIdDetalleAdquisicion(ejbDetalleAdquisicionFacade.findbyId(item2.getIdDetalleAdquisicionInt()));
                item2.setProducto(item2.getIdDetalleAdquisicion().getIdBien().getNombreProducto());

                String textoAnalisisPlantacionDetalle = "<table><tbody><tr><td style=\"width: 98%;\" colspan=\"3\"><strong>Producto: " + item2.getIdDetalleAdquisicion().getIdBien().getNombreProducto() + "</strong></td><tr>\n"
                        + "<td style=\"text-align: justify;\" valign=\"top\" width=\"100%\">";

                List<ControlPlantacion> controlPlantacionList = new ArrayList<>();
                controlPlantacionList = ejbControlPlantacionFacade.findbyIdPlantacionDetalle(item2.getIdPlantacionDetalle());

                for (ControlPlantacion item3 : controlPlantacionList) {
                    Double totalTemperaturaCruce = 0.0;
                    Double totalHumedadCruce = 0.0;

                    for (org.Plantacion.Entities.WeatherMap item4 : climaCruceList) {
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
                    if (item3.getAfeccion() == false) {
                        analisisClimaControlItem.setCondicionesCorrectas(true);
                    } else {
                        analisisClimaControlItem.setCondicionesCorrectas(false);
                        textoAnalisisPlantacionDetalle += "\nLa plantación presenta problemas con una media de humedad de " + analisisClimaControlItem.getMediaHumedad() + " y con una media de Tempreatura de " + analisisClimaControlItem.getMediaTemperatura() + " grados centígrados<br/> ";
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(item3.getFechaControlPlantacion());
                        if (analisisClimaControlItem.getMediaTemperatura() > 30) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de plaga de TRIPS";
                            if (item3.getDescripcionAfeccion().toUpperCase().contains("TRIPS")) {
                                List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("TRIPS");
                                if (!accionList.isEmpty()) {
                                    for (Accion x : accionList) {
                                        textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                    }
                                }

                            }
                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("ÁFIDOS") && ((cal.get(Calendar.MONTH) >= 3) && (cal.get(Calendar.MONTH) >= 9))) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de plaga de ÁFIDOS";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("ÁFIDOS");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("TERMITAS") && analisisClimaControlItem.getMediaHumedad() > 85) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de plaga de TERMITAS";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("TERMITAS");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("ZOMPOPOS") && ((cal.get(Calendar.MONTH) >= 2) && (cal.get(Calendar.MONTH) >= 3))) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de plaga de ZOMPOPOS";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("ZOMPOPOS");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("BARRENADORES DE TALLO Y RAMA") && analisisClimaControlItem.getMediaHumedad() <= 50) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de plaga de BARRENADORES DE TALLO Y RAMA";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("BARRENADORES DE TALLO Y RAMA");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("ARDILLAS") && analisisClimaControlItem.getMediaTemperatura() > 30) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de plaga de ARDILLAS";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("ARDILLAS");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("Monalonion") && analisisClimaControlItem.getMediaTemperatura() < 10) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de plaga de Monalonion";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("Monalonion");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("MAL DE MACHETE") && analisisClimaControlItem.getMediaTemperatura() < 10 && analisisClimaControlItem.getMediaHumedad() > 80) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de enfermedad de MAL DE MACHETE";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("MAL DE MACHETE");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("MONILIASIS") && analisisClimaControlItem.getMediaTemperatura() > 30) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de enfermedad de MONILIASIS";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("MONILIASIS");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("MAZORCA NEGRA") && analisisClimaControlItem.getMediaTemperatura() > 30) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de enfermedad de MAZORCA NEGRA";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("MAZORCA NEGRA");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("ESCOBA DE BRUJA") && analisisClimaControlItem.getMediaTemperatura() >= 24 && analisisClimaControlItem.getMediaTemperatura() <= 28 && analisisClimaControlItem.getMediaHumedad() > 80) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de enfermedad de ESCOBA DE BRUJA";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("ESCOBA DE BRUJA");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("ANTRACNOSIS") && analisisClimaControlItem.getMediaTemperatura() >= 27 && analisisClimaControlItem.getMediaHumedad() > 80) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de enfermedad de ANTRACNOSIS";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("ANTRACNOSIS");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                        if (item3.getDescripcionAfeccion().toUpperCase().contains("MUERTE DE FRUTOS TIERNO") && analisisClimaControlItem.getMediaTemperatura() <= 17 && analisisClimaControlItem.getMediaHumedad() > 80) {
                            textoAnalisisPlantacionDetalle += "\n Precaución de enfermedad de MUERTE DE FRUTOS TIERNO";
                            List<Accion> accionList = accionFacade.getAccionbyTextoAfeccion("MUERTE DE FRUTOS TIERNO");
                            if (!accionList.isEmpty()) {
                                for (Accion x : accionList) {
                                    textoAnalisisPlantacionDetalle += "\n" + x.getDescripcion();
                                }
                            }

                        }
                    }

                    item2.getAnalisisClimaControlList().add(analisisClimaControlItem);
                }
                textoAnalisisPlantacionDetalle += "</td></tr>";
                textoAnalisisPlantacion += textoAnalisisPlantacionDetalle;
            }
            textoAnalisisPlantacion += "</table></td></tr></table>";
            textoAnalisisCompleto.append(textoAnalisisPlantacion);

        }
        String mail = FacesUtil.crearMensajePorTipoPlantillaHTML(textoAnalisisCompleto.toString());
        SendEmailTLS sendMail = new SendEmailTLS();
        List<SegUsuario> usuariosAdminList = ejbSegUsuarioFacade.findbyPerfil("Administrador");
        usuariosAdminList.forEach((SegUsuario item) -> {
            try {
                sendMail.SendNotificacion(item.getEmailUsuario(), mail);
            } catch (Exception ex) {
                Logger.getLogger(ProcesoAnalisisPeriodicoImpl.class.getName()).log(Level.SEVERE, null, ex);

            }
        });

    }

}
