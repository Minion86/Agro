package org.Seguridades.Entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.Seguridades.Entities.SegAccionMenu;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-28T15:42:31")
@StaticMetamodel(SegMenu.class)
public class SegMenu_ { 

    public static volatile SingularAttribute<SegMenu, String> aplicacionMenu;
    public static volatile SingularAttribute<SegMenu, Integer> secuencialMenu;
    public static volatile ListAttribute<SegMenu, SegAccionMenu> segAccionMenuList;
    public static volatile SingularAttribute<SegMenu, Integer> raizMenu;
    public static volatile SingularAttribute<SegMenu, String> tipoMenu;
    public static volatile SingularAttribute<SegMenu, String> textoMenu;
    public static volatile SingularAttribute<SegMenu, Boolean> tieneHijosMenu;
    public static volatile SingularAttribute<SegMenu, String> descripcionMenu;
    public static volatile SingularAttribute<SegMenu, Integer> idMenu;
    public static volatile SingularAttribute<SegMenu, String> urlMenu;

}