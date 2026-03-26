package com.joseescobar.kinalapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal; // Importación necesaria
import java.time.LocalDate;

@Entity
@Table(name="Ventas")
public class Venta {

    @Id
    @Column(name="codigo_venta")
    private int codigoVenta;
    @Column(name="fecha_venta")
    private LocalDate fechaVenta;
    @Column(precision = 10, scale = 2)
    private BigDecimal total;
    @Column
    private int estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Usuarios_codigo_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Clientes_dpi_cliente", nullable = false)
    private Cliente cliente;

    public Venta() {
    }

    public Venta(int codigoVenta, LocalDate fechaVenta, BigDecimal total, int estado, Usuario usuario, Cliente cliente) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.estado = estado;
        this.usuario = usuario;
        this.cliente = cliente;
    }

    public int getCodigoVenta() { return codigoVenta; }
    public void setCodigoVenta(int codigoVenta) { this.codigoVenta = codigoVenta; }

    public LocalDate getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDate fechaVenta) { this.fechaVenta = fechaVenta; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}