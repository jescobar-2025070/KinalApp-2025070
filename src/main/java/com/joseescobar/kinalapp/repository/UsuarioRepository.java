package com.joseescobar.kinalapp.repository;

import com.joseescobar.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByEstado(int estado);
    Optional<Usuario> findByUserName(String userName);
}
