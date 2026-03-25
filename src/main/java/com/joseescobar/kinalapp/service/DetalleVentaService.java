package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public class DetalleVentaService implements IDetalleVentaService{
    @Override
    public List<DetalleVenta> listarTodos() {
        return List.of();
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        return null;
    }

    @Override
    public Optional<DetalleVenta> buscarPorCodigo(int codigoDetalleVenta) {
        return Optional.empty();
    }

    @Override
    public DetalleVenta actualizar(int codigoDetalleVenta, DetalleVenta detalleVenta) {
        return null;
    }

    @Override
    public void eliminar(int codigoDetalleVenta) {

    }

    @Override
    public boolean existePorCodigo(int codigoDetalleVenta) {
        return false;
    }
}
