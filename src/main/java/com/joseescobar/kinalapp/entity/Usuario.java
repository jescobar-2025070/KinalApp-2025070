package com.joseescobar.kinalapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="Usuarios")
public class Usuario implements UserDetails {

    @Id
    @Column(name="codigo_usuario")
    private int codigoUsuario;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String rol;

    @Column
    private int estado;

    public Usuario() {
    }

    public Usuario(int codigoUsuario, String userName, String password, String email, String rol, int estado) {
        this.codigoUsuario = codigoUsuario;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
    }

    // --- MÉTODOS DE LA INTERFAZ UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security espera roles con el prefijo ROLE_
        // Ejemplo: "ADMIN" se convierte en "ROLE_ADMIN"
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.rol));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        // Retornamos userName porque es el campo que identifica al usuario en tu DB
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta no expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // La cuenta no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Las credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        // Si 'estado' es 1 está activo, si es 0 está inactivo
        return this.estado == 1;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}