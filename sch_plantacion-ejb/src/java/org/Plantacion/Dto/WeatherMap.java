/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nmartinez
 */
@XmlRootElement
public class WeatherMap implements Serializable {

    private Coordenadas coord;
    private List<Clima> weather;
    private Main main;
    private Viento wind;

    /**
     * @return the coord
     */
    public Coordenadas getCoord() {
        return coord;
    }

    /**
     * @param coord the coord to set
     */
    public void setCoord(Coordenadas coord) {
        this.coord = coord;
    }

    /**
     * @return the main
     */
    public Main getMain() {
        return main;
    }

    /**
     * @param main the main to set
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * @return the wind
     */
    public Viento getWind() {
        return wind;
    }

    /**
     * @param wind the wind to set
     */
    public void setWind(Viento wind) {
        this.wind = wind;
    }

    /**
     * @return the weather
     */
    public List<Clima> getWeather() {
        return weather;
    }

    /**
     * @param weather the weather to set
     */
    public void setWeather(List<Clima> weather) {
        this.weather = weather;
    }
}
