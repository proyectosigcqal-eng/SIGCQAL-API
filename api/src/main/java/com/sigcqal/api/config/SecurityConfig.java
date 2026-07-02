package com.sigcqal.api.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Environment env) throws Exception {
        boolean prod = Arrays.asList(env.getActiveProfiles()).contains("prod");

        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .headers(headers -> {
                if (prod) {
                    headers.frameOptions(frame -> frame.sameOrigin());
                } else {
                    // Dev: permitir iframe cross-origin desde Vite (localhost:5173)
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                    headers.contentSecurityPolicy(csp -> csp.policyDirectives(
                        "frame-ancestors 'self' http://localhost:5173 http://127.0.0.1:5173"));
                }
            });

        return http.build();
    }
}