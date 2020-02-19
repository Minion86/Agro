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
import javax.persistence.FetchType;
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
import org.Adquisicion.Entities.Ubicacion;

/**
 *
 * @author nmartinez
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
    @Column(name = "fecha_plantacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPlantacion;
    @OneToMany(mappedBy = "plantacion", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PlantacionDetalle> plantacionDetalleList;
    @Column(name = "nombre_plantacion")
    private String nombrePlantacion;
    @Transient
    private Date fechaPlantacionDesde;
    @Transient
    private Date fechaPlantacionHasta;
    @Transient
    private Ubicacion idUbicacion;
    @Transient
    private Ubicacion idUbicacionPadre;
    @Column(name = "estado_cosecha")
    private Boolean estadoCosecha;
    @Column(name = "fecha_cosecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCosecha;
    @Column(name = "estado_plantacion")
    private Boolean estadoPlantacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_ubicacion")
    private Integer idUbicacionInt;
    @Transient
    private String producto;
    @OneToMany(mappedBy = "idPlantacion", fetch = FetchType.LAZY)
    private List<WeatherMap> weatherMapList;

    public Plantacion() {
    }

    public Plantacion(Long idPlantacion) {
        this.idPlantacion = idPlantacion;
    }

    public Plantacion(Long idPlantacion, Integer idUbicacionInt) {
        this.idPlantacion = idPlantacion;
        this.idUbicacionInt = idUbicacionInt;
    }

    public Long getIdPlantacion() {
        return idPlantacion;
    }

    public void setIdPlantacion(Long idPlantacion) {
        this.idPlantacion = idPlantacion;
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
     * @return the idUbicacionInt
     */
    public Integer getIdUbicacionInt() {
        return idUbicacionInt;
    }

    /**
     * @param idUbicacionInt the idUbicacionInt to set
     */
    public void setIdUbicacionInt(Integer idUbicacionInt) {
        this.idUbicacionInt = idUbicacionInt;
    }

    /**
     * @return the idUbicacion
     */
    public Ubicacion getIdUbicacion() {
        return idUbicacion;
    }

    /**
     * @param idUbicacion the idUbicacion to set
     */
    public void setIdUbicacion(Ubicacion idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    /**
     * @return the estadoCosecha
     */
    public Boolean getEstadoCosecha() {
        return estadoCosecha;
    }

    /**
     * @param estadoCosecha the estadoCosecha to set
     */
    public void setEstadoCosecha(Boolean estadoCosecha) {
        this.estadoCosecha = estadoCosecha;
    }

    /**
     * @return the fechaCosecha
     */
    public Date getFechaCosecha() {
        return fechaCosecha;
    }

    /**
     * @param fechaCosecha the fechaCosecha to set
     */
    public void setFechaCosecha(Date fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
    }

    /**
     * @return the estadoPlantacion
     */
    public Boolean getEstadoPlantacion() {
        return estadoPlantacion;
    }

    /**
     * @param estadoPlantacion the estadoPlantacion to set
     */
    public void setEstadoPlantacion(Boolean estadoPlantacion) {
        this.estadoPlantacion = estadoPlantacion;
    }

    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }

    /**
     * @return the weatherMapList
     */
    public List<WeatherMap> getWeatherMapList() {
        return weatherMapList;
    }

    /**
     * @param weatherMapList the weatherMapList to set
     */
    public void setWeatherMapList(List<WeatherMap> weatherMapList) {
        this.weatherMapList = weatherMapList;
    }

}
