/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author leofernando
 */
@FacesValidator("rucValidacion")
public class RucValidacion implements Validator {

    Validation v;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {

        String strValue = (String) value;
        if (strValue != null) {

            if (!Validation.validateCCRuc(strValue)) {
                String invalidRucIdMsg = "Identificación invalida";
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, invalidRucIdMsg, invalidRucIdMsg);
                throw new ValidatorException(message);


            }

        }
    }
}
