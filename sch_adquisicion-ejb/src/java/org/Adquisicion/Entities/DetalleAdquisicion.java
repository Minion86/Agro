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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nmartinez
 */
@Entity
@Table(name = "detalle_adquisicion", schema = "sch_adquisicion")
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
    @NamedQuery(name = "DetalleAdquisicion.findByCodigoBien", query = "SELECT d FROM DetalleAdquisicion d WHERE d.codigoBien = :codigoBien")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByCodigoAnteriorBien", query = "SELECT d FROM DetalleAdquisicion d WHERE d.codigoAnteriorBien = :codigoAnteriorBien")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByEstadoDetalle", query = "SELECT d FROM DetalleAdquisicion d WHERE d.estadoDetalle = :estadoDetalle")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByColorBien", query = "SELECT d FROM DetalleAdquisicion d WHERE d.colorBien = :colorBien")
    ,
    @NamedQuery(name = "DetalleAdquisicion.findByNumeroSerieBien", query = "SELECT d FROM DetalleAdquisicion d WHERE d.numeroSerieBien = :numeroSerieBien")
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
    @Column(name = "codigo_bien")
    private String codigoBien;
    @Size(max = 100)
    @Column(name = "codigo_anterior_bien")
    private String codigoAnteriorBien;
    @Column(name = "estado_detalle")
    private Boolean estadoDetalle;
    @Size(max = 100)
    @Column(name = "color_bien")
    private String colorBien;
    @Size(max = 200)
    @Column(name = "numero_serie_bien")
    private String numeroSerieBien;
    @Column(name = "tipo_cantidad")
    private String tipoCantidad;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "cantidad_bodega_detalle_adquisicion")
    private Double cantidadBodegaDetalleAdquisicion;
    @JoinColumn(name = "id_adquisicion", referencedColumnName = "id_adquisicion")
    @ManyToOne
    private Adquisicion idAdquisicion;
    @JoinColumn(name = "id_bien", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idBien;
    @JoinColumn(name = "tipo_bien", referencedColumnName = "id_catalogo")
    @ManyToOne
    private Catalogo tipoBien;
    @JoinColumn(name = "estado_bien", referencedColumnName = "id_catalogo")
    @ManyToOne
    private Catalogo estadoBien;
    @Transient
    private Integer valorBienDesde;
    @Transient
    private Integer valorBienHasta;
    @Transient
    private Object codigoBarras;
    @Transient
    private String nombreBien;
    @Column(name = "codigo_barras")
    private BigInteger codigoBarra;
    @Transient
    private String clasebien;
    @Transient
    private Integer idProductoint;
    @Transient
    private Integer tipoBienInt;
    @Transient
    private Integer estadoBienInt;

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

    public String getCodigoBien() {
        return codigoBien;
    }

    public void setCodigoBien(String codigoBien) {
        this.codigoBien = codigoBien;
    }

    public String getCodigoAnteriorBien() {
        return codigoAnteriorBien;
    }

    public void setCodigoAnteriorBien(String codigoAnteriorBien) {
        this.codigoAnteriorBien = codigoAnteriorBien;
    }

    public Boolean getEstadoDetalle() {
        return estadoDetalle;
    }

    public void setEstadoDetalle(Boolean estadoDetalle) {
        this.estadoDetalle = estadoDetalle;
    }

    public String getColorBien() {
        return colorBien;
    }

    public void setColorBien(String colorBien) {
        this.colorBien = colorBien;
    }

    public String getNumeroSerieBien() {
        return numeroSerieBien;
    }

    public void setNumeroSerieBien(String numeroSerieBien) {
        this.numeroSerieBien = numeroSerieBien;
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

    public Producto getIdBien() {
        return idBien;
    }

    public void setIdBien(Producto idBien) {
        this.idBien = idBien;
    }

    public Catalogo getTipoBien() {
        return tipoBien;
    }

    public void setTipoBien(Catalogo tipoBien) {
        this.tipoBien = tipoBien;
    }

    public Catalogo getEstadoBien() {
        return estadoBien;
    }

    public void setEstadoBien(Catalogo estadoBien) {
        this.estadoBien = estadoBien;
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
        if (getIdBien() != null) {
            return getIdBien().getNombreProducto() + " -  Valor:$" + getValorDetalleAdquisicion();
        } else {
            return "";
        }
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
     * @return the nombreBien
     */
    public String getNombreBien() {
        return nombreBien;
    }

    /**
     * @param nombreBien the nombreBien to set
     */
    public void setNombreBien(String nombreBien) {
        this.nombreBien = nombreBien;
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
     * @return the clasebien
     */
    public String getClasebien() {
        return clasebien;
    }

    /**
     * @param clasebien the clasebien to set
     */
    public void setClasebien(String clasebien) {
        this.clasebien = clasebien;
    }

    /**
     * @return the idAdquisicion
     */
    public Adquisicion getIdAdquisicion() {
        return idAdquisicion;
    }

    /**
     * @param idAdquisicion the idAdquisicion to set
     */
    public void setIdAdquisicion(Adquisicion idAdquisicion) {
        this.idAdquisicion = idAdquisicion;
    }

    /**
     * @return the idProductoint
     */
    public Integer getIdProductoint() {
        return idProductoint;
    }

    /**
     * @param idProductoint the idProductoint to set
     */
    public void setIdProductoint(Integer idProductoint) {
        this.idProductoint = idProductoint;
    }

    /**
     * @return the tipoBienInt
     */
    public Integer getTipoBienInt() {
        return tipoBienInt;
    }

    /**
     * @param tipoBienInt the tipoBienInt to set
     */
    public void setTipoBienInt(Integer tipoBienInt) {
        this.tipoBienInt = tipoBienInt;
    }

    /**
     * @return the estadoBienInt
     */
    public Integer getEstadoBienInt() {
        return estadoBienInt;
    }

    /**
     * @param estadoBienInt the estadoBienInt to set
     */
    public void setEstadoBienInt(Integer estadoBienInt) {
        this.estadoBienInt = estadoBienInt;
    }

    /**
     * @return the tipoCantidad
     */
    public String getTipoCantidad() {
        return tipoCantidad;
    }

    /**
     * @param tipoCantidad the tipoCantidad to set
     */
    public void setTipoCantidad(String tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

}
