/**
 * Copyright 2014 MINISTERIO DE RELACIONES LABORALES - ECUADOR 
 * Todos los derechos reservados
 **/

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
 * @Agregar Permite validar la clave de usuario
 * PasswordValidator.java
 *   
 * @Date Aug 5, 2014 9:52:55 AM
 * @author Alex Román MRL(TIC'S)
 */
@FacesValidator("org.Seguridades.validators.PasswordValidator")
public class PasswordValidator implements Validator{
    
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
    
    private Pattern pattern;
    private Matcher matcher;
    
    public PasswordValidator(){
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = 
				new FacesMessage("Contraseña no cumple con la política de seguridad. Debe contener mínimo 8 caracteres, una mayúscula, un número y un caracter especial [@#$%] Ej: Pass14@");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
    }

}