package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.Producto;
import com.joseescobar.kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService implements IProductoService{
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }


    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> buscarPorCodigo(Long codigoProducto) {
        return productoRepository.findById(codigoProducto);
    }

    @Override
    public Producto actualizar(Long codigoProducto, Producto producto) {
        if(!productoRepository.existsById(codigoProducto))
            throw new RuntimeException("El producto no existe");
        producto.setCodigoProducto(codigoProducto);
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(Long codigoProducto) {
        if(!productoRepository.existsById(codigoProducto))
            throw new RuntimeException("El producto no existe");
        productoRepository.deleteById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(Long codigoProducto) {
        return productoRepository.existsById(codigoProducto);
    }

    @Override
    public List<Producto> findByEstado(int estado) {
        return productoRepository.findByEstado(estado);
    }

    private void validarProducto(Producto producto){
        if(producto.getStock() <= 0){
            throw new IllegalArgumentException("El stock no puede ser 0");
        }
        if(producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }
}
