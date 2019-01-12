/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nelson
 */
@Entity
@Table(name = "seg_usuario_perfil",schema = "sch_seguridades")
@NamedQueries({
    @NamedQuery(name = "SegUsuarioPerfil.findAll", query = "SELECT s FROM SegUsuarioPerfil s"),
    @NamedQuery(name = "SegUsuarioPerfil.findByIdUsuarioPerfil", query = "SELECT s FROM SegUsuarioPerfil s WHERE s.idUsuarioPerfil = :idUsuarioPerfil")})
public class SegUsuarioPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario_perfil")
    private Integer idUsuarioPerfil;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil")
    @ManyToOne
    private SegPerfil idPerfil;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(cascade = CascadeType.ALL)
    private SegUsuario idUsuario;

    public SegUsuarioPerfil() {
    }

    public SegUsuarioPerfil(Integer idUsuarioPerfil) {
        this.idUsuarioPerfil = idUsuarioPerfil;
    }

    public Integer getIdUsuarioPerfil() {
        return idUsuarioPerfil;
    }

    public void setIdUsuarioPerfil(Integer idUsuarioPerfil) {
        this.idUsuarioPerfil = idUsuarioPerfil;
    }

    public SegPerfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(SegPerfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    public SegUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(SegUsuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioPerfil != null ? idUsuarioPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUsuarioPerfil)) {
            return false;
        }
        SegUsuarioPerfil other = (SegUsuarioPerfil) object;
        if ((this.idUsuarioPerfil == null && other.idUsuarioPerfil != null) || (this.idUsuarioPerfil != null && !this.idUsuarioPerfil.equals(other.idUsuarioPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Seguridades.SegUsuarioPerfil[ idUsuarioPerfil=" + idUsuarioPerfil + " ]";
    }
    
}
