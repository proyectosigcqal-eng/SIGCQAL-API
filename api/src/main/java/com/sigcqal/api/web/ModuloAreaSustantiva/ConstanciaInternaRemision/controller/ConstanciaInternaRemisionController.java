package com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.ConstanciaInternaRemision.ConstanciaInternaRemisionService;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.ConstanciaPreviewResponse;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.GenerarConstanciaRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/expedientes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ConstanciaInternaRemisionController {

    private final ConstanciaInternaRemisionService service;

    /**
     * Genera la Constancia Interna de Remisión (DOCX) y la persiste en BD
     */
    @PostMapping("/{expedienteId}/constancia-interna-remision/generate")
    public ResponseEntity<byte[]> generarConstancia(
        @PathVariable Integer expedienteId,
        @Valid @RequestBody GenerarConstanciaRequest request,
        HttpServletRequest http
    ) {
        log.info("Generando CIR para expedienteId: {}", expedienteId);
        request.setIpCliente(obtenerIp(http));
        
        var archivo = service.generar(expedienteId, request);

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            ))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.nombreArchivo() + "\"")
            .body(archivo.archivo());
    }

    /**
     * Genera HTML preview para visualización en iframe (sin generar DOCX)
     */
    @PostMapping("/{expedienteId}/constancia-interna-remision/preview")
    public ResponseEntity<String> previewCIR(
        @PathVariable Integer expedienteId,
        @Valid @RequestBody GenerarConstanciaRequest request
    ) {
        log.info("Generando preview HTML para expedienteId: {}", expedienteId);
        try {
            String html = service.generarHtmlPreview(expedienteId, request);
            return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
        } catch (Exception e) {
            log.error("Error al generar preview", e);
            return ResponseEntity.badRequest()
                .body("<html><body><h1>Error: " + e.getMessage() + "</h1></body></html>");
        }
    }

    /**
     * Obtiene datos de preview (metadatos de la CIR sin generar archivo)
     */
    @GetMapping("/{folio}/constancia-preview")
    public ResponseEntity<ConstanciaPreviewResponse> obtenerPreview(@PathVariable String folio) {
        log.info("Obteniendo preview para folio: {}", folio);
        return ResponseEntity.ok(service.obtenerPreview(folio));
    }

    /**
     * Descarga una Constancia ya generada
     */
    @GetMapping("/{expedienteId}/constancia-interna-remision/download/{filename}")
    public ResponseEntity<Resource> descargarConstancia(
        @PathVariable Integer expedienteId,
        @PathVariable String filename
    ) {
        log.info("Descargando constancia: {} para expedienteId: {}", filename, expedienteId);
        try {
            Path root = Paths.get(".").toAbsolutePath().normalize();
            Path archivo = root.resolve("uploads/constancias/" + filename);
            Resource resource = new UrlResource(archivo.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                log.warn("Archivo no encontrado o no legible: {}", archivo);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
        } catch (MalformedURLException e) {
            log.error("Error al descargar constancia", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Helper para obtener IP del cliente desde request
     */
    private String obtenerIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @GetMapping("/folio/{folio}/constancia-interna-remision/descargar")
public ResponseEntity<byte[]> descargarConstanciaPorFolio(@PathVariable String folio) {
    log.info("Descargando CIR por folio: {}", folio);
    return service.obtenerExistente(folio)
        .map(cir -> ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cir.nombreArchivo() + "\"")
            .body(cir.archivo()))
        .orElseGet(() -> ResponseEntity.notFound().build());
}
}