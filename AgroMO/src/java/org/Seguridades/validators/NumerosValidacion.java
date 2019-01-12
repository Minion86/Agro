/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author fernando
 */
@FacesValidator("numerosValidacion")
public class NumerosValidacion implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {

        String strValue = (String) value;
        if (strValue.trim().length() > 0) {
            //Set the email pattern string.  (?i) is a flag for case-insensitive.
            Pattern p = Pattern.compile("^[0-9]+$");

            //Match the given string with the pattern
            Matcher m = p.matcher(strValue);

            //Check whether match is found
            boolean matchFound = m.matches();

            if (!matchFound) {
                String invalidEmailMsg = "Dato Inválido";
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, invalidEmailMsg, invalidEmailMsg);
                throw new ValidatorException(message);
            }
        }
    }
}

