package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.entity.Cliente;
import com.joseescobar.kinalapp.service.IClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC de VISTAS para Clientes (Thymeleaf).
 * Prefijo de rutas: /vista/clientes
 */
@Controller
@RequestMapping("/vista/clientes")
public class ClienteViewController {

    private final IClienteService clienteService;

    public ClienteViewController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // GET /vista/clientes → Lista todos los clientes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("paginaActual", "clientes");
        return "clientes/lista";
    }

    // GET /vista/clientes/nuevo → Muestra formulario de creación
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("paginaActual", "clientes");
        return "clientes/formulario";
    }

    // GET /vista/clientes/editar/{dpi} → Muestra formulario de edición
    @GetMapping("/editar/{dpi}")
    public String formularioEditar(@PathVariable Long dpi, Model model, RedirectAttributes redirect) {
        return clienteService.buscarPorDPI(dpi)
            .map(cliente -> {
                model.addAttribute("cliente", cliente);
                model.addAttribute("paginaActual", "clientes");
                return "clientes/formulario";
            })
            .orElseGet(() -> {
                redirect.addFlashAttribute("error", "Cliente con DPI " + dpi + " no encontrado.");
                return "redirect:/vista/clientes";
            });
    }

    // POST /vista/clientes/guardar → Crea o actualiza un cliente
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente, RedirectAttributes redirect) {
        try {
            boolean esNuevo = !clienteService.existePorDPI(cliente.getDPICliente());
            if (esNuevo) {
                clienteService.guardar(cliente);
                redirect.addFlashAttribute("exito", "Cliente creado exitosamente.");
            } else {
                clienteService.actualizar(cliente.getDPICliente(), cliente);
                redirect.addFlashAttribute("exito", "Cliente actualizado exitosamente.");
            }
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/vista/clientes";
    }

    // GET /vista/clientes/eliminar/{dpi} → Elimina un cliente
    @GetMapping("/eliminar/{dpi}")
    public String eliminar(@PathVariable Long dpi, RedirectAttributes redirect) {
        try {
            clienteService.eliminar(dpi);
            redirect.addFlashAttribute("exito", "Cliente eliminado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/vista/clientes";
    }
}
