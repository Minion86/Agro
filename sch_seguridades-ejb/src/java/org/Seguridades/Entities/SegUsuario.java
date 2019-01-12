/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nelson
 */
@Entity
@Table(name = "seg_usuario", schema = "sch_seguridades")
@NamedQueries({
    @NamedQuery(name = "SegUsuario.findAll", query = "SELECT s FROM SegUsuario s"),
    @NamedQuery(name = "SegUsuario.findByIdUsuario", query = "SELECT s FROM SegUsuario s WHERE s.idUsuario = :idUsuario"),
    @NamedQuery(name = "SegUsuario.findByUsernameUsuario", query = "SELECT s FROM SegUsuario s WHERE s.usernameUsuario = :usernameUsuario"),
    @NamedQuery(name = "SegUsuario.findByPasswordUsuario", query = "SELECT s FROM SegUsuario s WHERE s.passwordUsuario = :passwordUsuario and s.usernameUsuario= :usernameUsuario and s.activoUsuario=true"),
    @NamedQuery(name = "SegUsuario.findByActivoUsuario", query = "SELECT s FROM SegUsuario s WHERE s.activoUsuario = :activoUsuario"),
    @NamedQuery(name = "SegUsuario.findByFechaCreacionUsuario", query = "SELECT s FROM SegUsuario s WHERE s.fechaCreacionUsuario = :fechaCreacionUsuario"),
    @NamedQuery(name = "SegUsuario.findByUsuarioModificacionUsuario", query = "SELECT s FROM SegUsuario s WHERE s.usuarioModificacionUsuario = :usuarioModificacionUsuario"),
    @NamedQuery(name = "SegUsuario.findByFechaModificacionUsuario", query = "SELECT s FROM SegUsuario s WHERE s.fechaModificacionUsuario = :fechaModificacionUsuario")})
public class SegUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size( max = 100)
    @Column(name = "username_usuario")
    private String usernameUsuario;
    @Basic(optional = false)
    @NotNull
    @Size( max = 500)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Size( max = 300)
    @Column(name = "apellido_usuario")
    private String apellidoUsuario;
    @Size( max = 20)
    @Column(name = "identificacion_usuario")
    private String identificacionUsuario;
    @Size( max = 200)
    @Column(name = "email_usuario")
    private String emailUsuario;
    @Basic(optional = false)
    @NotNull
    @Size( max = 50)
    @Column(name = "password_usuario")
    private String passwordUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo_usuario")
    private boolean activoUsuario;
    @Column(name = "fecha_creacion_usuario")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacionUsuario;
    @Column(name = "usuario_modificacion_usuario")
    private Integer usuarioModificacionUsuario;
    @Column(name = "fecha_modificacion_usuario")
    @Temporal(TemporalType.DATE)
    private Date fechaModificacionUsuario;
    @OneToMany(mappedBy = "idUsuario",cascade = CascadeType.ALL)
    private List<SegUsuarioPerfil> segUsuarioPerfilList;

    public SegUsuario() {
    }

    public SegUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public SegUsuario(Integer idUsuario, String nombreUsuario, String passwordUsuario, boolean activoUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.passwordUsuario = passwordUsuario;
        this.activoUsuario = activoUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    public boolean getActivoUsuario() {
        return activoUsuario;
    }

    public void setActivoUsuario(boolean activoUsuario) {
        this.activoUsuario = activoUsuario;
    }

    public Date getFechaCreacionUsuario() {
        return fechaCreacionUsuario;
    }

    public void setFechaCreacionUsuario(Date fechaCreacionUsuario) {
        this.fechaCreacionUsuario = fechaCreacionUsuario;
    }

    public Integer getUsuarioModificacionUsuario() {
        return usuarioModificacionUsuario;
    }

    public void setUsuarioModificacionUsuario(Integer usuarioModificacionUsuario) {
        this.usuarioModificacionUsuario = usuarioModificacionUsuario;
    }

    public Date getFechaModificacionUsuario() {
        return fechaModificacionUsuario;
    }

    public void setFechaModificacionUsuario(Date fechaModificacionUsuario) {
        this.fechaModificacionUsuario = fechaModificacionUsuario;
    }

    @XmlTransient
    public List<SegUsuarioPerfil> getSegUsuarioPerfilList() {
        return segUsuarioPerfilList;
    }

    public void setSegUsuarioPerfilList(List<SegUsuarioPerfil> segUsuarioPerfilList) {
        this.segUsuarioPerfilList = segUsuarioPerfilList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUsuario)) {
            return false;
        }
        SegUsuario other = (SegUsuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Seguridades.SegUsuario[ idUsuario=" + idUsuario + " ]";
    }

    /**
     * @return the usernameUsuario
     */
    public String getUsernameUsuario() {
        return usernameUsuario;
    }

    /**
     * @param usernameUsuario the usernameUsuario to set
     */
    public void setUsernameUsuario(String usernameUsuario) {
        this.usernameUsuario = usernameUsuario;
    }

    /**
     * @return the apellidoUsuario
     */
    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    /**
     * @param apellidoUsuario the apellidoUsuario to set
     */
    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    /**
     * @return the identificacionUsuario
     */
    public String getIdentificacionUsuario() {
        return identificacionUsuario;
    }

    /**
     * @param identificacionUsuario the identificacionUsuario to set
     */
    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    /**
     * @return the emailUsuario
     */
    public String getEmailUsuario() {
        return emailUsuario;
    }

    /**
     * @param emailUsuario the emailUsuario to set
     */
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

}
