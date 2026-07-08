package com.sigcqal.api.security;


import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sigcqal.api.infra.Catalogo.UsuarioRol.Repository.UserSessionRepository;

import java.io.IOException;

@Slf4j  // ← Habilita log.debug() sin configurar logger manualmente
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final @Lazy UserDetailsService userDetailsService;
private final UserSessionRepository sessionRepository; 
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain chain)
            throws ServletException, IOException {
        
        String header = request.getHeader("Authorization");
        
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            
            try {
                // 1. Validar estructura y firma del token
                if (tokenProvider.validateToken(token)) {
                    String username = tokenProvider.extractUsername(token);


  // 🔹 NUEVO: Verificar que exista una sesión activa en BD
        boolean hasActiveSession = sessionRepository
            .existsByUserUsernameAndIsActiveTrue(username); // Método que debes agregar al repo
            
        if (!hasActiveSession) {
            log.warn("Token válido pero sesión inactiva para usuario: {}", username);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sesión cerrada");
            return; // Detiene la cadena de filtros
        }

                    
                    // 2. Solo autenticar si no hay contexto previo
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        
                        var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            null, 
                            userDetails.getAuthorities()
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        
                        log.debug("Usuario autenticado: {}", username);
                    }
                }
            } catch (JwtException e) {
                // 🔹 Token malformado, expirado o con firma inválida
                log.warn("JWT inválido: {}", e.getMessage());
                // No lanzamos excepción: dejamos que Spring devuelva 401 automáticamente
            } catch (IllegalArgumentException e) {
                // 🔹 Token null, vacío o formato inesperado
                log.warn("Error procesando Authorization header: {}", e.getMessage());
            }
            // ⚠️ IMPORTANTE: No hacemos chain.doFilter() aquí si queremos bloquear
            // Pero lo correcto es dejar continuar para que otros filtros manejen la respuesta
        }
        
        chain.doFilter(request, response);
    }
}