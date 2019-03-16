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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nmartinez
 */
@Entity
@Table(name = "grupocatalogo",schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "Grupocatalogo.findAll", query = "SELECT g FROM Grupocatalogo g"),
    @NamedQuery(name = "Grupocatalogo.findByIdGrupocatalogo", query = "SELECT g FROM Grupocatalogo g WHERE g.idGrupocatalogo = :idGrupocatalogo"),
    @NamedQuery(name = "Grupocatalogo.findByNombreGrupocatalogo", query = "SELECT g FROM Grupocatalogo g WHERE g.nombreGrupocatalogo = :nombreGrupocatalogo"),
    @NamedQuery(name = "Grupocatalogo.findByNemonicoGrupocatalogo", query = "SELECT g FROM Grupocatalogo g WHERE g.nemonicoGrupocatalogo = :nemonicoGrupocatalogo"),
    @NamedQuery(name = "Grupocatalogo.findByValorGrupocatalogo", query = "SELECT g FROM Grupocatalogo g WHERE g.valorGrupocatalogo = :valorGrupocatalogo")})
public class Grupocatalogo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_grupocatalogo")
    private Integer idGrupocatalogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "nombre_grupocatalogo")
    private String nombreGrupocatalogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nemonico_grupocatalogo")
    private String nemonicoGrupocatalogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "valor_grupocatalogo")
    private String valorGrupocatalogo;
    @OneToMany(mappedBy = "idGrupocatalogo")
    private List<Catalogo> catalogoList;

    public Grupocatalogo() {
    }

    public Grupocatalogo(Integer idGrupocatalogo) {
        this.idGrupocatalogo = idGrupocatalogo;
    }

    public Grupocatalogo(Integer idGrupocatalogo, String nombreGrupocatalogo, String nemonicoGrupocatalogo, String valorGrupocatalogo) {
        this.idGrupocatalogo = idGrupocatalogo;
        this.nombreGrupocatalogo = nombreGrupocatalogo;
        this.nemonicoGrupocatalogo = nemonicoGrupocatalogo;
        this.valorGrupocatalogo = valorGrupocatalogo;
    }

    public Integer getIdGrupocatalogo() {
        return idGrupocatalogo;
    }

    public void setIdGrupocatalogo(Integer idGrupocatalogo) {
        this.idGrupocatalogo = idGrupocatalogo;
    }

    public String getNombreGrupocatalogo() {
        return nombreGrupocatalogo;
    }

    public void setNombreGrupocatalogo(String nombreGrupocatalogo) {
        this.nombreGrupocatalogo = nombreGrupocatalogo;
    }

    public String getNemonicoGrupocatalogo() {
        return nemonicoGrupocatalogo;
    }

    public void setNemonicoGrupocatalogo(String nemonicoGrupocatalogo) {
        this.nemonicoGrupocatalogo = nemonicoGrupocatalogo;
    }

    public String getValorGrupocatalogo() {
        return valorGrupocatalogo;
    }

    public void setValorGrupocatalogo(String valorGrupocatalogo) {
        this.valorGrupocatalogo = valorGrupocatalogo;
    }

    @XmlTransient
    public List<Catalogo> getCatalogoList() {
        return catalogoList;
    }

    public void setCatalogoList(List<Catalogo> catalogoList) {
        this.catalogoList = catalogoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupocatalogo != null ? idGrupocatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupocatalogo)) {
            return false;
        }
        Grupocatalogo other = (Grupocatalogo) object;
        if ((this.idGrupocatalogo == null && other.idGrupocatalogo != null) || (this.idGrupocatalogo != null && !this.idGrupocatalogo.equals(other.idGrupocatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Activos.Entities.Grupocatalogo[ idGrupocatalogo=" + idGrupocatalogo + " ]";
    }
    
}
