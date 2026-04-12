package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.entity.DetalleVenta;
import com.joseescobar.kinalapp.entity.Producto;
import com.joseescobar.kinalapp.entity.Venta;
import com.joseescobar.kinalapp.service.IDetalleVentaService;
import com.joseescobar.kinalapp.service.IProductoService;
import com.joseescobar.kinalapp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

/**
 * Controlador MVC de VISTAS para Detalle de Venta (Thymeleaf).
 * Prefijo de rutas: /vista/detalles
 *
 * El formulario calcula el subtotal automáticamente (cantidad × precioUnitario)
 * via JavaScript al seleccionar el producto o cambiar la cantidad.
 */
@Controller
@RequestMapping("/vista/detalles")
public class DetalleVentaViewController {

    private final IDetalleVentaService detalleVentaService;
    private final IVentaService        ventaService;
    private final IProductoService     productoService;

    public DetalleVentaViewController(IDetalleVentaService detalleVentaService,
                                      IVentaService ventaService,
                                      IProductoService productoService) {
        this.detalleVentaService = detalleVentaService;
        this.ventaService        = ventaService;
        this.productoService     = productoService;
    }

    // GET /vista/detalles → Lista todos los detalles
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("detalles", detalleVentaService.listarTodos());
        model.addAttribute("paginaActual", "detalles");
        return "detalles/lista";
    }

    // GET /vista/detalles/nuevo → Formulario vacío
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("detalle",   new DetalleVenta());
        model.addAttribute("ventas",    ventaService.listarTodos());
        model.addAttribute("productos", productoService.findByEstado(1));
        model.addAttribute("paginaActual", "detalles");
        return "detalles/formulario";
    }

    // GET /vista/detalles/nuevo?ventaId=X → Formulario preseleccionando una venta
    @GetMapping(value = "/nuevo", params = "ventaId")
    public String formularioNuevoConVenta(@RequestParam Long ventaId, Model model) {
        DetalleVenta detalle = new DetalleVenta();
        ventaService.buscarPorCodigo(ventaId).ifPresent(detalle::setCodigoVenta);

        model.addAttribute("detalle",   detalle);
        model.addAttribute("ventas",    ventaService.listarTodos());
        model.addAttribute("productos", productoService.findByEstado(1));
        model.addAttribute("paginaActual", "detalles");
        return "detalles/formulario";
    }

    // GET /vista/detalles/editar/{id} → Formulario con datos existentes
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model,
                                   RedirectAttributes redirect) {
        return detalleVentaService.buscarPorCodigo(id)
            .map(detalle -> {
                model.addAttribute("detalle",   detalle);
                model.addAttribute("ventas",    ventaService.listarTodos());
                model.addAttribute("productos", productoService.findByEstado(1));
                model.addAttribute("paginaActual", "detalles");
                return "detalles/formulario";
            })
            .orElseGet(() -> {
                redirect.addFlashAttribute("error", "Detalle #" + id + " no encontrado.");
                return "redirect:/vista/detalles";
            });
    }

    // POST /vista/detalles/guardar → Crear o actualizar
    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Long codigoDetalleVenta,
                          @RequestParam Long           ventaId,
                          @RequestParam Long           productoId,
                          @RequestParam int            cantidad,
                          @RequestParam BigDecimal     precioUnitario,
                          @RequestParam BigDecimal     subTotal,
                          RedirectAttributes redirect) {
        try {
            // Resolver entidades relacionadas
            Venta    venta    = ventaService.buscarPorCodigo(ventaId)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada."));
            Producto producto = productoService.buscarPorCodigo(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setCodigoVenta(venta);
            detalle.setCodigoProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubTotal(subTotal);

            if (codigoDetalleVenta != null && detalleVentaService.existePorCodigo(codigoDetalleVenta)) {
                detalle.setCodigoDetalleVenta(codigoDetalleVenta);
                detalleVentaService.actualizar(codigoDetalleVenta, detalle);
                redirect.addFlashAttribute("exito", "Detalle actualizado correctamente.");
            } else {
                detalleVentaService.guardar(detalle);
                redirect.addFlashAttribute("exito", "Detalle de venta registrado correctamente.");
            }
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/vista/detalles";
    }

    // GET /vista/detalles/eliminar/{id} → Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            detalleVentaService.eliminar(id);
            redirect.addFlashAttribute("exito", "Detalle eliminado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/vista/detalles";
    }
}
