package com.sigcqal.api.application.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 2.8.2 — Payload incluye id, login, idArea y lista de roles con url_base
    public String generarToken(
            Integer idUsuario,
            String  usuarioLogin,
            Integer idArea,
            List<Map<String, Object>> roles) {

        return Jwts.builder()
                .subject(usuarioLogin)
                .claim("idUsuario",    idUsuario)
                .claim("usuarioLogin", usuarioLogin)
                .claim("idArea",       idArea)
                .claim("roles",        roles)  // [{ idRol, nombreRol, urlBase }]
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public Claims parsearToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean esValido(String token) {
        try {
            parsearToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}