package org.Seguridades.Entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.Seguridades.Entities.SegUsuarioPerfil;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-25T16:11:27")
@StaticMetamodel(SegPerfil.class)
public class SegPerfil_ { 

    public static volatile ListAttribute<SegPerfil, SegUsuarioPerfil> segUsuarioPerfilList;
    public static volatile ListAttribute<SegPerfil, SegAccionMenuPerfil> segAccionMenuPerfilList;
    public static volatile SingularAttribute<SegPerfil, Integer> idPerfil;
    public static volatile SingularAttribute<SegPerfil, Boolean> estadoPerfil;
    public static volatile SingularAttribute<SegPerfil, String> nombrePerfil;

}