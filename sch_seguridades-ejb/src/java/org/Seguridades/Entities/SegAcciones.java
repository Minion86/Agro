/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "seg_acciones", schema = "sch_seguridades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegAcciones.findAll", query = "SELECT s FROM SegAcciones s"),
    @NamedQuery(name = "SegAcciones.findByIdAcciones", query = "SELECT s FROM SegAcciones s WHERE s.idAcciones = :idAcciones"),
    @NamedQuery(name = "SegAcciones.findByNombreAccion", query = "SELECT s FROM SegAcciones s WHERE s.nombreAccion = :nombreAccion"),
    @NamedQuery(name = "SegAcciones.findByAccion", query = "SELECT s FROM SegAcciones s WHERE s.nombreAccion = :nombreAccion"),
    @NamedQuery(name = "SegAcciones.findByOpcion", query = "SELECT ssa FROM SegAcciones ssa join ssa.segAccionMenuList soa on ssa=soa.idAcciones  where soa.idMenu =:idMenu"),
    @NamedQuery(name = "SegAcciones.findByPerfil", query = "SELECT ssa FROM SegAcciones ssa join ssa.segAccionMenuList soa on ssa=soa.idAcciones join soa.segAccionMenuPerfilList soap on soa=soap.idAccionOpcion  where soap.idPerfil =:idPerfil")})

public class SegAcciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acciones")
    private Integer idAcciones;
    @Size(max = 100)
    @Column(name = "nombre_accion")
    private String nombreAccion;
    @OneToMany(mappedBy = "idAcciones")
    private List<SegAccionMenu> segAccionMenuList;

    public SegAcciones() {
    }

    public SegAcciones(Integer idAcciones) {
        this.idAcciones = idAcciones;
    }

    public Integer getIdAcciones() {
        return idAcciones;
    }

    public void setIdAcciones(Integer idAcciones) {
        this.idAcciones = idAcciones;
    }

    public String getNombreAccion() {
        return nombreAccion;
    }

    public void setNombreAccion(String nombreAccion) {
        this.nombreAccion = nombreAccion;
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
        hash += (idAcciones != null ? idAcciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegAcciones)) {
            return false;
        }
        SegAcciones other = (SegAcciones) object;
        if ((this.idAcciones == null && other.idAcciones != null) || (this.idAcciones != null && !this.idAcciones.equals(other.idAcciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Seguridades.SegAcciones[ idAcciones=" + idAcciones + " ]";
    }

}
