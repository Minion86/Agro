/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nelson
 */
@Entity
@Table(name = "seg_accion_menu_perfil",schema = "sch_seguridades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegAccionMenuPerfil.findAll", query = "SELECT s FROM SegAccionMenuPerfil s"),
    @NamedQuery(name = "SegAccionMenuPerfil.findByIdAccionOpcionPerfil", query = "SELECT s FROM SegAccionMenuPerfil s WHERE s.idAccionOpcionPerfil = :idAccionOpcionPerfil")})
public class SegAccionMenuPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accion_opcion_perfil")
    private Integer idAccionOpcionPerfil;
    @JoinColumn(name = "id_accion_opcion", referencedColumnName = "id_accion_opcion")
    @ManyToOne
    private SegAccionMenu idAccionOpcion;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil")
    @ManyToOne
    private SegPerfil idPerfil;

    public SegAccionMenuPerfil() {
    }

    public SegAccionMenuPerfil(Integer idAccionOpcionPerfil) {
        this.idAccionOpcionPerfil = idAccionOpcionPerfil;
    }

    public Integer getIdAccionOpcionPerfil() {
        return idAccionOpcionPerfil;
    }

    public void setIdAccionOpcionPerfil(Integer idAccionOpcionPerfil) {
        this.idAccionOpcionPerfil = idAccionOpcionPerfil;
    }

    public SegAccionMenu getIdAccionOpcion() {
        return idAccionOpcion;
    }

    public void setIdAccionOpcion(SegAccionMenu idAccionOpcion) {
        this.idAccionOpcion = idAccionOpcion;
    }

    public SegPerfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(SegPerfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccionOpcionPerfil != null ? idAccionOpcionPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegAccionMenuPerfil)) {
            return false;
        }
        SegAccionMenuPerfil other = (SegAccionMenuPerfil) object;
        if ((this.idAccionOpcionPerfil == null && other.idAccionOpcionPerfil != null) || (this.idAccionOpcionPerfil != null && !this.idAccionOpcionPerfil.equals(other.idAccionOpcionPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Seguridades.SegAccionMenuPerfil[ idAccionOpcionPerfil=" + idAccionOpcionPerfil + " ]";
    }
    
}
