package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.Venta;

import java.util.List;
import java.util.Optional;

public interface IVentaService {
    List<Venta> listarTodos();
    Venta guardar(Venta venta);
    Optional<Venta> buscarPorCodigo(int codigoVenta);
    Venta actualizar(int codigoVenta, Venta venta);
    void eliminar(int codigoVenta);
    boolean existePorCodigo(int codigoVenta);
    List<Venta> findByEstado(int estado);
}
