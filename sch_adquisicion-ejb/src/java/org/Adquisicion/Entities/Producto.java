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
@Table(name = "producto", schema = "sch_adquisicion")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT b FROM Producto b")
    ,
    @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT b FROM Producto b WHERE b.idProducto = :idProducto")
    ,
    @NamedQuery(name = "Producto.findByDescripcionProducto", query = "SELECT b FROM Producto b WHERE b.descripcionProducto = :descripcionProducto")
    ,
    @NamedQuery(name = "Producto.findByValorProducto", query = "SELECT b FROM Producto b WHERE b.valorProducto = :valorProducto")
    ,
    @NamedQuery(name = "Producto.findByCodigoProducto", query = "SELECT b FROM Producto b WHERE b.codigoProducto = :codigoProducto")
    ,
    @NamedQuery(name = "Producto.findByMarcaProducto", query = "SELECT b FROM Producto b WHERE b.marcaProducto = :marcaProducto")
    ,
    @NamedQuery(name = "Producto.findByIdUsuarioIngresa", query = "SELECT b FROM Producto b WHERE b.idUsuarioIngresa = :idUsuarioIngresa")
    ,
    @NamedQuery(name = "Producto.findBySerieProducto", query = "SELECT b FROM Producto b WHERE b.serieProducto = :serieProducto")
    ,
    @NamedQuery(name = "Producto.findByMedidaProducto", query = "SELECT b FROM Producto b WHERE b.medidaProducto = :medidaProducto")
    ,
    @NamedQuery(name = "Producto.findByPesoProducto", query = "SELECT b FROM Producto b WHERE b.pesoProducto = :pesoProducto")
    ,
    @NamedQuery(name = "Producto.findByVidaUtilProducto", query = "SELECT b FROM Producto b WHERE b.vidaUtilProducto = :vidaUtilProducto")
    ,
    @NamedQuery(name = "Producto.findByEstadoProducto", query = "SELECT b FROM Producto b WHERE b.estadoProducto = :estadoProducto")
    ,
    @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT b FROM Producto b WHERE b.nombreProducto = :nombreProducto")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_producto")
    private Integer idProducto;
    @Size(max = 2147483647)
    @Column(name = "descripcion_producto")
    private String descripcionProducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_producto")
    private Double valorProducto;
    @Basic(optional = false)
    @NotNull
    @Size(max = 200)
    @Column(name = "codigo_producto")
    private String codigoProducto;

    @Column(name = "id_usuario_ingresa")
    private Integer idUsuarioIngresa;
    @Size(max = 100)
    @Column(name = "medida_producto")
    private String medidaProducto;
    @Size(max = 100)
    @Column(name = "peso_producto")
    private String pesoProducto;
    @Column(name = "estado_producto")
    private Boolean estadoProducto;
    @Size(max = 100)
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @OneToMany(mappedBy = "idProducto")
    private List<DetalleAdquisicion> detalleAdquisicionList;
    @Transient
    private Integer valorProductoDesde;
    @Transient
    private Integer valorProductoHasta;

    public Producto() {
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(Integer idProducto, String codigoProducto) {
        this.idProducto = idProducto;
        this.codigoProducto = codigoProducto;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Double getValorProducto() {
        return valorProducto;
    }

    public void setValorProducto(Double valorProducto) {
        this.valorProducto = valorProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Integer getIdUsuarioIngresa() {
        return idUsuarioIngresa;
    }

    public void setIdUsuarioIngresa(Integer idUsuarioIngresa) {
        this.idUsuarioIngresa = idUsuarioIngresa;
    }

    public String getMedidaProducto() {
        return medidaProducto;
    }

    public void setMedidaProducto(String medidaProducto) {
        this.medidaProducto = medidaProducto;
    }

    public String getPesoProducto() {
        return pesoProducto;
    }

    public void setPesoProducto(String pesoProducto) {
        this.pesoProducto = pesoProducto;
    }

    public Boolean getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(Boolean estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
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
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombreProducto();
    }

    /**
     * @return the valorProductoDesde
     */
    public Integer getValorProductoDesde() {
        return valorProductoDesde;
    }

    /**
     * @param valorProductoDesde the valorProductoDesde to set
     */
    public void setValorProductoDesde(Integer valorProductoDesde) {
        this.valorProductoDesde = valorProductoDesde;
    }

    /**
     * @return the valorProductoHasta
     */
    public Integer getValorProductoHasta() {
        return valorProductoHasta;
    }

    /**
     * @param valorProductoHasta the valorProductoHasta to set
     */
    public void setValorProductoHasta(Integer valorProductoHasta) {
        this.valorProductoHasta = valorProductoHasta;
    }

}
