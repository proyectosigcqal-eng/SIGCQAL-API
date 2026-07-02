package com.sigcqal.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Contrato de URLs de documentos (correspondencia):
     * - Memorándums firmados: GET /api/files/memorandums/{nombre}.pdf
     * - Adjuntos seguimiento memorandum: GET /api/files/seguimiento-memorandum/{folioFormateado}.pdf
     * - Oficios: GET /api/files/oficios/{nombre}.pdf
     * Siempre rutas relativas bajo /api/files/**; nunca /api/memorandum/{id}/adjunto.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/api/files/memorandums/**")
                .addResourceLocations("file:uploads/memorandums/");

        registry.addResourceHandler("/api/files/seguimiento-memorandum/**")
                .addResourceLocations("file:uploads/seguimiento-memorandum/");
    }
}
