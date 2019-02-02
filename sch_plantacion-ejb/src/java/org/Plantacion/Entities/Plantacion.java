/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Entities;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "plantacion", schema = "sch_plantacion")
@XmlRootElement
public class Plantacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plantacion")
    private Long idPlantacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_ubicacion")
    private Integer idUbicacion;
    @Column(name = "fecha_plantacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPlantacion;
    @OneToMany(mappedBy = "idPlantacion", cascade = CascadeType.ALL)
    private List<PlantacionDetalle> plantacionDetalleList;
    @Column(name = "nombre_plantacion")
    private String nombrePlantacion;
    @Transient
    private Date fechaPlantacionDesde;
    @Transient
    private Date fechaPlantacionHasta;

    public Plantacion() {
    }

    public Plantacion(Long idPlantacion) {
        this.idPlantacion = idPlantacion;
    }

    public Plantacion(Long idPlantacion, Integer idUbicacion) {
        this.idPlantacion = idPlantacion;
        this.idUbicacion = idUbicacion;
    }

    public Long getIdPlantacion() {
        return idPlantacion;
    }

    public void setIdPlantacion(Long idPlantacion) {
        this.idPlantacion = idPlantacion;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Date getFechaPlantacion() {
        return fechaPlantacion;
    }

    public void setFechaPlantacion(Date fechaPlantacion) {
        this.fechaPlantacion = fechaPlantacion;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idPlantacion != null ? idPlantacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plantacion)) {
            return false;
        }
        Plantacion other = (Plantacion) object;
        if ((this.idPlantacion == null && other.idPlantacion != null) || (this.idPlantacion != null && !this.idPlantacion.equals(other.idPlantacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Plantacion.entities.Plantacion[ idPlantacion=" + idPlantacion + " ]";
    }

    /**
     * @return the nombrePlantacion
     */
    public String getNombrePlantacion() {
        return nombrePlantacion;
    }

    /**
     * @param nombrePlantacion the nombrePlantacion to set
     */
    public void setNombrePlantacion(String nombrePlantacion) {
        this.nombrePlantacion = nombrePlantacion;
    }

    /**
     * @return the fechaPlantacionDesde
     */
    public Date getFechaPlantacionDesde() {
        return fechaPlantacionDesde;
    }

    /**
     * @param fechaPlantacionDesde the fechaPlantacionDesde to set
     */
    public void setFechaPlantacionDesde(Date fechaPlantacionDesde) {
        this.fechaPlantacionDesde = fechaPlantacionDesde;
    }

    /**
     * @return the fechaPlantacionHasta
     */
    public Date getFechaPlantacionHasta() {
        return fechaPlantacionHasta;
    }

    /**
     * @param fechaPlantacionHasta the fechaPlantacionHasta to set
     */
    public void setFechaPlantacionHasta(Date fechaPlantacionHasta) {
        this.fechaPlantacionHasta = fechaPlantacionHasta;
    }

    /**
     * @return the plantacionDetalleList
     */
    public List<PlantacionDetalle> getPlantacionDetalleList() {
        return plantacionDetalleList;
    }

    /**
     * @param plantacionDetalleList the plantacionDetalleList to set
     */
    public void setPlantacionDetalleList(List<PlantacionDetalle> plantacionDetalleList) {
        this.plantacionDetalleList = plantacionDetalleList;
    }

}
