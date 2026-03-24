package com.joseescobar.kinalapp.service;

import com.joseescobar.kinalapp.entity.Usuario;
import com.joseescobar.kinalapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService{
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        validarUsuario(usuario);
         if(usuario.getEstado() == 0) {
             usuario.setEstado(1);
         }
         return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCodigo(int codigoUsuario) {
        return usuarioRepository.findById(codigoUsuario);
    }

    @Override
    public Usuario actualizar(int codigoUsuario, Usuario usuario) {

        if (!usuarioRepository.existsById(codigoUsuario))
            throw new RuntimeException("El Codigo De Usuario No Existe");
        usuario.setCodigoUsuario(codigoUsuario);
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(int codigoUsuario) {
        if(!usuarioRepository.existsById(codigoUsuario))
            throw new RuntimeException("El Usuario no existe");
        usuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(int codigoUsuario) {
        return usuarioRepository.existsById(codigoUsuario);
    }

    @Override
    public List<Usuario> findByEstado(int estado) {
        return usuarioRepository.findByEstado(estado);
    }

    private void validarUsuario(Usuario usuario){
        if(usuario.getCodigoUsuario() !=0 ) {
            throw new IllegalArgumentException("El codigo es un obligatorio");
        }
        if(usuario.getUserName() == null || usuario.getUserName().trim().isEmpty()){
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        if(usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()){
            throw new IllegalArgumentException("El password es obligatorio");
        }
    }
}
