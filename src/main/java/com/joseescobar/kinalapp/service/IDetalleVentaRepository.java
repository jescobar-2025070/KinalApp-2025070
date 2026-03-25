package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaRepository {
    List<DetalleVenta> listarTodos();
    DetalleVenta guardar(DetalleVenta detalleVenta);
    Optional<DetalleVenta> buscarPorCodigo(int codigoDetalleVenta);

}
