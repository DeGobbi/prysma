package com.prysma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // libera acesso aos endpoints da API
                        .requestMatchers("/api/**").permitAll()
                        // libera acesso público às imagens
                        .requestMatchers("/uploads/**").permitAll()
                        // qualquer outro endpoint precisa de autenticação
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}