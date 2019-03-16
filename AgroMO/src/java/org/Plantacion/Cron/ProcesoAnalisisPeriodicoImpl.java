/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Cron;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import org.Adquisicion.Entities.Ubicacion;
import org.Adquisicion.Facade.UbicacionFacade;
import org.Plantacion.Dto.Clima;
import org.Plantacion.Dto.WeatherMap;
import org.Plantacion.Entities.Plantacion;
import org.Plantacion.Entities.Weather;
import org.Plantacion.Facade.PlantacionFacade;
import org.Plantacion.Facade.WeatherMapFacade;
import org.Plantacion.Services.WeatherMapClient;

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

}
