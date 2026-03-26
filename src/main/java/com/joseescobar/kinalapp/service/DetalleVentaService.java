package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.DetalleVenta;
import com.joseescobar.kinalapp.repository.DetalleVentaRepository;

import java.util.List;
import java.util.Optional;

public class DetalleVentaService implements IDetalleVentaService{

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    public List<DetalleVenta> listarTodos() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        validarDetalleVenta(detalleVenta);
        return detalleVentaRepository.save(detalleVenta);

    }

    @Override
    public Optional<DetalleVenta> buscarPorCodigo(int codigoDetalleVenta) {
        return detalleVentaRepository.findById(codigoDetalleVenta);
    }

    @Override
    public DetalleVenta actualizar(int codigoDetalleVenta, DetalleVenta detalleVenta) {
        if(!detalleVentaRepository.existsById(codigoDetalleVenta))
            throw new RuntimeException("No existe");
        detalleVenta.setCodigoDetalleVenta(codigoDetalleVenta);
        validarDetalleVenta(detalleVenta);
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void eliminar(int codigoDetalleVenta) {
        if(!detalleVentaRepository.existsById(codigoDetalleVenta))
            throw new RuntimeException("No existe");
        detalleVentaRepository.deleteById(codigoDetalleVenta);
    }

    @Override
    public boolean existePorCodigo(int codigoDetalleVenta) {
        return detalleVentaRepository.existsById(codigoDetalleVenta);
    }

    private void validarDetalleVenta(DetalleVenta detalleVenta){
        if (detalleVenta.getCantidad() == 0){
            throw new IllegalArgumentException("La cantidad no puede ser 0");
        }
        if (detalleVenta.getsubTotal() <= 0){
            throw new IllegalArgumentException("El subtotal no puede ser 0");
        }
    }
}
