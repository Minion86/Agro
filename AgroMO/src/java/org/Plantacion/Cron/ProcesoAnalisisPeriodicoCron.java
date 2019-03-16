/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Cron;

import javax.ejb.Local;

/**
 *
 * @author savia3
 */
@Local
public interface ProcesoAnalisisPeriodicoCron {

    /**
     *
     */
    void analizar();
}
