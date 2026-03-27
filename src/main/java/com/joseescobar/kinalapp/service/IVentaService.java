package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.Venta;

import java.util.List;
import java.util.Optional;

public interface IVentaService {
    List<Venta> listarTodos();
    Venta guardar(Venta venta);
    Optional<Venta> buscarPorCodigo(Long codigoVenta);
    Venta actualizar(Long codigoVenta, Venta venta);
    void eliminar(Long codigoVenta);
    boolean existePorCodigo(Long codigoVenta);
    List<Venta> findByEstado(int estado);
}
