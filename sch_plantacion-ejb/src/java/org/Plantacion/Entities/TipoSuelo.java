/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Entities;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nmartinez
 */
@Entity
@Table(name = "tipo_suelo", schema = "sch_plantacion")
@XmlRootElement
public class TipoSuelo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_suelo")
    private Integer idTipoSuelo;
    @Size(max = 200)
    @Column(name = "nombre_tipo_suelo")
    private String nombreTipoSuelo;
    @Size(max = 200)
    @Column(name = "nivel_ph_tipo_suelo")
    private String nivelPhTipoSuelo;
    @Size(max = 500)
    @Column(name = "descripcion_tipo_suelo")
    private String descripcionTipoSuelo;
    @Column(name = "estado_tipo_suelo")
    private Boolean estadoTipoSuelo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoSuelo", fetch = FetchType.LAZY)
    private List<PlantacionDetalle> plantacionDetalleList;

    public TipoSuelo() {
    }

    public TipoSuelo(Integer idTipoSuelo) {
        this.idTipoSuelo = idTipoSuelo;
    }

    public Integer getIdTipoSuelo() {
        return idTipoSuelo;
    }

    public void setIdTipoSuelo(Integer idTipoSuelo) {
        this.idTipoSuelo = idTipoSuelo;
    }

    public String getNombreTipoSuelo() {
        return nombreTipoSuelo;
    }

    public void setNombreTipoSuelo(String nombreTipoSuelo) {
        this.nombreTipoSuelo = nombreTipoSuelo;
    }

    public String getNivelPhTipoSuelo() {
        return nivelPhTipoSuelo;
    }

    public void setNivelPhTipoSuelo(String nivelPhTipoSuelo) {
        this.nivelPhTipoSuelo = nivelPhTipoSuelo;
    }

    public String getDescripcionTipoSuelo() {
        return descripcionTipoSuelo;
    }

    public void setDescripcionTipoSuelo(String descripcionTipoSuelo) {
        this.descripcionTipoSuelo = descripcionTipoSuelo;
    }

    public Boolean getEstadoTipoSuelo() {
        return estadoTipoSuelo;
    }

    public void setEstadoTipoSuelo(Boolean estadoTipoSuelo) {
        this.estadoTipoSuelo = estadoTipoSuelo;
    }

    @XmlTransient
    public List<PlantacionDetalle> getPlantacionDetalleList() {
        return plantacionDetalleList;
    }

    public void setPlantacionDetalleList(List<PlantacionDetalle> plantacionDetalleList) {
        this.plantacionDetalleList = plantacionDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoSuelo != null ? idTipoSuelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoSuelo)) {
            return false;
        }
        TipoSuelo other = (TipoSuelo) object;
        if ((this.idTipoSuelo == null && other.idTipoSuelo != null) || (this.idTipoSuelo != null && !this.idTipoSuelo.equals(other.idTipoSuelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Plantacion.entities.TipoSuelo[ idTipoSuelo=" + idTipoSuelo + " ]";
    }
    
}
