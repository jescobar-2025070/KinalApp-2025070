package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {
    List<DetalleVenta> listarTodos();
    DetalleVenta guardar(DetalleVenta detalleVenta);
    Optional<DetalleVenta> buscarPorCodigo(Long codigoDetalleVenta);
    DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVenta);
    void eliminar(Long codigoDetalleVenta);
    boolean existePorCodigo(Long codigoDetalleVenta);
}
