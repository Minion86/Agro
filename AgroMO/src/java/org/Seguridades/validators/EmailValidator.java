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
@FacesValidator("org.Seguridades.validators.EmailValidator")
public class EmailValidator implements Validator{
    
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";
    
    private Pattern pattern;
    private Matcher matcher;
    
    public EmailValidator(){
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = 
				new FacesMessage("Correo electronico incorrecto Ejemplo aaaaa@bbbb.ccc");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
    }

}