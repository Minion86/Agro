/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "ubicacion",schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "Ubicacion.findAll", query = "SELECT u FROM Ubicacion u"),
    @NamedQuery(name = "Ubicacion.findByIdUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.idUbicacion = :idUbicacion"),
    @NamedQuery(name = "Ubicacion.findByNombreUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.nombreUbicacion = :nombreUbicacion"),
    @NamedQuery(name = "Ubicacion.findByNivelUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.nivelUbicacion = :nivelUbicacion")})
public class Ubicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ubicacion")
    private Integer idUbicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "nombre_ubicacion")
    private String nombreUbicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel_ubicacion")
    private int nivelUbicacion;
    @OneToMany(mappedBy = "padreId")
    private List<Ubicacion> ubicacionList;
    @JoinColumn(name = "padre_id", referencedColumnName = "id_ubicacion")
    @ManyToOne
    private Ubicacion padreId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUbicacion")
    private List<Bodega> bodegaList;

    public Ubicacion() {
    }

    public Ubicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Ubicacion(Integer idUbicacion, String nombreUbicacion, int nivelUbicacion) {
        this.idUbicacion = idUbicacion;
        this.nombreUbicacion = nombreUbicacion;
        this.nivelUbicacion = nivelUbicacion;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getNombreUbicacion() {
        return nombreUbicacion;
    }

    public void setNombreUbicacion(String nombreUbicacion) {
        this.nombreUbicacion = nombreUbicacion;
    }

    public int getNivelUbicacion() {
        return nivelUbicacion;
    }

    public void setNivelUbicacion(int nivelUbicacion) {
        this.nivelUbicacion = nivelUbicacion;
    }

    @XmlTransient
    public List<Ubicacion> getUbicacionList() {
        return ubicacionList;
    }

    public void setUbicacionList(List<Ubicacion> ubicacionList) {
        this.ubicacionList = ubicacionList;
    }

    public Ubicacion getPadreId() {
        return padreId;
    }

    public void setPadreId(Ubicacion padreId) {
        this.padreId = padreId;
    }

    @XmlTransient
    public List<Bodega> getBodegaList() {
        return bodegaList;
    }

    public void setBodegaList(List<Bodega> bodegaList) {
        this.bodegaList = bodegaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUbicacion != null ? idUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ubicacion)) {
            return false;
        }
        Ubicacion other = (Ubicacion) object;
        if ((this.idUbicacion == null && other.idUbicacion != null) || (this.idUbicacion != null && !this.idUbicacion.equals(other.idUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombreUbicacion();
    }
    
}
