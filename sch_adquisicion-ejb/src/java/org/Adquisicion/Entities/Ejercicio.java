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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "ejercicio",schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "Ejercicio.findAll", query = "SELECT e FROM Ejercicio e"),
    @NamedQuery(name = "Ejercicio.findByIdEjercicio", query = "SELECT e FROM Ejercicio e WHERE e.idEjercicio = :idEjercicio"),
    @NamedQuery(name = "Ejercicio.findByAnioEjercicio", query = "SELECT e FROM Ejercicio e WHERE e.anioEjercicio = :anioEjercicio"),
    @NamedQuery(name = "Ejercicio.findByFechaDesdeEjercicio", query = "SELECT e FROM Ejercicio e WHERE e.fechaDesdeEjercicio = :fechaDesdeEjercicio"),
    @NamedQuery(name = "Ejercicio.findByFechaHastaEjercicio", query = "SELECT e FROM Ejercicio e WHERE e.fechaHastaEjercicio = :fechaHastaEjercicio")})
public class Ejercicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ejercicio")
    private Integer idEjercicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio_ejercicio")
    private int anioEjercicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_desde_ejercicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesdeEjercicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hasta_ejercicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHastaEjercicio;
    @OneToMany(mappedBy = "idEjercicio")
    private List<Adquisicion> adquisicionList;

    public Ejercicio() {
    }

    public Ejercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Ejercicio(Integer idEjercicio, int anioEjercicio, Date fechaDesdeEjercicio, Date fechaHastaEjercicio) {
        this.idEjercicio = idEjercicio;
        this.anioEjercicio = anioEjercicio;
        this.fechaDesdeEjercicio = fechaDesdeEjercicio;
        this.fechaHastaEjercicio = fechaHastaEjercicio;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public int getAnioEjercicio() {
        return anioEjercicio;
    }

    public void setAnioEjercicio(int anioEjercicio) {
        this.anioEjercicio = anioEjercicio;
    }

    public Date getFechaDesdeEjercicio() {
        return fechaDesdeEjercicio;
    }

    public void setFechaDesdeEjercicio(Date fechaDesdeEjercicio) {
        this.fechaDesdeEjercicio = fechaDesdeEjercicio;
    }

    public Date getFechaHastaEjercicio() {
        return fechaHastaEjercicio;
    }

    public void setFechaHastaEjercicio(Date fechaHastaEjercicio) {
        this.fechaHastaEjercicio = fechaHastaEjercicio;
    }

    @XmlTransient
    public List<Adquisicion> getAdquisicionList() {
        return adquisicionList;
    }

    public void setAdquisicionList(List<Adquisicion> adquisicionList) {
        this.adquisicionList = adquisicionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEjercicio != null ? idEjercicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ejercicio)) {
            return false;
        }
        Ejercicio other = (Ejercicio) object;
        if ((this.idEjercicio == null && other.idEjercicio != null) || (this.idEjercicio != null && !this.idEjercicio.equals(other.idEjercicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Activos.Entities.Ejercicio[ idEjercicio=" + idEjercicio + " ]";
    }
    
}
