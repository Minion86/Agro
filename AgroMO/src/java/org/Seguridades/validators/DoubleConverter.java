/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.validators;

import java.text.DecimalFormat;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author christian
 */
public class DoubleConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value == null || value.equals("")) {
                return null;
            }
            value = value.replace(",", ".");
            return new Double(value);
        } catch (Exception e) {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setDetail("El valor no es un número válido");
            facesMessage.setSummary("El valor no es un número válido");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(facesMessage);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (value != null) {
                return new DecimalFormat("###.##").format(value);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }
}
