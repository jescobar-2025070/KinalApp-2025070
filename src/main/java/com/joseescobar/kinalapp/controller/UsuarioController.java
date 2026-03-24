package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.entity.Usuario;
import com.joseescobar.kinalapp.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{codigoUsuario}")
    public ResponseEntity<Usuario> buscarPorCodigo(@PathVariable int codigoUsuario){
        return usuarioService.buscarPorCodigo(codigoUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario){
        try {
            Usuario nuevoUsuario = usuarioService.guardar(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoUsuario}")
    public ResponseEntity<Void> eliminar(@PathVariable int codigoUsuario){
        try{
            if(!usuarioService.existePorCodigo(codigoUsuario)){
                return ResponseEntity.notFound().build();
            }
            usuarioService.eliminar(codigoUsuario);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Usuario>> listarPorEstado(@PathVariable int estado){
        List<Usuario> usuarios = usuarioService.findByEstado(estado);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{codigoUsuario}")
    public ResponseEntity<?> actualizar(@PathVariable int codigoUsuario, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizar(codigoUsuario, usuario);
            return ResponseEntity.ok(usuarioActualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
