/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nelson
 */
@Entity
@Table(name = "seg_menu",schema = "sch_seguridades")
@NamedQueries({
    @NamedQuery(name = "SegMenu.findAll", query = "SELECT s FROM SegMenu s"),
    @NamedQuery(name = "SegMenu.findByIdMenu", query = "SELECT s FROM SegMenu s WHERE s.idMenu = :idMenu"),
    @NamedQuery(name = "SegMenu.findByAplicacionMenu", query = "SELECT s FROM SegMenu s WHERE s.aplicacionMenu = :aplicacionMenu"),
    @NamedQuery(name = "SegMenu.findByTipoMenu", query = "SELECT s FROM SegMenu s WHERE s.tipoMenu = :tipoMenu"),
    @NamedQuery(name = "SegMenu.findByDescripcionMenu", query = "SELECT s FROM SegMenu s WHERE s.descripcionMenu = :descripcionMenu"),
    @NamedQuery(name = "SegMenu.findByTextoMenu", query = "SELECT s FROM SegMenu s WHERE s.textoMenu = :textoMenu"),
    @NamedQuery(name = "SegMenu.findBySecuencialMenu", query = "SELECT s FROM SegMenu s WHERE s.secuencialMenu = :secuencialMenu"),
    @NamedQuery(name = "SegMenu.findByUrlMenu", query = "SELECT s FROM SegMenu s WHERE s.urlMenu = :urlMenu"),
    @NamedQuery(name = "SegMenu.findByRaizMenu", query = "SELECT s FROM SegMenu s WHERE s.raizMenu = :raizMenu"),
    @NamedQuery(name = "SegMenu.findByTieneHijosMenu", query = "SELECT s FROM SegMenu s WHERE s.tieneHijosMenu = :tieneHijosMenu"),
    @NamedQuery(name = "SegMenu.findByPermisos", query = "SELECT so FROM SegMenu so join so.segAccionMenuList soa on so=soa.idMenu join soa.segAccionMenuPerfilList soap on soa=soap.idAccionOpcion join soap.idPerfil sp on soap=sp.segAccionMenuPerfilList where sp.idPerfil= :idPerfil and soa.idAcciones.idAcciones= :idAcciones order by so.secuencialMenu DESC"),   
    @NamedQuery(name = "SegMenu.findByPerfil", query = "SELECT so FROM SegMenu so join so.segAccionMenuList soa on so=soa.idMenu join soa.segAccionMenuPerfilList soap on soa=soap.idAccionOpcion join soap.idPerfil sp on soap=sp.segAccionMenuPerfilList where sp.idPerfil= :idPerfil")})   

public class SegMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_menu")
    private Integer idMenu;
    @Size(max = 200)
    @Column(name = "aplicacion_menu")
    private String aplicacionMenu;
    @Size(max = 100)
    @Column(name = "tipo_menu")
    private String tipoMenu;
    @Size(max = 300)
    @Column(name = "descripcion_menu")
    private String descripcionMenu;
    @Size(max = 100)
    @Column(name = "texto_menu")
    private String textoMenu;
    @Column(name = "secuencial_menu")
    private Integer secuencialMenu;
    @Size(max = 600)
    @Column(name = "url_menu")
    private String urlMenu;
    @Column(name = "raiz_menu")
    private Integer raizMenu;
    @Column(name = "tiene_hijos_menu")
    private Boolean tieneHijosMenu;
    @OneToMany(mappedBy = "idMenu",cascade = CascadeType.ALL)
    private List<SegAccionMenu> segAccionMenuList;

    public SegMenu() {
    }

    public SegMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public String getAplicacionMenu() {
        return aplicacionMenu;
    }

    public void setAplicacionMenu(String aplicacionMenu) {
        this.aplicacionMenu = aplicacionMenu;
    }

    public String getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(String tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

    public String getDescripcionMenu() {
        return descripcionMenu;
    }

    public void setDescripcionMenu(String descripcionMenu) {
        this.descripcionMenu = descripcionMenu;
    }

    public String getTextoMenu() {
        return textoMenu;
    }

    public void setTextoMenu(String textoMenu) {
        this.textoMenu = textoMenu;
    }

    public Integer getSecuencialMenu() {
        return secuencialMenu;
    }

    public void setSecuencialMenu(Integer secuencialMenu) {
        this.secuencialMenu = secuencialMenu;
    }

    public String getUrlMenu() {
        return urlMenu;
    }

    public void setUrlMenu(String urlMenu) {
        this.urlMenu = urlMenu;
    }

    public Integer getRaizMenu() {
        return raizMenu;
    }

    public void setRaizMenu(Integer raizMenu) {
        this.raizMenu = raizMenu;
    }

    public Boolean getTieneHijosMenu() {
        return tieneHijosMenu;
    }

    public void setTieneHijosMenu(Boolean tieneHijosMenu) {
        this.tieneHijosMenu = tieneHijosMenu;
    }

    @XmlTransient
    public List<SegAccionMenu> getSegAccionMenuList() {
        return segAccionMenuList;
    }

    public void setSegAccionMenuList(List<SegAccionMenu> segAccionMenuList) {
        this.segAccionMenuList = segAccionMenuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegMenu)) {
            return false;
        }
        SegMenu other = (SegMenu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Seguridades.SegMenu[ idMenu=" + idMenu + " ]";
    }
    
}
