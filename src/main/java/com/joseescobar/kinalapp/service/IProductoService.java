package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> listarTodos();
    Producto guardar(Producto producto);
    Optional<Producto> buscarPorCodigo(Long codigoProducto);
    Producto actualizar(Long codigoProducto, Producto producto);
    void eliminar(Long codigoProducto);
    boolean existePorCodigo(Long codigoProducto);
    List<Producto> findByEstado(int estado);
}
