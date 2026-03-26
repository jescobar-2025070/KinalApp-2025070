package com.joseescobar.kinalapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "DetalleVenta")
public class DetalleVenta {
    @Id
    @Column(name="codigo_detalle_venta")
    private int codigoDetalleVenta;
    @Column
    private int cantidad;
    @Column
    private double precioUnitario;
    @Column
    private double decimal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ventas_codigo_venta")
    private Venta codigoVenta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productos_codigo_producto")
    private Producto codigoProducto;

    public DetalleVenta() {
    }

    public DetalleVenta(int codigoDetalleVenta, int cantidad, double precioUnitario, double decimal, Venta codigoVenta, Producto codigoProducto) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.decimal = decimal;
        this.codigoVenta = codigoVenta;
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoDetalleVenta() {
        return codigoDetalleVenta;
    }

    public void setCodigoDetalleVenta(int codigoDetalleVenta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getDecimal() {
        return decimal;
    }

    public void setDecimal(double decimal) {
        this.decimal = decimal;
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
