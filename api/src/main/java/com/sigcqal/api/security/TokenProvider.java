package com.sigcqal.api.security;


import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.sigcqal.api.config.AppProperties;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Entity.UsuarioRolEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class TokenProvider {
    private final SecretKey key;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public TokenProvider(AppProperties props) {
        byte[] keyBytes = props.jwt().secret().getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes); // ✅ Requiere >= 256 bits
        this.accessTokenExpirationMs = props.jwt().accessTokenExpirationMs();
        this.refreshTokenExpirationMs = props.jwt().refreshTokenExpirationMs();
    }

    public String generateAccessToken(UsuarioEntity user, List<UsuarioRolEntity> roles) {
        return Jwts.builder()
            .subject(user.getUsuarioLogin())
            .claim("roles", roles.stream().map(r -> r.getRol().getNombre())
            .toList())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
            .signWith(key)
            .compact();
    }

    public String generateRefreshToken(UsuarioEntity user) {
        return Jwts.builder()
            .subject(user.getUsuarioLogin())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
            .signWith(key)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claimsResolver.apply(claims);
    }
}
