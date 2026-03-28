package com.joseescobar.kinalapp.service;


import com.joseescobar.kinalapp.entity.Cliente;
import com.joseescobar.kinalapp.entity.Usuario;
import com.joseescobar.kinalapp.entity.Venta;
import com.joseescobar.kinalapp.repository.ClienteRepository;
import com.joseescobar.kinalapp.repository.UsuarioRepository;
import com.joseescobar.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class VentaService implements IVentaService{
    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;       // agregar
    private final UsuarioRepository usuarioRepository;

    public VentaService(VentaRepository ventaRepository,
                        ClienteRepository clienteRepository,
                        UsuarioRepository usuarioRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta) {
        // Resolver cliente desde la BD
        Cliente cliente = clienteRepository.findById(venta.getCliente().getDPICliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

        // Resolver usuario desde la BD
        Usuario usuario = usuarioRepository.findById(venta.getUsuario().getCodigoUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        venta.setCliente(cliente);
        venta.setUsuario(usuario);

        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public Optional<Venta> buscarPorCodigo(Long codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    public Venta actualizar(Long codigoVenta, Venta venta) {
        if (!ventaRepository.existsById(codigoVenta))
            throw new RuntimeException("El codigo de venta no existe");

        Cliente cliente = clienteRepository.findById(venta.getCliente().getDPICliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

        Usuario usuario = usuarioRepository.findById(venta.getUsuario().getCodigoUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        venta.setCodigoVenta(codigoVenta);
        venta.setCliente(cliente);
        venta.setUsuario(usuario);

        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Long codigoVenta) {
        if(!ventaRepository.existsById(codigoVenta))
            throw new RuntimeException("La venta no existe");
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(Long codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    @Override
    public List<Venta> findByEstado(int estado) {
        return ventaRepository.findByEstado(estado);
    }

    private void validarVenta(Venta venta) {
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria.");
        }

        if (venta.getTotal() == null || venta.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El total de la venta debe ser mayor a 0.");
        }
    }
}
