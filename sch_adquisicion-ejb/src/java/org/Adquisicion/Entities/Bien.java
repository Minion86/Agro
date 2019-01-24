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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "bien", schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "Bien.findAll", query = "SELECT b FROM Bien b"),
    @NamedQuery(name = "Bien.findByIdBien", query = "SELECT b FROM Bien b WHERE b.idBien = :idBien"),
    @NamedQuery(name = "Bien.findByDescripcionBien", query = "SELECT b FROM Bien b WHERE b.descripcionBien = :descripcionBien"),
    @NamedQuery(name = "Bien.findByValorBien", query = "SELECT b FROM Bien b WHERE b.valorBien = :valorBien"),
    @NamedQuery(name = "Bien.findByCodigoBien", query = "SELECT b FROM Bien b WHERE b.codigoBien = :codigoBien"),
    @NamedQuery(name = "Bien.findByMarcaBien", query = "SELECT b FROM Bien b WHERE b.marcaBien = :marcaBien"),
    @NamedQuery(name = "Bien.findByIdUsuarioIngresa", query = "SELECT b FROM Bien b WHERE b.idUsuarioIngresa = :idUsuarioIngresa"),
    @NamedQuery(name = "Bien.findBySerieBien", query = "SELECT b FROM Bien b WHERE b.serieBien = :serieBien"),
    @NamedQuery(name = "Bien.findByMedidaBien", query = "SELECT b FROM Bien b WHERE b.medidaBien = :medidaBien"),
    @NamedQuery(name = "Bien.findByPesoBien", query = "SELECT b FROM Bien b WHERE b.pesoBien = :pesoBien"),
    @NamedQuery(name = "Bien.findByVidaUtilBien", query = "SELECT b FROM Bien b WHERE b.vidaUtilBien = :vidaUtilBien"),
    @NamedQuery(name = "Bien.findByEstadoBien", query = "SELECT b FROM Bien b WHERE b.estadoBien = :estadoBien"),
    @NamedQuery(name = "Bien.findByNombreBien", query = "SELECT b FROM Bien b WHERE b.nombreBien = :nombreBien")})
public class Bien implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_bien")
    private Integer idBien;
    @Size(max = 2147483647)
    @Column(name = "descripcion_bien")
    private String descripcionBien;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_bien")
    private Double valorBien;
    @Basic(optional = false)
    @NotNull
    @Size( max = 200)
    @Column(name = "codigo_bien")
    private String codigoBien;
    @Size(max = 100)
    @Column(name = "marca_bien")
    private String marcaBien;
    @Column(name = "id_usuario_ingresa")
    private Integer idUsuarioIngresa;
    @Size(max = 100)
    @Column(name = "serie_bien")
    private String serieBien;
    @Size(max = 100)
    @Column(name = "medida_bien")
    private String medidaBien;
    @Size(max = 100)
    @Column(name = "peso_bien")
    private String pesoBien;
    @Column(name = "vida_util_bien")
    private Integer vidaUtilBien;
    @Column(name = "estado_bien")
    private Boolean estadoBien;
    @Size(max = 100)
    @Column(name = "nombre_bien")
    private String nombreBien;
    @OneToMany(mappedBy = "idBien")
    private List<DetalleAdquisicion> detalleAdquisicionList;
    @Transient
    private Integer vidaUtilBienDesde;
    @Transient
    private Integer vidaUtilBienHasta;
    @Transient
    private Integer valorBienDesde;
    @Transient
    private Integer valorBienHasta;

    public Bien() {
    }

    public Bien(Integer idBien) {
        this.idBien = idBien;
    }

    public Bien(Integer idBien, String codigoBien) {
        this.idBien = idBien;
        this.codigoBien = codigoBien;
    }

    public Integer getIdBien() {
        return idBien;
    }

    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
    }

    public String getDescripcionBien() {
        return descripcionBien;
    }

    public void setDescripcionBien(String descripcionBien) {
        this.descripcionBien = descripcionBien;
    }

    public Double getValorBien() {
        return valorBien;
    }

    public void setValorBien(Double valorBien) {
        this.valorBien = valorBien;
    }

    public String getCodigoBien() {
        return codigoBien;
    }

    public void setCodigoBien(String codigoBien) {
        this.codigoBien = codigoBien;
    }

    public String getMarcaBien() {
        return marcaBien;
    }

    public void setMarcaBien(String marcaBien) {
        this.marcaBien = marcaBien;
    }

    public Integer getIdUsuarioIngresa() {
        return idUsuarioIngresa;
    }

    public void setIdUsuarioIngresa(Integer idUsuarioIngresa) {
        this.idUsuarioIngresa = idUsuarioIngresa;
    }

    public String getSerieBien() {
        return serieBien;
    }

    public void setSerieBien(String serieBien) {
        this.serieBien = serieBien;
    }

    public String getMedidaBien() {
        return medidaBien;
    }

    public void setMedidaBien(String medidaBien) {
        this.medidaBien = medidaBien;
    }

    public String getPesoBien() {
        return pesoBien;
    }

    public void setPesoBien(String pesoBien) {
        this.pesoBien = pesoBien;
    }

    public Integer getVidaUtilBien() {
        return vidaUtilBien;
    }

    public void setVidaUtilBien(Integer vidaUtilBien) {
        this.vidaUtilBien = vidaUtilBien;
    }

    public Boolean getEstadoBien() {
        return estadoBien;
    }

    public void setEstadoBien(Boolean estadoBien) {
        this.estadoBien = estadoBien;
    }

    public String getNombreBien() {
        return nombreBien;
    }

    public void setNombreBien(String nombreBien) {
        this.nombreBien = nombreBien;
    }

    @XmlTransient
    public List<DetalleAdquisicion> getDetalleAdquisicionList() {
        return detalleAdquisicionList;
    }

    public void setDetalleAdquisicionList(List<DetalleAdquisicion> detalleAdquisicionList) {
        this.detalleAdquisicionList = detalleAdquisicionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBien != null ? idBien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bien)) {
            return false;
        }
        Bien other = (Bien) object;
        if ((this.idBien == null && other.idBien != null) || (this.idBien != null && !this.idBien.equals(other.idBien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombreBien();
    }

    /**
     * @return the vidaUtilBienDesde
     */
    public Integer getVidaUtilBienDesde() {
        return vidaUtilBienDesde;
    }

    /**
     * @param vidaUtilBienDesde the vidaUtilBienDesde to set
     */
    public void setVidaUtilBienDesde(Integer vidaUtilBienDesde) {
        this.vidaUtilBienDesde = vidaUtilBienDesde;
    }

    /**
     * @return the vidaUtilBienHasta
     */
    public Integer getVidaUtilBienHasta() {
        return vidaUtilBienHasta;
    }

    /**
     * @param vidaUtilBienHasta the vidaUtilBienHasta to set
     */
    public void setVidaUtilBienHasta(Integer vidaUtilBienHasta) {
        this.vidaUtilBienHasta = vidaUtilBienHasta;
    }

    /**
     * @return the valorBienDesde
     */
    public Integer getValorBienDesde() {
        return valorBienDesde;
    }

    /**
     * @param valorBienDesde the valorBienDesde to set
     */
    public void setValorBienDesde(Integer valorBienDesde) {
        this.valorBienDesde = valorBienDesde;
    }

    /**
     * @return the valorBienHasta
     */
    public Integer getValorBienHasta() {
        return valorBienHasta;
    }

    /**
     * @param valorBienHasta the valorBienHasta to set
     */
    public void setValorBienHasta(Integer valorBienHasta) {
        this.valorBienHasta = valorBienHasta;
    }

}
