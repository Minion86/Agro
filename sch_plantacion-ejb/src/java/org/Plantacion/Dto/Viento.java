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
public class Viento  implements Serializable{

    private Double speed;
    private Double deg;

    /**
     * @return the speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     * @return the deg
     */
    public Double getDeg() {
        return deg;
    }

    /**
     * @param deg the deg to set
     */
    public void setDeg(Double deg) {
        this.deg = deg;
    }
}
