package com.joseescobar.kinalapp.security;

import com.joseescobar.kinalapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar para facilitar pruebas con Postman/APIs
                .authorizeHttpRequests(auth -> auth
                        // 1. RUTAS PÚBLICAS
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll() // Permitir registro
                        .requestMatchers("/login", "/publico/**").permitAll()

                        // 2. RUTAS PROTEGIDAS (Requieren Login)
                        .requestMatchers("/clientes/**").authenticated()
                        .requestMatchers("/productos/**").authenticated()
                        .requestMatchers("/ventas/**").authenticated()
                        .requestMatchers("/detalles/**").authenticated()

                        // 3. SEGURIDAD EXTRA PARA USUARIOS
                        // Solo un admin debería poder listar o borrar otros usuarios
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN")

                        .anyRequest().authenticated() // Cualquier otra ruta no especificada pide login
                )
                .httpBasic(Customizer.withDefaults()) // Permite autenticación básica para probar en Postman
                .formLogin(form -> form.permitAll())   // Habilita el formulario de login de Spring
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}