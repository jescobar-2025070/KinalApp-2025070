package com.joseescobar.kinalapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "DetalleVenta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="codigo_detalle_venta")
    private Long codigoDetalleVenta;
    @Column
    private int cantidad;
    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    @Column(name = "sub_total", precision = 10, scale = 2)
    private BigDecimal subTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ventas_codigo_venta")
    private Venta codigoVenta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productos_codigo_producto")
    private Producto codigoProducto;

    public DetalleVenta() {
    }

    public DetalleVenta(Long codigoDetalleVenta, int cantidad, BigDecimal precioUnitario, BigDecimal subTotal, Venta codigoVenta, Producto codigoProducto) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
        this.codigoVenta = codigoVenta;
        this.codigoProducto = codigoProducto;
    }

    public Long getCodigoDetalleVenta() {
        return codigoDetalleVenta;
    }

    public void setCodigoDetalleVenta(Long codigoDetalleVenta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public Venta getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(Venta codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public Producto getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Producto codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
}