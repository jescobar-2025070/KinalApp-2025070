package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> listarTodos();
    Producto guardar(Producto producto);
    Optional<Producto> buscarPorCodigo(int codigoProducto);
    Producto actualizar(int codigoProducto, Producto producto);
    void eliminar(int codigoProducto);
    boolean existePorCodigo(int codigoProducto);
    List<Producto> findByEstado(int estado);
}
