package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.Usuario;
import com.joseescobar.kinalapp.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Inyección del encoder

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        validarUsuario(usuario);

        // 1. Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        if (usuario.getEstado() == 0) {
            usuario.setEstado(1);
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCodigo(Long codigoUsuario) {
        return usuarioRepository.findById(codigoUsuario);
    }

    @Override
    public Usuario actualizar(Long codigoUsuario, Usuario usuario) {
        return usuarioRepository.findById(codigoUsuario).map(usuarioExistente -> {
            validarUsuario(usuario);

            usuarioExistente.setUserName(usuario.getUserName());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setRol(usuario.getRol());
            usuarioExistente.setEstado(usuario.getEstado());

            // 2. Solo actualizar y encriptar contraseña si viene en el JSON
            if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()) {
                usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new RuntimeException("El Codigo De Usuario No Existe: " + codigoUsuario));
    }

    @Override
    public void eliminar(Long codigoUsuario) {
        if (!usuarioRepository.existsById(codigoUsuario))
            throw new RuntimeException("El Usuario no existe");
        usuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(Long codigoUsuario) {
        return usuarioRepository.existsById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByEstado(int estado) {
        return usuarioRepository.findByEstado(estado);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getUserName() == null || usuario.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        // Nota: En actualizar, la contraseña podría ser opcional si no se desea cambiar
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("El password es obligatorio");
        }
    }
}