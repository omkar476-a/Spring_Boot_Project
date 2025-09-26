package com.example.project_1.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
        // Enable CORS
        .cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of(
            		"http://localhost:5173",
            		"https://spring-boot-project-2.onrender.com"
            		
            		)); // React dev server
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);
            return config;
        }))
        .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
        .authorizeHttpRequests(auth -> auth
            // Public endpoints
            .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll() // optional if APIs public

            // Role-based endpoints
            .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
            .requestMatchers(new AntPathRequestMatcher("/user/**")).hasRole("USER")

            // Any other request requires authentication
            .anyRequest().authenticated()
        )
        // H2 console fix
        .headers(headers -> headers.frameOptions().disable())
        // Disable form login & basic auth
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable());

    return http.build();

    }

}
