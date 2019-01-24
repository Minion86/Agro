/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Entities;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "detalle_asignacion_bien",schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "DetalleAsignacionBien.findAll", query = "SELECT d FROM DetalleAsignacionBien d"),
    @NamedQuery(name = "DetalleAsignacionBien.findByIdDetalleAsignacionBien", query = "SELECT d FROM DetalleAsignacionBien d WHERE d.idDetalleAsignacionBien = :idDetalleAsignacionBien"),
    @NamedQuery(name = "DetalleAsignacionBien.findByValorBien", query = "SELECT d FROM DetalleAsignacionBien d WHERE d.valorBien = :valorBien"),
    @NamedQuery(name = "DetalleAsignacionBien.findByObservacionesAsignacionBien", query = "SELECT d FROM DetalleAsignacionBien d WHERE d.observacionesAsignacionBien = :observacionesAsignacionBien"),
    @NamedQuery(name = "DetalleAsignacionBien.findByCodigoAnteriorDetalleAsignacionBien", query = "SELECT d FROM DetalleAsignacionBien d WHERE d.codigoAnteriorDetalleAsignacionBien = :codigoAnteriorDetalleAsignacionBien")})
public class DetalleAsignacionBien implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_asignacion_bien")
    private Integer idDetalleAsignacionBien;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_bien")
    private Float valorBien;
    @Size(max = 2147483647)
    @Column(name = "observaciones_asignacion_bien")
    private String observacionesAsignacionBien;
    @Size(max = 10)
    @Column(name = "codigo_anterior_detalle_asignacion_bien")
    private String codigoAnteriorDetalleAsignacionBien;
    @JoinColumn(name = "id_asignacion_bien", referencedColumnName = "id_asignacion_bien")
    @ManyToOne
    private AsignacionBien idAsignacionBien;
    @JoinColumn(name = "id_catalogo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private Catalogo idCatalogo;
    @JoinColumn(name = "id_detalle_adquisicion", referencedColumnName = "id_detalle_adquisicion")
    @ManyToOne
    private DetalleAdquisicion idDetalleAdquisicion;

    public DetalleAsignacionBien() {
    }

    public DetalleAsignacionBien(Integer idDetalleAsignacionBien) {
        this.idDetalleAsignacionBien = idDetalleAsignacionBien;
    }

    public Integer getIdDetalleAsignacionBien() {
        return idDetalleAsignacionBien;
    }

    public void setIdDetalleAsignacionBien(Integer idDetalleAsignacionBien) {
        this.idDetalleAsignacionBien = idDetalleAsignacionBien;
    }

    public Float getValorBien() {
        return valorBien;
    }

    public void setValorBien(Float valorBien) {
        this.valorBien = valorBien;
    }

    public String getObservacionesAsignacionBien() {
        return observacionesAsignacionBien;
    }

    public void setObservacionesAsignacionBien(String observacionesAsignacionBien) {
        this.observacionesAsignacionBien = observacionesAsignacionBien;
    }

    public String getCodigoAnteriorDetalleAsignacionBien() {
        return codigoAnteriorDetalleAsignacionBien;
    }

    public void setCodigoAnteriorDetalleAsignacionBien(String codigoAnteriorDetalleAsignacionBien) {
        this.codigoAnteriorDetalleAsignacionBien = codigoAnteriorDetalleAsignacionBien;
    }

    public AsignacionBien getIdAsignacionBien() {
        return idAsignacionBien;
    }

    public void setIdAsignacionBien(AsignacionBien idAsignacionBien) {
        this.idAsignacionBien = idAsignacionBien;
    }

    public Catalogo getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Catalogo idCatalogo) {
        this.idCatalogo = idCatalogo;
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
        hash += (idDetalleAsignacionBien != null ? idDetalleAsignacionBien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleAsignacionBien)) {
            return false;
        }
        DetalleAsignacionBien other = (DetalleAsignacionBien) object;
        if ((this.idDetalleAsignacionBien == null && other.idDetalleAsignacionBien != null) || (this.idDetalleAsignacionBien != null && !this.idDetalleAsignacionBien.equals(other.idDetalleAsignacionBien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Activos.Entities.DetalleAsignacionBien[ idDetalleAsignacionBien=" + idDetalleAsignacionBien + " ]";
    }
    
}
