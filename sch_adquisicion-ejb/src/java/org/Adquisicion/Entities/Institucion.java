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
@Table(name = "institucion",schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "Institucion.findAll", query = "SELECT i FROM Institucion i"),
    @NamedQuery(name = "Institucion.findByIdInstitucion", query = "SELECT i FROM Institucion i WHERE i.idInstitucion = :idInstitucion"),
    @NamedQuery(name = "Institucion.findByNombreInstitucion", query = "SELECT i FROM Institucion i WHERE i.nombreInstitucion = :nombreInstitucion"),
    @NamedQuery(name = "Institucion.findByRucInstitucion", query = "SELECT i FROM Institucion i WHERE i.rucInstitucion = :rucInstitucion"),
    @NamedQuery(name = "Institucion.findByCodigoInstitucion", query = "SELECT i FROM Institucion i WHERE i.codigoInstitucion = :codigoInstitucion")})
public class Institucion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_institucion")
    private Integer idInstitucion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "nombre_institucion")
    private String nombreInstitucion;
    @Size(max = 20)
    @Column(name = "ruc_institucion")
    private String rucInstitucion;
    @Size(max = 100)
    @Column(name = "codigo_institucion")
    private String codigoInstitucion;
    @OneToMany(mappedBy = "idInstitucion")
    private List<Adquisicion> adquisicionList;
    @OneToMany(mappedBy = "idInstitucion")
    private List<Bodega> bodegaList;
    @OneToMany(mappedBy = "institucionDestinoAsignacionBien")
    private List<AsignacionBien> asignacionBienList;

    public Institucion() {
    }

    public Institucion(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Institucion(Integer idInstitucion, String nombreInstitucion) {
        this.idInstitucion = idInstitucion;
        this.nombreInstitucion = nombreInstitucion;
    }

    public Integer getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getRucInstitucion() {
        return rucInstitucion;
    }

    public void setRucInstitucion(String rucInstitucion) {
        this.rucInstitucion = rucInstitucion;
    }

    public String getCodigoInstitucion() {
        return codigoInstitucion;
    }

    public void setCodigoInstitucion(String codigoInstitucion) {
        this.codigoInstitucion = codigoInstitucion;
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

    @XmlTransient
    public List<AsignacionBien> getAsignacionBienList() {
        return asignacionBienList;
    }

    public void setAsignacionBienList(List<AsignacionBien> asignacionBienList) {
        this.asignacionBienList = asignacionBienList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInstitucion != null ? idInstitucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institucion)) {
            return false;
        }
        Institucion other = (Institucion) object;
        if ((this.idInstitucion == null && other.idInstitucion != null) || (this.idInstitucion != null && !this.idInstitucion.equals(other.idInstitucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombreInstitucion();
    }
    
}
