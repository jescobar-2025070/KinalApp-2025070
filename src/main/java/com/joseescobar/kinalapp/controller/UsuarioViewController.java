package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.entity.Usuario;
import com.joseescobar.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC de VISTAS para Usuarios (Thymeleaf).
 * Solo accesible por rol ADMIN.
 * Prefijo de rutas: /vista/usuarios
 */
@Controller
@RequestMapping("/vista/usuarios")
public class UsuarioViewController {

    private final IUsuarioService usuarioService;

    public UsuarioViewController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("paginaActual", "usuarios");
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("paginaActual", "usuarios");
        return "usuarios/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        return usuarioService.buscarPorCodigo(id)
            .map(usuario -> {
                // No exponemos el hash de contraseña en el formulario
                usuario.setPassword("");
                model.addAttribute("usuario", usuario);
                model.addAttribute("paginaActual", "usuarios");
                return "usuarios/formulario";
            })
            .orElseGet(() -> {
                redirect.addFlashAttribute("error", "Usuario no encontrado.");
                return "redirect:/vista/usuarios";
            });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, RedirectAttributes redirect) {
        try {
            boolean esNuevo = usuario.getCodigoUsuario() == null
                    || !usuarioService.existePorCodigo(usuario.getCodigoUsuario());
            if (esNuevo) {
                usuarioService.guardar(usuario);
                redirect.addFlashAttribute("exito", "Usuario creado exitosamente.");
            } else {
                usuarioService.actualizar(usuario.getCodigoUsuario(), usuario);
                redirect.addFlashAttribute("exito", "Usuario actualizado exitosamente.");
            }
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/vista/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            usuarioService.eliminar(id);
            redirect.addFlashAttribute("exito", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/vista/usuarios";
    }
}
