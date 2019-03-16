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
 * @author nmartinez
 */
@Entity
@Table(name = "catalogo",schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "Catalogo.findAll", query = "SELECT c FROM Catalogo c"),
    @NamedQuery(name = "Catalogo.findByIdCatalogo", query = "SELECT c FROM Catalogo c WHERE c.idCatalogo = :idCatalogo"),
    @NamedQuery(name = "Catalogo.findByNombreCatalogo", query = "SELECT c FROM Catalogo c WHERE c.nombreCatalogo = :nombreCatalogo"),
    @NamedQuery(name = "Catalogo.findByNemonicoCatalogo", query = "SELECT c FROM Catalogo c WHERE c.nemonicoCatalogo = :nemonicoCatalogo"),
    @NamedQuery(name = "Catalogo.findByValorCatalogo", query = "SELECT c FROM Catalogo c WHERE c.valorCatalogo = :valorCatalogo")})
public class Catalogo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_catalogo")
    private Integer idCatalogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_catalogo")
    private String nombreCatalogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nemonico_catalogo")
    private String nemonicoCatalogo;
    @Size(max = 300)
    @Column(name = "valor_catalogo")
    private String valorCatalogo;
    @OneToMany(mappedBy = "estadoBien")
    private List<DetalleAdquisicion> detalleAdquisicionList;
    @JoinColumn(name = "id_grupocatalogo", referencedColumnName = "id_grupocatalogo")
    @ManyToOne
    private Grupocatalogo idGrupocatalogo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoBodega")
    private List<Bodega> bodegaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "claseBodega")
    private List<Bodega> bodegaList1;

    public Catalogo() {
    }

    public Catalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public Catalogo(Integer idCatalogo, String nombreCatalogo, String nemonicoCatalogo) {
        this.idCatalogo = idCatalogo;
        this.nombreCatalogo = nombreCatalogo;
        this.nemonicoCatalogo = nemonicoCatalogo;
    }

    public Integer getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getNombreCatalogo() {
        return nombreCatalogo;
    }

    public void setNombreCatalogo(String nombreCatalogo) {
        this.nombreCatalogo = nombreCatalogo;
    }

    public String getNemonicoCatalogo() {
        return nemonicoCatalogo;
    }

    public void setNemonicoCatalogo(String nemonicoCatalogo) {
        this.nemonicoCatalogo = nemonicoCatalogo;
    }

    public String getValorCatalogo() {
        return valorCatalogo;
    }

    public void setValorCatalogo(String valorCatalogo) {
        this.valorCatalogo = valorCatalogo;
    }

    @XmlTransient
    public List<DetalleAdquisicion> getDetalleAdquisicionList() {
        return detalleAdquisicionList;
    }

    public void setDetalleAdquisicionList(List<DetalleAdquisicion> detalleAdquisicionList) {
        this.detalleAdquisicionList = detalleAdquisicionList;
    }

    public Grupocatalogo getIdGrupocatalogo() {
        return idGrupocatalogo;
    }

    public void setIdGrupocatalogo(Grupocatalogo idGrupocatalogo) {
        this.idGrupocatalogo = idGrupocatalogo;
    }

    @XmlTransient
    public List<Bodega> getBodegaList() {
        return bodegaList;
    }

    public void setBodegaList(List<Bodega> bodegaList) {
        this.bodegaList = bodegaList;
    }

    @XmlTransient
    public List<Bodega> getBodegaList1() {
        return bodegaList1;
    }

    public void setBodegaList1(List<Bodega> bodegaList1) {
        this.bodegaList1 = bodegaList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatalogo != null ? idCatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catalogo)) {
            return false;
        }
        Catalogo other = (Catalogo) object;
        if ((this.idCatalogo == null && other.idCatalogo != null) || (this.idCatalogo != null && !this.idCatalogo.equals(other.idCatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getValorCatalogo();
    }
    
}
