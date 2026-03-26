package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.DetalleVenta;
import com.joseescobar.kinalapp.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService{

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public boolean existePorCodigo(int codigoDetalleVenta) {
        return detalleVentaRepository.existsById(codigoDetalleVenta);
    }

    private void validarDetalleVenta(DetalleVenta detalleVenta) {
        if (detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        if (detalleVenta.getSubTotal() == null || detalleVenta.getSubTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El subtotal debe ser mayor a 0");
        }
    }
}
