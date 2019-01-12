/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Entities;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nelson
 */
@Entity
@Table(name = "seg_perfil", schema = "sch_seguridades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegPerfil.findAll", query = "SELECT s FROM SegPerfil s"),
    @NamedQuery(name = "SegPerfil.findByIdPerfil", query = "SELECT s FROM SegPerfil s WHERE s.idPerfil = :idPerfil"),
    @NamedQuery(name = "SegPerfil.findByNombrePerfil", query = "SELECT s FROM SegPerfil s WHERE s.nombrePerfil = :nombrePerfil"),
    @NamedQuery(name = "SegPerfil.findByEstadoPerfil", query = "SELECT s FROM SegPerfil s WHERE s.estadoPerfil = :estadoPerfil"),
    @NamedQuery(name = "SegPerfil.findByPermisosUsername", query = "SELECT s FROM SegPerfil s join s.segUsuarioPerfilList u on s=u.idPerfil  where u.idUsuario.nombreUsuario =:nombreUsuario and s.estadoPerfil=true"),
    @NamedQuery(name = "SegPerfil.findByPermisosUsuario", query = "SELECT s FROM SegPerfil s join s.segUsuarioPerfilList u on s=u.idPerfil  where u.idUsuario =:idUsuario and s.estadoPerfil=true")})

public class SegPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_perfil")
    private Integer idPerfil;
    @Size(max = 100)
    @Column(name = "nombre_perfil")
    private String nombrePerfil;
    @Column(name = "estado_perfil")
    private Boolean estadoPerfil;
    @OneToMany(mappedBy = "idPerfil")
    private List<SegUsuarioPerfil> segUsuarioPerfilList;
    @OneToMany(mappedBy = "idPerfil")
    private List<SegAccionMenuPerfil> segAccionMenuPerfilList;

    public SegPerfil() {
    }

    public SegPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Boolean getEstadoPerfil() {
        return estadoPerfil;
    }

    public void setEstadoPerfil(Boolean estadoPerfil) {
        this.estadoPerfil = estadoPerfil;
    }

    @XmlTransient
    public List<SegUsuarioPerfil> getSegUsuarioPerfilList() {
        return segUsuarioPerfilList;
    }

    public void setSegUsuarioPerfilList(List<SegUsuarioPerfil> segUsuarioPerfilList) {
        this.segUsuarioPerfilList = segUsuarioPerfilList;
    }

    @XmlTransient
    public List<SegAccionMenuPerfil> getSegAccionMenuPerfilList() {
        return segAccionMenuPerfilList;
    }

    public void setSegAccionMenuPerfilList(List<SegAccionMenuPerfil> segAccionMenuPerfilList) {
        this.segAccionMenuPerfilList = segAccionMenuPerfilList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPerfil)) {
            return false;
        }
        SegPerfil other = (SegPerfil) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombrePerfil();
    }

}
