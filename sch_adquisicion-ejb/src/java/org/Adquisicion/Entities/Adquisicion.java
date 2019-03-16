/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nmartinez
 */
@Entity
@Table(name = "adquisicion", schema = "sch_adquisicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adquisicion.findAll", query = "SELECT a FROM Adquisicion a"),
    @NamedQuery(name = "Adquisicion.findByIdAdquisicion", query = "SELECT a FROM Adquisicion a WHERE a.idAdquisicion = :idAdquisicion"),
    @NamedQuery(name = "Adquisicion.findByCedulaResponsableAdquisicion", query = "SELECT a FROM Adquisicion a WHERE a.cedulaResponsableAdquisicion = :cedulaResponsableAdquisicion"),
    @NamedQuery(name = "Adquisicion.findByNombresResponsableAdquisicion", query = "SELECT a FROM Adquisicion a WHERE a.nombresResponsableAdquisicion = :nombresResponsableAdquisicion"),
    @NamedQuery(name = "Adquisicion.findByApellidosResponsableAdquisicion", query = "SELECT a FROM Adquisicion a WHERE a.apellidosResponsableAdquisicion = :apellidosResponsableAdquisicion"),
    @NamedQuery(name = "Adquisicion.findByEmailResponsableAdquisicion", query = "SELECT a FROM Adquisicion a WHERE a.emailResponsableAdquisicion = :emailResponsableAdquisicion"),
    @NamedQuery(name = "Adquisicion.findByFechaAdquisicion", query = "SELECT a FROM Adquisicion a WHERE a.fechaAdquisicion = :fechaAdquisicion"),
    @NamedQuery(name = "Adquisicion.findByOrigenAdquisicion", query = "SELECT a FROM Adquisicion a WHERE a.origenAdquisicion = :origenAdquisicion"),
    @NamedQuery(name = "Adquisicion.findByNumeroFacturaBien", query = "SELECT a FROM Adquisicion a WHERE a.numeroFacturaBien = :numeroFacturaBien")})
public class Adquisicion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_adquisicion")
    private Integer idAdquisicion;
    @Size(max = 20)
    @Column(name = "cedula_responsable_adquisicion")
    private String cedulaResponsableAdquisicion;
    @Size(max = 200)
    @Column(name = "nombres_responsable_adquisicion")
    private String nombresResponsableAdquisicion;
    @Size(max = 200)
    @Column(name = "apellidos_responsable_adquisicion")
    private String apellidosResponsableAdquisicion;
    @Size(max = 100)
    @Column(name = "email_responsable_adquisicion")
    private String emailResponsableAdquisicion;
    @Column(name = "fecha_adquisicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAdquisicion;
    @Size(max = 100)
    @Column(name = "origen_adquisicion")
    private String origenAdquisicion;
    @Size(max = 100)
    @Column(name = "numero_factura_bien")
    private String numeroFacturaBien;
    @JoinColumn(name = "id_bodega", referencedColumnName = "id_bodega")
    @ManyToOne
    private Bodega idBodega;
    @OneToMany(mappedBy = "idAdquisicion",cascade = CascadeType.ALL)
    private List<DetalleAdquisicion> detalleAdquisicionList;
    @Transient
    private Date fechaAdquisicionDesde;
    @Transient
    private Date fechaAdquisicionHasta;
    @Transient
    private Integer idBodegaInt;
    

    public Adquisicion() {
    }

    public Adquisicion(Integer idAdquisicion) {
        this.idAdquisicion = idAdquisicion;
    }

    public Integer getIdAdquisicion() {
        return idAdquisicion;
    }

    public void setIdAdquisicion(Integer idAdquisicion) {
        this.idAdquisicion = idAdquisicion;
    }

    public String getCedulaResponsableAdquisicion() {
        return cedulaResponsableAdquisicion;
    }

    public void setCedulaResponsableAdquisicion(String cedulaResponsableAdquisicion) {
        this.cedulaResponsableAdquisicion = cedulaResponsableAdquisicion;
    }

    public String getNombresResponsableAdquisicion() {
        return nombresResponsableAdquisicion;
    }

    public void setNombresResponsableAdquisicion(String nombresResponsableAdquisicion) {
        this.nombresResponsableAdquisicion = nombresResponsableAdquisicion;
    }

    public String getApellidosResponsableAdquisicion() {
        return apellidosResponsableAdquisicion;
    }

    public void setApellidosResponsableAdquisicion(String apellidosResponsableAdquisicion) {
        this.apellidosResponsableAdquisicion = apellidosResponsableAdquisicion;
    }

    public String getEmailResponsableAdquisicion() {
        return emailResponsableAdquisicion;
    }

    public void setEmailResponsableAdquisicion(String emailResponsableAdquisicion) {
        this.emailResponsableAdquisicion = emailResponsableAdquisicion;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getOrigenAdquisicion() {
        return origenAdquisicion;
    }

    public void setOrigenAdquisicion(String origenAdquisicion) {
        this.origenAdquisicion = origenAdquisicion;
    }

    public String getNumeroFacturaBien() {
        return numeroFacturaBien;
    }

    public void setNumeroFacturaBien(String numeroFacturaBien) {
        this.numeroFacturaBien = numeroFacturaBien;
    }

    public Bodega getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Bodega idBodega) {
        this.idBodega = idBodega;
    }

    @XmlTransient
    public List<DetalleAdquisicion> getDetalleAdquisicionList() {
        return detalleAdquisicionList;
    }

    public void setDetalleAdquisicionList(List<DetalleAdquisicion> detalleAdquisicionList) {
        this.detalleAdquisicionList = detalleAdquisicionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdquisicion != null ? idAdquisicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adquisicion)) {
            return false;
        }
        Adquisicion other = (Adquisicion) object;
        if ((this.idAdquisicion == null && other.idAdquisicion != null) || (this.idAdquisicion != null && !this.idAdquisicion.equals(other.idAdquisicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Adquisicion.Entities.Adquisicion[ idAdquisicion=" + idAdquisicion + " ]";
    }

    /**
     * @return the fechaAdquisicionDesde
     */
    public Date getFechaAdquisicionDesde() {
        return fechaAdquisicionDesde;
    }

    /**
     * @param fechaAdquisicionDesde the fechaAdquisicionDesde to set
     */
    public void setFechaAdquisicionDesde(Date fechaAdquisicionDesde) {
        this.fechaAdquisicionDesde = fechaAdquisicionDesde;
    }

    /**
     * @return the fechaAdquisicionHasta
     */
    public Date getFechaAdquisicionHasta() {
        return fechaAdquisicionHasta;
    }

    /**
     * @param fechaAdquisicionHasta the fechaAdquisicionHasta to set
     */
    public void setFechaAdquisicionHasta(Date fechaAdquisicionHasta) {
        this.fechaAdquisicionHasta = fechaAdquisicionHasta;
    }

    /**
     * @return the idBodegaInt
     */
    public Integer getIdBodegaInt() {
        return idBodegaInt;
    }

    /**
     * @param idBodegaInt the idBodegaInt to set
     */
    public void setIdBodegaInt(Integer idBodegaInt) {
        this.idBodegaInt = idBodegaInt;
    }

}
