package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.entity.Producto;
import com.joseescobar.kinalapp.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC de VISTAS para Productos (Thymeleaf).
 * Prefijo de rutas: /vista/productos
 */
@Controller
@RequestMapping("/vista/productos")
public class ProductoViewController {

    private final IProductoService productoService;

    public ProductoViewController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("paginaActual", "productos");
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("paginaActual", "productos");
        return "productos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        return productoService.buscarPorCodigo(id)
            .map(producto -> {
                model.addAttribute("producto", producto);
                model.addAttribute("paginaActual", "productos");
                return "productos/formulario";
            })
            .orElseGet(() -> {
                redirect.addFlashAttribute("error", "Producto no encontrado.");
                return "redirect:/vista/productos";
            });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes redirect) {
        try {
            boolean esNuevo = producto.getCodigoProducto() == null
                    || !productoService.existePorCodigo(producto.getCodigoProducto());
            if (esNuevo) {
                productoService.guardar(producto);
                redirect.addFlashAttribute("exito", "Producto creado exitosamente.");
            } else {
                productoService.actualizar(producto.getCodigoProducto(), producto);
                redirect.addFlashAttribute("exito", "Producto actualizado exitosamente.");
            }
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/vista/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            productoService.eliminar(id);
            redirect.addFlashAttribute("exito", "Producto eliminado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/vista/productos";
    }
}
