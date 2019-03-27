/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Dto;

import java.io.Serializable;

/**
 *
 * @author nmartinez
 */
public class AnalisisClimaControl implements Serializable {

    private Double totalHumedad;
    private Double mediaHumedad;
    private Double totalTemperatura;
    private Double mediaTemperatura;
    private boolean afeccion;
    private boolean tratamiento;
    private boolean condicionesCorrectas;

    /**
     * @return the totalHumedad
     */
    public Double getTotalHumedad() {
        return totalHumedad;
    }

    /**
     * @param totalHumedad the totalHumedad to set
     */
    public void setTotalHumedad(Double totalHumedad) {
        this.totalHumedad = totalHumedad;
    }

    /**
     * @return the mediaHumedad
     */
    public Double getMediaHumedad() {
        return mediaHumedad;
    }

    /**
     * @param mediaHumedad the mediaHumedad to set
     */
    public void setMediaHumedad(Double mediaHumedad) {
        this.mediaHumedad = mediaHumedad;
    }

    /**
     * @return the totalTemperatura
     */
    public Double getTotalTemperatura() {
        return totalTemperatura;
    }

    /**
     * @param totalTemperatura the totalTemperatura to set
     */
    public void setTotalTemperatura(Double totalTemperatura) {
        this.totalTemperatura = totalTemperatura;
    }

    /**
     * @return the mediaTemperatura
     */
    public Double getMediaTemperatura() {
        return mediaTemperatura;
    }

    /**
     * @param mediaTemperatura the mediaTemperatura to set
     */
    public void setMediaTemperatura(Double mediaTemperatura) {
        this.mediaTemperatura = mediaTemperatura;
    }

    /**
     * @return the afeccion
     */
    public boolean isAfeccion() {
        return afeccion;
    }

    /**
     * @param afeccion the afeccion to set
     */
    public void setAfeccion(boolean afeccion) {
        this.afeccion = afeccion;
    }

    /**
     * @return the tratamiento
     */
    public boolean isTratamiento() {
        return tratamiento;
    }

    /**
     * @param tratamiento the tratamiento to set
     */
    public void setTratamiento(boolean tratamiento) {
        this.tratamiento = tratamiento;
    }

    /**
     * @return the condicionesCorrectas
     */
    public boolean isCondicionesCorrectas() {
        return condicionesCorrectas;
    }

    /**
     * @param condicionesCorrectas the condicionesCorrectas to set
     */
    public void setCondicionesCorrectas(boolean condicionesCorrectas) {
        this.condicionesCorrectas = condicionesCorrectas;
    }
}
