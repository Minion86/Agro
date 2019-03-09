package org.Seguridades.Entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.Seguridades.Entities.SegUsuarioPerfil;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-09T18:14:55")
@StaticMetamodel(SegUsuario.class)
public class SegUsuario_ { 

    public static volatile ListAttribute<SegUsuario, SegUsuarioPerfil> segUsuarioPerfilList;
    public static volatile SingularAttribute<SegUsuario, Date> fechaCreacionUsuario;
    public static volatile SingularAttribute<SegUsuario, Date> fechaModificacionUsuario;
    public static volatile SingularAttribute<SegUsuario, String> apellidoUsuario;
    public static volatile SingularAttribute<SegUsuario, Integer> usuarioModificacionUsuario;
    public static volatile SingularAttribute<SegUsuario, Integer> idUsuario;
    public static volatile SingularAttribute<SegUsuario, String> passwordUsuario;
    public static volatile SingularAttribute<SegUsuario, String> emailUsuario;
    public static volatile SingularAttribute<SegUsuario, String> nombreUsuario;
    public static volatile SingularAttribute<SegUsuario, String> identificacionUsuario;
    public static volatile SingularAttribute<SegUsuario, Boolean> activoUsuario;
    public static volatile SingularAttribute<SegUsuario, String> usernameUsuario;

}