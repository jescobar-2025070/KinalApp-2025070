package com.joseescobar.kinalapp.service;


import com.joseescobar.kinalapp.entity.Venta;
import com.joseescobar.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class VentaService implements IVentaService{
    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta) {
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public Optional<Venta> buscarPorCodigo(Long codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    public Venta actualizar(Long codigoVenta, Venta venta) {
        if(!ventaRepository.existsById(codigoVenta))
            throw new RuntimeException("El codigo de venta no existe");
        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Long codigoVenta) {
        if(!ventaRepository.existsById(codigoVenta))
            throw new RuntimeException("La venta no existe");
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(Long codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    @Override
    public List<Venta> findByEstado(int estado) {
        return ventaRepository.findByEstado(estado);
    }

    private void validarVenta(Venta venta) {
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria.");
        }

        if (venta.getTotal() == null || venta.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El total de la venta debe ser mayor a 0.");
        }
    }
}
