package org.Seguridades.Entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.Seguridades.Entities.SegAccionMenuPerfil;
import org.Seguridades.Entities.SegAcciones;
import org.Seguridades.Entities.SegMenu;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-30T08:41:21")
@StaticMetamodel(SegAccionMenu.class)
public class SegAccionMenu_ { 

    public static volatile ListAttribute<SegAccionMenu, SegAccionMenuPerfil> segAccionMenuPerfilList;
    public static volatile SingularAttribute<SegAccionMenu, SegMenu> idMenu;
    public static volatile SingularAttribute<SegAccionMenu, Integer> idAccionOpcion;
    public static volatile SingularAttribute<SegAccionMenu, SegAcciones> idAcciones;

}