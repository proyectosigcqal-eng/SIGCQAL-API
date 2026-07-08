package com.sigcqal.api.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Configuración type-safe mapeada desde application.properties (prefijo "app")
 * Spring Boot 3.2+ soporta binding nativo a records.
 */
@ConfigurationProperties(prefix = "app")
@Validated
public record AppProperties(
    @NotNull JwtProperties jwt,
    @NotNull SecurityProperties security
) {
    public record JwtProperties(
        @NotBlank String secret,
        @Positive long accessTokenExpirationMs,
        @Positive long refreshTokenExpirationMs
    ) {}

    public record SecurityProperties(
        @NotBlank String corsAllowedOrigins
    ) {}
}