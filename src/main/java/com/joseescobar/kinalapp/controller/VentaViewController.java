package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.entity.Venta;
import com.joseescobar.kinalapp.service.IClienteService;
import com.joseescobar.kinalapp.service.IUsuarioService;
import com.joseescobar.kinalapp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC de VISTAS para Ventas (Thymeleaf).
 * Prefijo de rutas: /vista/ventas
 */
@Controller
@RequestMapping("/vista/ventas")
public class VentaViewController {

    private final IVentaService ventaService;
    private final IClienteService clienteService;
    private final IUsuarioService usuarioService;

    public VentaViewController(IVentaService ventaService,
                               IClienteService clienteService,
                               IUsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodos());
        model.addAttribute("paginaActual", "ventas");
        return "ventas/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.findByEstado(1));
        model.addAttribute("usuarios", usuarioService.findByEstado(1));
        model.addAttribute("paginaActual", "ventas");
        return "ventas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        return ventaService.buscarPorCodigo(id)
            .map(venta -> {
                model.addAttribute("venta", venta);
                model.addAttribute("clientes", clienteService.findByEstado(1));
                model.addAttribute("usuarios", usuarioService.findByEstado(1));
                model.addAttribute("paginaActual", "ventas");
                return "ventas/formulario";
            })
            .orElseGet(() -> {
                redirect.addFlashAttribute("error", "Venta no encontrada.");
                return "redirect:/vista/ventas";
            });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta, RedirectAttributes redirect) {
        try {
            boolean esNuevo = venta.getCodigoVenta() == null
                    || !ventaService.existePorCodigo(venta.getCodigoVenta());
            if (esNuevo) {
                ventaService.guardar(venta);
                redirect.addFlashAttribute("exito", "Venta registrada exitosamente.");
            } else {
                ventaService.actualizar(venta.getCodigoVenta(), venta);
                redirect.addFlashAttribute("exito", "Venta actualizada exitosamente.");
            }
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/vista/ventas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            ventaService.eliminar(id);
            redirect.addFlashAttribute("exito", "Venta eliminada correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/vista/ventas";
    }
}
