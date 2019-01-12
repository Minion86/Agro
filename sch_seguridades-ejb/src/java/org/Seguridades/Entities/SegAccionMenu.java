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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nelson
 */
@Entity
@Table(name = "seg_accion_menu",schema = "sch_seguridades")
@NamedQueries({
    @NamedQuery(name = "SegAccionMenu.findAll", query = "SELECT s FROM SegAccionMenu s"),
    @NamedQuery(name = "SegAccionMenu.findByIdAccionOpcion", query = "SELECT s FROM SegAccionMenu s WHERE s.idAccionOpcion = :idAccionOpcion")})
public class SegAccionMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accion_opcion")
    private Integer idAccionOpcion;
    @JoinColumn(name = "id_acciones", referencedColumnName = "id_acciones")
    @ManyToOne
    private SegAcciones idAcciones;
    @JoinColumn(name = "id_menu", referencedColumnName = "id_menu")
    @ManyToOne
    private SegMenu idMenu;
    @OneToMany(mappedBy = "idAccionOpcion",cascade = CascadeType.ALL)
    private List<SegAccionMenuPerfil> segAccionMenuPerfilList;

    public SegAccionMenu() {
    }

    public SegAccionMenu(Integer idAccionOpcion) {
        this.idAccionOpcion = idAccionOpcion;
    }

    public Integer getIdAccionOpcion() {
        return idAccionOpcion;
    }

    public void setIdAccionOpcion(Integer idAccionOpcion) {
        this.idAccionOpcion = idAccionOpcion;
    }

    public SegAcciones getIdAcciones() {
        return idAcciones;
    }

    public void setIdAcciones(SegAcciones idAcciones) {
        this.idAcciones = idAcciones;
    }

    public SegMenu getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(SegMenu idMenu) {
        this.idMenu = idMenu;
    }

    @XmlTransient
    public List<SegAccionMenuPerfil> getSegAccionMenuPerfilList() {
        return segAccionMenuPerfilList;
    }

    public void setSegAccionMenuPerfilList(List<SegAccionMenuPerfil> segAccionMenuPerfilList) {
        this.segAccionMenuPerfilList = segAccionMenuPerfilList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccionOpcion != null ? idAccionOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegAccionMenu)) {
            return false;
        }
        SegAccionMenu other = (SegAccionMenu) object;
        if ((this.idAccionOpcion == null && other.idAccionOpcion != null) || (this.idAccionOpcion != null && !this.idAccionOpcion.equals(other.idAccionOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Seguridades.SegAccionMenu[ idAccionOpcion=" + idAccionOpcion + " ]";
    }
    
}
