/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Entities;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "detalle_adquisicion",schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "DetalleAdquisicion.findAll", query = "SELECT d FROM DetalleAdquisicion d"),
    @NamedQuery(name = "DetalleAdquisicion.findByIdDetalleAdquisicion", query = "SELECT d FROM DetalleAdquisicion d WHERE d.idDetalleAdquisicion = :idDetalleAdquisicion"),
    @NamedQuery(name = "DetalleAdquisicion.findByCantidadDetalleAdquisicion", query = "SELECT d FROM DetalleAdquisicion d WHERE d.cantidadDetalleAdquisicion = :cantidadDetalleAdquisicion"),
    @NamedQuery(name = "DetalleAdquisicion.findByValorDetalleAdquisicion", query = "SELECT d FROM DetalleAdquisicion d WHERE d.valorDetalleAdquisicion = :valorDetalleAdquisicion"),
    @NamedQuery(name = "DetalleAdquisicion.findByValorLibrosDetalleAdquisicion", query = "SELECT d FROM DetalleAdquisicion d WHERE d.valorLibrosDetalleAdquisicion = :valorLibrosDetalleAdquisicion"),
    @NamedQuery(name = "DetalleAdquisicion.findByCodigoBien", query = "SELECT d FROM DetalleAdquisicion d WHERE d.codigoBien = :codigoBien"),
    @NamedQuery(name = "DetalleAdquisicion.findByCodigoAnteriorBien", query = "SELECT d FROM DetalleAdquisicion d WHERE d.codigoAnteriorBien = :codigoAnteriorBien"),
    @NamedQuery(name = "DetalleAdquisicion.findByEstadoDetalle", query = "SELECT d FROM DetalleAdquisicion d WHERE d.estadoDetalle = :estadoDetalle")})
public class DetalleAdquisicion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_adquisicion")
    private Integer idDetalleAdquisicion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad_detalle_adquisicion")
    private Double cantidadDetalleAdquisicion;
    @Column(name = "valor_detalle_adquisicion")
    private Double valorDetalleAdquisicion;
    @Column(name = "valor_libros_detalle_adquisicion")
    private Double valorLibrosDetalleAdquisicion;
    @Size(max = 100)
    @Column(name = "codigo_bien")
    private String codigoBien;
    @Size(max = 100)
    @Column(name = "codigo_anterior_bien")
    private String codigoAnteriorBien;
    @Column(name = "estado_detalle")
    private Boolean estadoDetalle;
    @OneToMany(mappedBy = "idDetalleAdquisicion")
    private List<DetalleAsignacionBien> detalleAsignacionBienList;
    @OneToMany(mappedBy = "idDetalleAdquisicion")
    private List<DepreciacionBien> depreciacionBienList;
    @JoinColumn(name = "id_adquisicion", referencedColumnName = "id_adquisicion")
    @ManyToOne
    private Adquisicion idAdquisicion;
    @JoinColumn(name = "id_bien", referencedColumnName = "id_bien")
    @ManyToOne
    private Bien idBien;
    @JoinColumn(name = "estado_bien", referencedColumnName = "id_catalogo")
    @ManyToOne
    private Catalogo estadoBien;

    public DetalleAdquisicion() {
    }

    public DetalleAdquisicion(Integer idDetalleAdquisicion) {
        this.idDetalleAdquisicion = idDetalleAdquisicion;
    }

    public Integer getIdDetalleAdquisicion() {
        return idDetalleAdquisicion;
    }

    public void setIdDetalleAdquisicion(Integer idDetalleAdquisicion) {
        this.idDetalleAdquisicion = idDetalleAdquisicion;
    }

    public Double getCantidadDetalleAdquisicion() {
        return cantidadDetalleAdquisicion;
    }

    public void setCantidadDetalleAdquisicion(Double cantidadDetalleAdquisicion) {
        this.cantidadDetalleAdquisicion = cantidadDetalleAdquisicion;
    }

    public Double getValorDetalleAdquisicion() {
        return valorDetalleAdquisicion;
    }

    public void setValorDetalleAdquisicion(Double valorDetalleAdquisicion) {
        this.valorDetalleAdquisicion = valorDetalleAdquisicion;
    }

    public Double getValorLibrosDetalleAdquisicion() {
        return valorLibrosDetalleAdquisicion;
    }

    public void setValorLibrosDetalleAdquisicion(Double valorLibrosDetalleAdquisicion) {
        this.valorLibrosDetalleAdquisicion = valorLibrosDetalleAdquisicion;
    }

    public String getCodigoBien() {
        return codigoBien;
    }

    public void setCodigoBien(String codigoBien) {
        this.codigoBien = codigoBien;
    }

    public String getCodigoAnteriorBien() {
        return codigoAnteriorBien;
    }

    public void setCodigoAnteriorBien(String codigoAnteriorBien) {
        this.codigoAnteriorBien = codigoAnteriorBien;
    }

    public Boolean getEstadoDetalle() {
        return estadoDetalle;
    }

    public void setEstadoDetalle(Boolean estadoDetalle) {
        this.estadoDetalle = estadoDetalle;
    }

    @XmlTransient
    public List<DetalleAsignacionBien> getDetalleAsignacionBienList() {
        return detalleAsignacionBienList;
    }

    public void setDetalleAsignacionBienList(List<DetalleAsignacionBien> detalleAsignacionBienList) {
        this.detalleAsignacionBienList = detalleAsignacionBienList;
    }

    @XmlTransient
    public List<DepreciacionBien> getDepreciacionBienList() {
        return depreciacionBienList;
    }

    public void setDepreciacionBienList(List<DepreciacionBien> depreciacionBienList) {
        this.depreciacionBienList = depreciacionBienList;
    }

    public Adquisicion getIdAdquisicion() {
        return idAdquisicion;
    }

    public void setIdAdquisicion(Adquisicion idAdquisicion) {
        this.idAdquisicion = idAdquisicion;
    }

    public Bien getIdBien() {
        return idBien;
    }

    public void setIdBien(Bien idBien) {
        this.idBien = idBien;
    }

    public Catalogo getEstadoBien() {
        return estadoBien;
    }

    public void setEstadoBien(Catalogo estadoBien) {
        this.estadoBien = estadoBien;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleAdquisicion != null ? idDetalleAdquisicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleAdquisicion)) {
            return false;
        }
        DetalleAdquisicion other = (DetalleAdquisicion) object;
        if ((this.idDetalleAdquisicion == null && other.idDetalleAdquisicion != null) || (this.idDetalleAdquisicion != null && !this.idDetalleAdquisicion.equals(other.idDetalleAdquisicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Activos.Entities.DetalleAdquisicion[ idDetalleAdquisicion=" + idDetalleAdquisicion + " ]";
    }
    
}
