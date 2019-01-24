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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "asignacion_bien",schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "AsignacionBien.findAll", query = "SELECT a FROM AsignacionBien a"),
    @NamedQuery(name = "AsignacionBien.findByIdAsignacionBien", query = "SELECT a FROM AsignacionBien a WHERE a.idAsignacionBien = :idAsignacionBien"),
    @NamedQuery(name = "AsignacionBien.findByCedulaFuncionarioAsignacionBien", query = "SELECT a FROM AsignacionBien a WHERE a.cedulaFuncionarioAsignacionBien = :cedulaFuncionarioAsignacionBien"),
    @NamedQuery(name = "AsignacionBien.findByNombresFuncionarioAsignacionBien", query = "SELECT a FROM AsignacionBien a WHERE a.nombresFuncionarioAsignacionBien = :nombresFuncionarioAsignacionBien"),
    @NamedQuery(name = "AsignacionBien.findByApellidosFuncionarioAsignacionBien", query = "SELECT a FROM AsignacionBien a WHERE a.apellidosFuncionarioAsignacionBien = :apellidosFuncionarioAsignacionBien"),
    @NamedQuery(name = "AsignacionBien.findByAreaAsignacionBien", query = "SELECT a FROM AsignacionBien a WHERE a.areaAsignacionBien = :areaAsignacionBien"),
    @NamedQuery(name = "AsignacionBien.findByFechaAsignacionBien", query = "SELECT a FROM AsignacionBien a WHERE a.fechaAsignacionBien = :fechaAsignacionBien"),
    @NamedQuery(name = "AsignacionBien.findByCedulaFuncionarioOrigen", query = "SELECT a FROM AsignacionBien a WHERE a.cedulaFuncionarioOrigen = :cedulaFuncionarioOrigen"),
    @NamedQuery(name = "AsignacionBien.findByNombresFuncionarioOrigen", query = "SELECT a FROM AsignacionBien a WHERE a.nombresFuncionarioOrigen = :nombresFuncionarioOrigen"),
    @NamedQuery(name = "AsignacionBien.findByApellidosFuncionarioOrigen", query = "SELECT a FROM AsignacionBien a WHERE a.apellidosFuncionarioOrigen = :apellidosFuncionarioOrigen")})
