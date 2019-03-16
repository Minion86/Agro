/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nmartinez
 */
@Entity
@Table(name = "control_plantacion", schema = "sch_plantacion")
@XmlRootElement
public class ControlPlantacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_control_plantacion")
    private Long idControlPlantacion;
    @Size(max = 2147483647)
    @Column(name = "descripcion_control")
    private String descripcionControl;
    @Column(name = "afeccion")
    private Boolean afeccion;
    @Column(name = "tratamiento")
    private Boolean tratamiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "humedad_suelo")
    private Double humedadSuelo;
    @Column(name = "fecha_control_plantacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaControlPlantacion;
    @JoinColumn(name = "id_plantacion_detalle", referencedColumnName = "id_plantacion_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlantacionDetalle idPlantacionDetalle;
    @Size(max = 2147483647)
    @Column(name = "descripcion_afeccion")
    private String descripcionAfeccion;
    @Size(max = 2147483647)
    @Column(name = "descripcion_tratamiento")
    private String descripcionTratamiento;
    @Column(name = "perdida")
    private Boolean perdida;
    @Size(max = 2147483647)
    @Column(name = "descripcion_perdida")
    private String descripcionPerdida;

    public ControlPlantacion() {
    }

    public ControlPlantacion(Long idControlPlantacion) {
        this.idControlPlantacion = idControlPlantacion;
    }

    public Long getIdControlPlantacion() {
        return idControlPlantacion;
    }

    public void setIdControlPlantacion(Long idControlPlantacion) {
        this.idControlPlantacion = idControlPlantacion;
    }

    public String getDescripcionControl() {
        return descripcionControl;
    }

    public void setDescripcionControl(String descripcionControl) {
        this.descripcionControl = descripcionControl;
    }

    public Boolean getAfeccion() {
        return afeccion;
    }

    public void setAfeccion(Boolean afeccion) {
        this.afeccion = afeccion;
    }

    public Boolean getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Boolean tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Double getHumedadSuelo() {
        return humedadSuelo;
    }

    public void setHumedadSuelo(Double humedadSuelo) {
        this.humedadSuelo = humedadSuelo;
    }

    public Date getFechaControlPlantacion() {
        return fechaControlPlantacion;
    }

    public void setFechaControlPlantacion(Date fechaControlPlantacion) {
        this.fechaControlPlantacion = fechaControlPlantacion;
    }

    public PlantacionDetalle getIdPlantacionDetalle() {
        return idPlantacionDetalle;
    }

    public void setIdPlantacionDetalle(PlantacionDetalle idPlantacionDetalle) {
        this.idPlantacionDetalle = idPlantacionDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idControlPlantacion != null ? idControlPlantacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlPlantacion)) {
            return false;
        }
        ControlPlantacion other = (ControlPlantacion) object;
        if ((this.idControlPlantacion == null && other.idControlPlantacion != null) || (this.idControlPlantacion != null && !this.idControlPlantacion.equals(other.idControlPlantacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Plantacion.Entities.ControlPlantacion[ idControlPlantacion=" + idControlPlantacion + " ]";
    }

    /**
     * @return the descripcionAfeccion
     */
    public String getDescripcionAfeccion() {
        return descripcionAfeccion;
    }

    /**
     * @param descripcionAfeccion the descripcionAfeccion to set
     */
    public void setDescripcionAfeccion(String descripcionAfeccion) {
        this.descripcionAfeccion = descripcionAfeccion;
    }

    /**
     * @return the descripcionTratamiento
     */
    public String getDescripcionTratamiento() {
        return descripcionTratamiento;
    }

    /**
     * @param descripcionTratamiento the descripcionTratamiento to set
     */
    public void setDescripcionTratamiento(String descripcionTratamiento) {
        this.descripcionTratamiento = descripcionTratamiento;
    }

    /**
     * @return the perdida
     */
    public Boolean getPerdida() {
        return perdida;
    }

    /**
     * @param perdida the perdida to set
     */
    public void setPerdida(Boolean perdida) {
        this.perdida = perdida;
    }

    /**
     * @return the descripcionPerdida
     */
    public String getDescripcionPerdida() {
        return descripcionPerdida;
    }

    /**
     * @param descripcionPerdida the descripcionPerdida to set
     */
    public void setDescripcionPerdida(String descripcionPerdida) {
        this.descripcionPerdida = descripcionPerdida;
    }

}
