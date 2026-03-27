package com.joseescobar.kinalapp.controller;

import com.joseescobar.kinalapp.entity.Producto;
import com.joseescobar.kinalapp.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{codigoProducto}")
    public ResponseEntity<Producto> buscarPorCodigo(@PathVariable Long codigoProducto){
        return productoService.buscarPorCodigo(codigoProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Producto>> listarPorEstado(@PathVariable int estado){
        List<Producto> productos = productoService.findByEstado(estado);
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Producto producto){
        try {
            Producto nuevoProducto = productoService.guardar(producto);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoProducto}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoProducto){
        try{
            if (!productoService.existePorCodigo(codigoProducto)){
                return ResponseEntity.notFound().build();
            }
            productoService.eliminar(codigoProducto);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigoProducto}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigoProducto, @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoService.actualizar(codigoProducto, producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
