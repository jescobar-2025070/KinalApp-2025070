package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listarTodos();
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorCodigo(int codigoUsuario);
    Usuario actualizar(int codigoUsuario, Usuario usuario);
    void eliminar(int codigoUsuario);
    boolean existePorCodigo(int codigoUsuario);
    List<Usuario> findByEstado(int estado);
}
