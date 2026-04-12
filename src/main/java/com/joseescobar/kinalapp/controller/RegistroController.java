package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.entity.Usuario;
import com.joseescobar.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de la vista de registro público.
 * No requiere autenticación (configurado en SecurityConfig).
 */
@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final IUsuarioService usuarioService;

    public RegistroController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping
    public String registrar(@ModelAttribute Usuario usuario, RedirectAttributes redirect) {
        try {
            // El rol por defecto para auto-registro es USER
            if (usuario.getRol() == null || usuario.getRol().isBlank()) {
                usuario.setRol("USER");
            }
            usuario.setEstado(1);
            usuarioService.guardar(usuario);
            redirect.addFlashAttribute("exito", "Cuenta creada exitosamente. Ya puedes iniciar sesión.");
            return "redirect:/login";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al registrarse: " + e.getMessage());
            return "redirect:/registro";
        }
    }
}
