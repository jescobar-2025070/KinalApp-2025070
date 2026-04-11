package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.service.IClienteService;
import com.joseescobar.kinalapp.service.IProductoService;
import com.joseescobar.kinalapp.service.IUsuarioService;
import com.joseescobar.kinalapp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador MVC para las vistas Thymeleaf del Dashboard.
 * NOTA: Este es un controlador de VISTAS (devuelve HTML), distinto de
 * los controladores REST (devuelven JSON) que ya existen en el proyecto.
 */
@Controller
public class DashboardController {

    private final IClienteService clienteService;
    private final IProductoService productoService;
    private final IVentaService ventaService;
    private final IUsuarioService usuarioService;

    public DashboardController(IClienteService clienteService,
                               IProductoService productoService,
                               IVentaService ventaService,
                               IUsuarioService usuarioService) {
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.ventaService = ventaService;
        this.usuarioService = usuarioService;
    }

    // Redirige la raíz al dashboard
    @GetMapping("/")
    public String raiz() {
        return "redirect:/dashboard";
    }

    // Vista principal del Dashboard con estadísticas
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalClientes   = clienteService.listarTodos().size();
        long clientesActivos = clienteService.findByEstado(1).size();

        long totalProductos   = productoService.listarTodos().size();
        long productosActivos = productoService.findByEstado(1).size();

        long totalVentas   = ventaService.listarTodos().size();
        long ventasActivas = ventaService.findByEstado(1).size();

        long totalUsuarios = usuarioService.listarTodos().size();

        model.addAttribute("totalClientes",    totalClientes);
        model.addAttribute("clientesActivos",  clientesActivos);
        model.addAttribute("totalProductos",   totalProductos);
        model.addAttribute("productosActivos", productosActivos);
        model.addAttribute("totalVentas",      totalVentas);
        model.addAttribute("ventasActivas",    ventasActivas);
        model.addAttribute("totalUsuarios",    totalUsuarios);

        // Últimas 5 ventas para la tabla resumen
        model.addAttribute("ultimasVentas",
            ventaService.listarTodos().stream()
                .sorted((a, b) -> b.getCodigoVenta().compareTo(a.getCodigoVenta()))
                .limit(5)
                .toList()
        );

        return "dashboard";
    }

    // Página de login personalizada
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
