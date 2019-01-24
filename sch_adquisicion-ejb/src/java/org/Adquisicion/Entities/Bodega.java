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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "bodega", schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "Bodega.findAll", query = "SELECT b FROM Bodega b"),
    @NamedQuery(name = "Bodega.findByIdBodega", query = "SELECT b FROM Bodega b WHERE b.idBodega = :idBodega"),
    @NamedQuery(name = "Bodega.findByNombreBodega", query = "SELECT b FROM Bodega b WHERE b.nombreBodega = :nombreBodega"),
    @NamedQuery(name = "Bodega.findByDireccionBodega", query = "SELECT b FROM Bodega b WHERE b.direccionBodega = :direccionBodega"),
    @NamedQuery(name = "Bodega.findBySectorBodega", query = "SELECT b FROM Bodega b WHERE b.sectorBodega = :sectorBodega"),
    @NamedQuery(name = "Bodega.findByObservacionesBodega", query = "SELECT b FROM Bodega b WHERE b.observacionesBodega = :observacionesBodega"),
    @NamedQuery(name = "Bodega.findByCodigoBodega", query = "SELECT b FROM Bodega b WHERE b.codigoBodega = :codigoBodega")})
public class Bodega implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_bodega")
    private Integer idBodega;
    @Basic(optional = false)
    @Size(max = 300)
    @Column(name = "nombre_bodega")
    private String nombreBodega;
    @Size(max = 300)
    @Column(name = "direccion_bodega")
    private String direccionBodega;
    @Size(max = 300)
    @Column(name = "sector_bodega")
    private String sectorBodega;
    @Size(max = 2147483647)
    @Column(name = "observaciones_bodega")
    private String observacionesBodega;
    @Size(max = 100)
    @Column(name = "codigo_bodega")
    private String codigoBodega;
    @OneToMany(mappedBy = "idBodega")
    private List<Adquisicion> adquisicionList;
    @OneToMany(mappedBy = "padreBodega")
    private List<Bodega> bodegaList;
    @JoinColumn(name = "padre_bodega", referencedColumnName = "id_bodega")
    @ManyToOne
    private Bodega padreBodega;
    @JoinColumn(name = "tipo_bodega", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private Catalogo tipoBodega;
    @JoinColumn(name = "clase_bodega", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private Catalogo claseBodega;
    @JoinColumn(name = "id_institucion", referencedColumnName = "id_institucion")
    @ManyToOne
    private Institucion idInstitucion;
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "id_ubicacion")
    @ManyToOne(optional = false)
    private Ubicacion idUbicacion;
    @Transient
    private Ubicacion idUbicacionPadre;
    @Column(name = "estado_bodega")
    private Boolean estadoBodega;

    public Bodega() {
    }

    public Bodega(Integer idBodega) {
        this.idBodega = idBodega;
    }

    public Bodega(Integer idBodega, String nombreBodega) {
        this.idBodega = idBodega;
        this.nombreBodega = nombreBodega;
    }

    public Integer getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Integer idBodega) {
        this.idBodega = idBodega;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public String getDireccionBodega() {
        return direccionBodega;
    }

    public void setDireccionBodega(String direccionBodega) {
        this.direccionBodega = direccionBodega;
    }

    public String getSectorBodega() {
        return sectorBodega;
    }

    public void setSectorBodega(String sectorBodega) {
        this.sectorBodega = sectorBodega;
    }

    public String getObservacionesBodega() {
        return observacionesBodega;
    }

    public void setObservacionesBodega(String observacionesBodega) {
        this.observacionesBodega = observacionesBodega;
    }

    public String getCodigoBodega() {
        return codigoBodega;
    }

    public void setCodigoBodega(String codigoBodega) {
        this.codigoBodega = codigoBodega;
    }

    @XmlTransient
    public List<Adquisicion> getAdquisicionList() {
        return adquisicionList;
    }

    public void setAdquisicionList(List<Adquisicion> adquisicionList) {
        this.adquisicionList = adquisicionList;
    }

    @XmlTransient
    public List<Bodega> getBodegaList() {
        return bodegaList;
    }

    public void setBodegaList(List<Bodega> bodegaList) {
        this.bodegaList = bodegaList;
    }

    public Bodega getPadreBodega() {
        return padreBodega;
    }

    public void setPadreBodega(Bodega padreBodega) {
        this.padreBodega = padreBodega;
    }

    public Catalogo getTipoBodega() {
        return tipoBodega;
    }

    public void setTipoBodega(Catalogo tipoBodega) {
        this.tipoBodega = tipoBodega;
    }

    public Catalogo getClaseBodega() {
        return claseBodega;
    }

    public void setClaseBodega(Catalogo claseBodega) {
        this.claseBodega = claseBodega;
    }

    public Institucion getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Institucion idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Ubicacion getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Ubicacion idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBodega != null ? idBodega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bodega)) {
            return false;
        }
        Bodega other = (Bodega) object;
        if ((this.idBodega == null && other.idBodega != null) || (this.idBodega != null && !this.idBodega.equals(other.idBodega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCodigoBodega()+"-"+getNombreBodega();
    }

    /**
     * @return the idUbicacionPadre
     */
    public Ubicacion getIdUbicacionPadre() {
        return idUbicacionPadre;
    }

    /**
     * @param idUbicacionPadre the idUbicacionPadre to set
     */
    public void setIdUbicacionPadre(Ubicacion idUbicacionPadre) {
        this.idUbicacionPadre = idUbicacionPadre;
    }

    /**
     * @return the estadoBodega
     */
    public Boolean getEstadoBodega() {
        return estadoBodega;
    }

    /**
     * @param estadoBodega the estadoBodega to set
     */
    public void setEstadoBodega(Boolean estadoBodega) {
        this.estadoBodega = estadoBodega;
    }

}
