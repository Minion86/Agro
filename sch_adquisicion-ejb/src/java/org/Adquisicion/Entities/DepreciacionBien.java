/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "depreciacion_bien",schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "DepreciacionBien.findAll", query = "SELECT d FROM DepreciacionBien d"),
    @NamedQuery(name = "DepreciacionBien.findByIdDepreciacionBien", query = "SELECT d FROM DepreciacionBien d WHERE d.idDepreciacionBien = :idDepreciacionBien"),
    @NamedQuery(name = "DepreciacionBien.findByPorcentajeDepreciacionBien", query = "SELECT d FROM DepreciacionBien d WHERE d.porcentajeDepreciacionBien = :porcentajeDepreciacionBien"),
    @NamedQuery(name = "DepreciacionBien.findByValorDepreciacionBien", query = "SELECT d FROM DepreciacionBien d WHERE d.valorDepreciacionBien = :valorDepreciacionBien"),
    @NamedQuery(name = "DepreciacionBien.findByFechaDepreciacionBien", query = "SELECT d FROM DepreciacionBien d WHERE d.fechaDepreciacionBien = :fechaDepreciacionBien")})
public class DepreciacionBien implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_depreciacion_bien")
    private Integer idDepreciacionBien;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentaje_depreciacion_bien")
    private Float porcentajeDepreciacionBien;
    @Column(name = "valor_depreciacion_bien")
    private Float valorDepreciacionBien;
    @Column(name = "fecha_depreciacion_bien")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDepreciacionBien;
    @JoinColumn(name = "id_detalle_adquisicion", referencedColumnName = "id_detalle_adquisicion")
    @ManyToOne
    private DetalleAdquisicion idDetalleAdquisicion;

    public DepreciacionBien() {
    }

    public DepreciacionBien(Integer idDepreciacionBien) {
        this.idDepreciacionBien = idDepreciacionBien;
    }

    public Integer getIdDepreciacionBien() {
        return idDepreciacionBien;
    }

    public void setIdDepreciacionBien(Integer idDepreciacionBien) {
        this.idDepreciacionBien = idDepreciacionBien;
    }

    public Float getPorcentajeDepreciacionBien() {
        return porcentajeDepreciacionBien;
    }

    public void setPorcentajeDepreciacionBien(Float porcentajeDepreciacionBien) {
        this.porcentajeDepreciacionBien = porcentajeDepreciacionBien;
    }

    public Float getValorDepreciacionBien() {
        return valorDepreciacionBien;
    }

    public void setValorDepreciacionBien(Float valorDepreciacionBien) {
        this.valorDepreciacionBien = valorDepreciacionBien;
    }

    public Date getFechaDepreciacionBien() {
        return fechaDepreciacionBien;
    }

    public void setFechaDepreciacionBien(Date fechaDepreciacionBien) {
        this.fechaDepreciacionBien = fechaDepreciacionBien;
    }

    public DetalleAdquisicion getIdDetalleAdquisicion() {
        return idDetalleAdquisicion;
    }

    public void setIdDetalleAdquisicion(DetalleAdquisicion idDetalleAdquisicion) {
        this.idDetalleAdquisicion = idDetalleAdquisicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDepreciacionBien != null ? idDepreciacionBien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepreciacionBien)) {
            return false;
        }
        DepreciacionBien other = (DepreciacionBien) object;
        if ((this.idDepreciacionBien == null && other.idDepreciacionBien != null) || (this.idDepreciacionBien != null && !this.idDepreciacionBien.equals(other.idDepreciacionBien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Activos.Entities.DepreciacionBien[ idDepreciacionBien=" + idDepreciacionBien + " ]";
    }
    
}
