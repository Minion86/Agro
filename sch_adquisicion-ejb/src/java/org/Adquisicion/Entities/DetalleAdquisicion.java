/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Entities;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.Adquisicion.Entities.Adquisicion;
import org.Adquisicion.Entities.Catalogo;
import org.Adquisicion.Entities.Producto;

/**
 *
 * @author fmullo
 */
@Entity
@Table(name = "detalle_adquisicion", schema = "sch_adquisiciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleAdquisicion.findAll", query = "SELECT d FROM DetalleAdquisicion d")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByIdDetalleAdquisicion", query = "SELECT d FROM DetalleAdquisicion d WHERE d.idDetalleAdquisicion = :idDetalleAdquisicion")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByCantidadDetalleAdquisicion", query = "SELECT d FROM DetalleAdquisicion d WHERE d.cantidadDetalleAdquisicion = :cantidadDetalleAdquisicion")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByValorDetalleAdquisicion", query = "SELECT d FROM DetalleAdquisicion d WHERE d.valorDetalleAdquisicion = :valorDetalleAdquisicion")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByValorLibrosDetalleAdquisicion", query = "SELECT d FROM DetalleAdquisicion d WHERE d.valorLibrosDetalleAdquisicion = :valorLibrosDetalleAdquisicion")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByCodigoProducto", query = "SELECT d FROM DetalleAdquisicion d WHERE d.codigoProducto = :codigoProducto")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByCodigoAnteriorProducto", query = "SELECT d FROM DetalleAdquisicion d WHERE d.codigoAnteriorProducto = :codigoAnteriorProducto")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByEstadoDetalle", query = "SELECT d FROM DetalleAdquisicion d WHERE d.estadoDetalle = :estadoDetalle")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByColorProducto", query = "SELECT d FROM DetalleAdquisicion d WHERE d.colorProducto = :colorProducto")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByNumeroSerieProducto", query = "SELECT d FROM DetalleAdquisicion d WHERE d.numeroSerieProducto = :numeroSerieProducto")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByObservaciones", query = "SELECT d FROM DetalleAdquisicion d WHERE d.observaciones = :observaciones")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByCantidadBodegaDetalleAdquisicion", query = "SELECT d FROM DetalleAdquisicion d WHERE d.cantidadBodegaDetalleAdquisicion = :cantidadBodegaDetalleAdquisicion")})
public class DetalleAdquisicion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_adquisicion")
    private Integer idDetalleAdquisicion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad_detalle_adquisicion")
    private Double cantidadDetalleAdquisicion;
    @Column(name = "valor_detalle_adquisicion")
    private Double valorDetalleAdquisicion;
    @Column(name = "valor_libros_detalle_adquisicion")
    private Double valorLibrosDetalleAdquisicion;
    @Size(max = 100)
    @Column(name = "codigo_producto")
    private String codigoProducto;
    @Size(max = 100)
    @Column(name = "codigo_anterior_producto")
    private String codigoAnteriorProducto;
    @Column(name = "estado_detalle")
    private Boolean estadoDetalle;
    @Size(max = 100)
    @Column(name = "color_producto")
    private String colorProducto;
    @Size(max = 200)
    @Column(name = "numero_serie_producto")
    private String numeroSerieProducto;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "cantidad_bodega_detalle_adquisicion")
    private Double cantidadBodegaDetalleAdquisicion;
    @OneToMany(mappedBy = "idDetalleAdquisicion")
    private Adquisicion idAdquisicion;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;
    @JoinColumn(name = "tipo_producto", referencedColumnName = "id_catalogo")
    @ManyToOne
    private Catalogo tipoProducto;
    @JoinColumn(name = "estado_producto", referencedColumnName = "id_catalogo")
    @ManyToOne
    private Catalogo estadoProducto;
    @Transient
    private Integer valorProductoDesde;
    @Transient
    private Integer valorProductoHasta;
    @Transient
    private Object codigoBarras;
    @Transient
    private String nombreProducto;
    @Column(name = "codigo_barras")
    private BigInteger codigoBarra;
    @Transient
    private String claseproducto;

    public DetalleAdquisicion() {
    }

    public DetalleAdquisicion(Integer idDetalleAdquisicion) {
        this.idDetalleAdquisicion = idDetalleAdquisicion;
    }

    public Integer getIdDetalleAdquisicion() {
        return idDetalleAdquisicion;
    }

    public void setIdDetalleAdquisicion(Integer idDetalleAdquisicion) {
        this.idDetalleAdquisicion = idDetalleAdquisicion;
    }

    public Double getCantidadDetalleAdquisicion() {
        return cantidadDetalleAdquisicion;
    }

    public void setCantidadDetalleAdquisicion(Double cantidadDetalleAdquisicion) {
        this.cantidadDetalleAdquisicion = cantidadDetalleAdquisicion;
    }

    public Double getValorDetalleAdquisicion() {
        return valorDetalleAdquisicion;
    }

    public void setValorDetalleAdquisicion(Double valorDetalleAdquisicion) {
        this.valorDetalleAdquisicion = valorDetalleAdquisicion;
    }

    public Double getValorLibrosDetalleAdquisicion() {
        return valorLibrosDetalleAdquisicion;
    }

    public void setValorLibrosDetalleAdquisicion(Double valorLibrosDetalleAdquisicion) {
        this.valorLibrosDetalleAdquisicion = valorLibrosDetalleAdquisicion;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoAnteriorProducto() {
        return codigoAnteriorProducto;
    }

    public void setCodigoAnteriorProducto(String codigoAnteriorProducto) {
        this.codigoAnteriorProducto = codigoAnteriorProducto;
    }

    public Boolean getEstadoDetalle() {
        return estadoDetalle;
    }

    public void setEstadoDetalle(Boolean estadoDetalle) {
        this.estadoDetalle = estadoDetalle;
    }

    public String getColorProducto() {
        return colorProducto;
    }

    public void setColorProducto(String colorProducto) {
        this.colorProducto = colorProducto;
    }

    public String getNumeroSerieProducto() {
        return numeroSerieProducto;
    }

    public void setNumeroSerieProducto(String numeroSerieProducto) {
        this.numeroSerieProducto = numeroSerieProducto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getCantidadBodegaDetalleAdquisicion() {
        return cantidadBodegaDetalleAdquisicion;
    }

    public void setCantidadBodegaDetalleAdquisicion(Double cantidadBodegaDetalleAdquisicion) {
        this.cantidadBodegaDetalleAdquisicion = cantidadBodegaDetalleAdquisicion;
    }

    public Adquisicion getIdAdquisicion() {
        return idAdquisicion;
    }

    public void setIdAdquisicion(Adquisicion idAdquisicion) {
        this.idAdquisicion = idAdquisicion;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Catalogo getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(Catalogo tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Catalogo getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(Catalogo estadoProducto) {
        this.estadoProducto = estadoProducto;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleAdquisicion != null ? idDetalleAdquisicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleAdquisicion)) {
            return false;
        }
        DetalleAdquisicion other = (DetalleAdquisicion) object;
        if ((this.idDetalleAdquisicion == null && other.idDetalleAdquisicion != null) || (this.idDetalleAdquisicion != null && !this.idDetalleAdquisicion.equals(other.idDetalleAdquisicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (getIdProducto() != null) {
            return getIdProducto().getNombreProducto() + " - Color:" + getColorProducto() + " Estado:" + getEstadoProducto().getNombreCatalogo() + " Valor:$" + getValorDetalleAdquisicion();
        }  else {
            return "";
        }
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

    /**
     * @return the codigoBarras
     */
    public Object getCodigoBarras() {
        return codigoBarras;
    }

    /**
     * @param codigoBarras the codigoBarras to set
     */
    public void setCodigoBarras(Object codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    /**
     * @return the nombreProducto
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * @param nombreProducto the nombreProducto to set
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * @return the codigoBarra
     */
    public BigInteger getCodigoBarra() {
        return codigoBarra;
    }

    /**
     * @param codigoBarra the codigoBarra to set
     */
    public void setCodigoBarra(BigInteger codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    /**
     * @return the claseproducto
     */
    public String getClaseproducto() {
        return claseproducto;
    }

    /**
     * @param claseproducto the claseproducto to set
     */
    public void setClaseproducto(String claseproducto) {
        this.claseproducto = claseproducto;
    }

}
