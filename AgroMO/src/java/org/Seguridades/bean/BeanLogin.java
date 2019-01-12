package org.Seguridades.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.Seguridades.Entities.SegUsuario;
import org.Seguridades.details.Login;
import org.Seguridades.servicio.IfaceLogin;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author SergioRios
 */
@ManagedBean(name = "beanLogin")
@SessionScoped
public class BeanLogin implements AuthenticationProvider, Serializable {

    private IfaceLogin serviceLogin;

    static Logger log = Logger.getLogger(
            BeanLogin.class.getName());

    /**
     * The user .
     */
    private SegUsuario user;
    /**
     * The user name.
     */
    private String userName;

    /**
     * The password.
     */
    private String password;


    public IfaceLogin getServiceLogin() {
        return serviceLogin;
    }

    /**
     * @param serviceLogin the serviceLogin to set
     */
    public void setServiceLogin(IfaceLogin serviceLogin) {
        this.serviceLogin = serviceLogin;
    }

    public boolean userExist(SegUsuario user) {
        try {
            this.user = getServiceLogin().validarLogin(user);
            if (this.user != null && this.user.getActivoUsuario() == true) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userLogin = authentication.getPrincipal().toString();
        String passwordLogin = authentication.getCredentials().toString();

        //System.out.println("User: " + userLogin);
        //System.out.println("Password: " + passwordLogin);
        SegUsuario user = new SegUsuario();
        user.setUsernameUsuario(userLogin);
        user.setPasswordUsuario(passwordLogin);
        if (userExist(user)) {
            List<GrantedAuthority> autoridades = new ArrayList<GrantedAuthority>();

            // autoridades.add(new SimpleGrantedAuthority(rol.getSonEtiquetaPerfil()));
            Login userDetails = new Login();
            userDetails.setUsername(userLogin);
            userDetails.setPassword(passwordLogin);

            Authentication customAuthentication = new UsernamePasswordAuthenticationToken(userDetails,
                    passwordLogin, autoridades);

            //ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return customAuthentication;
        } else {
            //log.error("Usuario:" + this.getUser().getUsernameUsuario() + " Usuario o Contraseña Inválidos", new BadCredentialsException("Usuario o Contraseña Inválidos."));
            throw new BadCredentialsException("Usuario o Contraseña Inválidos.");
        }

    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the user
     */
    public SegUsuario getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(SegUsuario user) {
        this.user = user;
    }

}
