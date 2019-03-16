/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.Adquisicion.Entities.DetalleAdquisicion;

/**
 *
 * @author nmartinez
 */
@Entity
@Table(name = "plantacion_detalle", schema = "sch_plantacion")
@XmlRootElement
public class PlantacionDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plantacion_detalle")
    private Long idPlantacionDetalle;
    @Transient
    private DetalleAdquisicion idDetalleAdquisicion;
    @Column(name = "id_detalle_adquisicion")
    private Integer idDetalleAdquisicionInt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad_plantacion_detalle")
    private Double cantidadPlantacionDetalle;
    @Size(max = 100)
    @Column(name = "tipo_cantidad_plantacion_detalle")
    private String tipoCantidadPlantacionDetalle;
    @JoinColumn(name = "id_plantacion_detalle", referencedColumnName = "id_plantacion", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Plantacion plantacion;
    @JoinColumn(name = "id_tipo_suelo", referencedColumnName = "id_tipo_suelo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoSuelo idTipoSuelo;
    @Transient
    private Double maximo;
    @Transient
    private Integer idTipoSueloInt;
    @OneToMany(mappedBy = "idPlantacionDetalle", fetch = FetchType.LAZY)
    private List<ControlPlantacion> controlPlantacionList;
    @Column(name = "estado")
    private Integer estado;

    public PlantacionDetalle() {
    }

    public PlantacionDetalle(Long idPlantacionDetalle) {
        this.idPlantacionDetalle = idPlantacionDetalle;
    }

    public Long getIdPlantacionDetalle() {
        return idPlantacionDetalle;
    }

    public void setIdPlantacionDetalle(Long idPlantacionDetalle) {
        this.idPlantacionDetalle = idPlantacionDetalle;
    }

    public Double getCantidadPlantacionDetalle() {
        return cantidadPlantacionDetalle;
    }

    public void setCantidadPlantacionDetalle(Double cantidadPlantacionDetalle) {
        this.cantidadPlantacionDetalle = cantidadPlantacionDetalle;
    }

    public String getTipoCantidadPlantacionDetalle() {
        return tipoCantidadPlantacionDetalle;
    }

    public void setTipoCantidadPlantacionDetalle(String tipoCantidadPlantacionDetalle) {
        this.tipoCantidadPlantacionDetalle = tipoCantidadPlantacionDetalle;
    }

    public Plantacion getPlantacion() {
        return plantacion;
    }

    public void setPlantacion(Plantacion plantacion) {
        this.plantacion = plantacion;
    }

    public TipoSuelo getIdTipoSuelo() {
        return idTipoSuelo;
    }

    public void setIdTipoSuelo(TipoSuelo idTipoSuelo) {
        this.idTipoSuelo = idTipoSuelo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlantacionDetalle != null ? idPlantacionDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlantacionDetalle)) {
            return false;
        }
        PlantacionDetalle other = (PlantacionDetalle) object;
        if ((this.idPlantacionDetalle == null && other.idPlantacionDetalle != null) || (this.idPlantacionDetalle != null && !this.idPlantacionDetalle.equals(other.idPlantacionDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Plantacion.entities.PlantacionDetalle[ idPlantacionDetalle=" + idPlantacionDetalle + " ]";
    }

    /**
     * @return the maximo
     */
    public Double getMaximo() {
        return maximo;
    }

    /**
     * @param maximo the maximo to set
     */
    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    /**
     * @return the idDetalleAdquisicion
     */
    public DetalleAdquisicion getIdDetalleAdquisicion() {
        return idDetalleAdquisicion;
    }

    /**
     * @param idDetalleAdquisicion the idDetalleAdquisicion to set
     */
    public void setIdDetalleAdquisicion(DetalleAdquisicion idDetalleAdquisicion) {
        this.idDetalleAdquisicion = idDetalleAdquisicion;
    }

    /**
     * @return the idDetalleAdquisicionInt
     */
    public Integer getIdDetalleAdquisicionInt() {
        return idDetalleAdquisicionInt;
    }

    /**
     * @param idDetalleAdquisicionInt the idDetalleAdquisicionInt to set
     */
    public void setIdDetalleAdquisicionInt(Integer idDetalleAdquisicionInt) {
        this.idDetalleAdquisicionInt = idDetalleAdquisicionInt;
    }

    /**
     * @return the idTipoSueloInt
     */
    public Integer getIdTipoSueloInt() {
        return idTipoSueloInt;
    }

    /**
     * @param idTipoSueloInt the idTipoSueloInt to set
     */
    public void setIdTipoSueloInt(Integer idTipoSueloInt) {
        this.idTipoSueloInt = idTipoSueloInt;
    }

    @XmlTransient
    public List<ControlPlantacion> getControlPlantacionList() {
        return controlPlantacionList;
    }

    public void setControlPlantacionList(List<ControlPlantacion> controlPlantacionList) {
        this.controlPlantacionList = controlPlantacionList;
    }

    /**
     * @return the estado
     */
    public Integer getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

}