public class AsignacionBien implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_asignacion_bien")
    private Integer idAsignacionBien;
    @Size(max = 20)
    @Column(name = "cedula_funcionario_asignacion_bien")
    private String cedulaFuncionarioAsignacionBien;
    @Size(max = 100)
    @Column(name = "nombres_funcionario_asignacion_bien")
    private String nombresFuncionarioAsignacionBien;
    @Size(max = 100)
    @Column(name = "apellidos_funcionario_asignacion_bien")
    private String apellidosFuncionarioAsignacionBien;
    @Size(max = 100)
    @Column(name = "area_asignacion_bien")
    private String areaAsignacionBien;
    @Column(name = "fecha_asignacion_bien")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacionBien;
    @Size(max = 20)
    @Column(name = "cedula_funcionario_origen")
    private String cedulaFuncionarioOrigen;
    @Size(max = 100)
    @Column(name = "nombres_funcionario_origen")
    private String nombresFuncionarioOrigen;
    @Size(max = 100)
    @Column(name = "apellidos_funcionario_origen")
    private String apellidosFuncionarioOrigen;
    @OneToMany(mappedBy = "idAsignacionBien")
    private List<DetalleAsignacionBien> detalleAsignacionBienList;
    @JoinColumn(name = "estado_asignacion_bien", referencedColumnName = "id_catalogo")
    @ManyToOne
    private Catalogo estadoAsignacionBien;
    @JoinColumn(name = "institucion_destino_asignacion_bien", referencedColumnName = "id_institucion")
    @ManyToOne
    private Institucion institucionDestinoAsignacionBien;
    @JoinColumn(name = "ubicacion_asignacion_bien", referencedColumnName = "id_ubicacion")
    @ManyToOne
    private Ubicacion ubicacionAsignacionBien;

    public AsignacionBien() {
    }

    public AsignacionBien(Integer idAsignacionBien) {
        this.idAsignacionBien = idAsignacionBien;
    }

    public Integer getIdAsignacionBien() {
        return idAsignacionBien;
    }

    public void setIdAsignacionBien(Integer idAsignacionBien) {
        this.idAsignacionBien = idAsignacionBien;
    }

    public String getCedulaFuncionarioAsignacionBien() {
        return cedulaFuncionarioAsignacionBien;
    }

    public void setCedulaFuncionarioAsignacionBien(String cedulaFuncionarioAsignacionBien) {
        this.cedulaFuncionarioAsignacionBien = cedulaFuncionarioAsignacionBien;
    }

    public String getNombresFuncionarioAsignacionBien() {
        return nombresFuncionarioAsignacionBien;
    }

    public void setNombresFuncionarioAsignacionBien(String nombresFuncionarioAsignacionBien) {
        this.nombresFuncionarioAsignacionBien = nombresFuncionarioAsignacionBien;
    }

    public String getApellidosFuncionarioAsignacionBien() {
        return apellidosFuncionarioAsignacionBien;
    }

    public void setApellidosFuncionarioAsignacionBien(String apellidosFuncionarioAsignacionBien) {
        this.apellidosFuncionarioAsignacionBien = apellidosFuncionarioAsignacionBien;
    }

    public String getAreaAsignacionBien() {
        return areaAsignacionBien;
    }

    public void setAreaAsignacionBien(String areaAsignacionBien) {
        this.areaAsignacionBien = areaAsignacionBien;
    }

    public Date getFechaAsignacionBien() {
        return fechaAsignacionBien;
    }

    public void setFechaAsignacionBien(Date fechaAsignacionBien) {
        this.fechaAsignacionBien = fechaAsignacionBien;
    }

    public String getCedulaFuncionarioOrigen() {
        return cedulaFuncionarioOrigen;
    }

    public void setCedulaFuncionarioOrigen(String cedulaFuncionarioOrigen) {
        this.cedulaFuncionarioOrigen = cedulaFuncionarioOrigen;
    }

    public String getNombresFuncionarioOrigen() {
        return nombresFuncionarioOrigen;
    }

    public void setNombresFuncionarioOrigen(String nombresFuncionarioOrigen) {
        this.nombresFuncionarioOrigen = nombresFuncionarioOrigen;
    }

    public String getApellidosFuncionarioOrigen() {
        return apellidosFuncionarioOrigen;
    }

    public void setApellidosFuncionarioOrigen(String apellidosFuncionarioOrigen) {
        this.apellidosFuncionarioOrigen = apellidosFuncionarioOrigen;
    }

    @XmlTransient
    public List<DetalleAsignacionBien> getDetalleAsignacionBienList() {
        return detalleAsignacionBienList;
    }

    public void setDetalleAsignacionBienList(List<DetalleAsignacionBien> detalleAsignacionBienList) {
        this.detalleAsignacionBienList = detalleAsignacionBienList;
    }

    public Catalogo getEstadoAsignacionBien() {
        return estadoAsignacionBien;
    }

    public void setEstadoAsignacionBien(Catalogo estadoAsignacionBien) {
        this.estadoAsignacionBien = estadoAsignacionBien;
    }

    public Institucion getInstitucionDestinoAsignacionBien() {
        return institucionDestinoAsignacionBien;
    }

    public void setInstitucionDestinoAsignacionBien(Institucion institucionDestinoAsignacionBien) {
        this.institucionDestinoAsignacionBien = institucionDestinoAsignacionBien;
    }

    public Ubicacion getUbicacionAsignacionBien() {
        return ubicacionAsignacionBien;
    }

    public void setUbicacionAsignacionBien(Ubicacion ubicacionAsignacionBien) {
        this.ubicacionAsignacionBien = ubicacionAsignacionBien;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsignacionBien != null ? idAsignacionBien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsignacionBien)) {
            return false;
        }
        AsignacionBien other = (AsignacionBien) object;
        if ((this.idAsignacionBien == null && other.idAsignacionBien != null) || (this.idAsignacionBien != null && !this.idAsignacionBien.equals(other.idAsignacionBien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Activos.Entities.AsignacionBien[ idAsignacionBien=" + idAsignacionBien + " ]";
    }
    
}
