package com.vintedge.config;

import com.vintedge.security.JwtAuthenticationEntryPoint;
import com.vintedge.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            UserDetailsService userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabilitiamo CSRF per semplificare
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/products/**").permitAll() // Accesso pubblico ai prodotti
                        .requestMatchers("/api/auth/**").permitAll() // Accesso pubblico al login e registrazione
                        .anyRequest().authenticated() // Protegge tutte le altre rotte
                )
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }
}
