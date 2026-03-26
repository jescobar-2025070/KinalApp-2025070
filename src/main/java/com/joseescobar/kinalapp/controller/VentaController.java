package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.entity.Cliente;
import com.joseescobar.kinalapp.entity.Usuario;
import com.joseescobar.kinalapp.entity.Venta;
import com.joseescobar.kinalapp.service.IVentaService;
import org.hibernate.sql.exec.spi.PostAction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {
    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> ventas = ventaService.listarTodos();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorCodigo(@PathVariable int codigoVenta){
        return ventaService.buscarPorCodigo(codigoVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Venta>> listarPorEstado(@PathVariable int estado){
        List<Venta> ventas = ventaService.findByEstado(estado);
        return ResponseEntity.ok(ventas);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta){
        try {
            Venta nuevaVenta = ventaService.guardar(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable int codigoVenta){
        try{
            if (!ventaService.existePorCodigo(codigoVenta)){
                return ResponseEntity.notFound().build();
            }
            ventaService.eliminar(codigoVenta);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigoVenta}")
    public ResponseEntity<?> actualizar(@PathVariable int codigoVenta, @RequestBody Venta venta) {
        try {
            Venta ventaActualizada = ventaService.actualizar(codigoVenta, venta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
