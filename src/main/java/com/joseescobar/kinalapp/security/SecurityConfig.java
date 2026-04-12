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
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // ── Recursos estáticos y registro ─────────────────────────────
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()   // API registro
                .requestMatchers("/registro", "/registro/**").permitAll()    // Vista registro

                // ── API REST (sigue funcionando con Basic Auth) ────────────────
                .requestMatchers("/clientes/**").authenticated()
                .requestMatchers("/productos/**").authenticated()
                .requestMatchers("/ventas/**").authenticated()
                .requestMatchers("/detalles/**").authenticated()
                .requestMatchers(HttpMethod.GET,    "/usuarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN")

                // ── Vistas Thymeleaf ───────────────────────────────────────────
                .requestMatchers("/dashboard/**").authenticated()
                .requestMatchers("/vista/clientes/**").authenticated()
                .requestMatchers("/vista/productos/**").authenticated()
                .requestMatchers("/vista/ventas/**").authenticated()
                .requestMatchers("/vista/usuarios/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            // Login form de Spring Security apuntando a nuestra vista personalizada
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            // Mantener Basic Auth para clientes API (Postman, etc.)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
