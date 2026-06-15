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

@RestController
@RequestMapping("/api/v1/expedientes")
@CrossOrigin(origins = "*")
public class ConstanciaInternaRemisionController {

    private final ConstanciaInternaRemisionService service;

    public ConstanciaInternaRemisionController(ConstanciaInternaRemisionService service) {
        this.service = service;
    }

    @PostMapping("/{expedienteId}/constancia-interna-remision/generate")
    public ResponseEntity<byte[]> generarConstancia(
        @PathVariable Integer expedienteId,
        @Valid @RequestBody GenerarConstanciaRequest request,
        HttpServletRequest http
    ) {
        request.setIpCliente(obtenerIp(http));
        var archivo = service.generar(expedienteId, request);

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            ))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.nombreArchivo() + "\"")
            .body(archivo.archivo());
    }

    @GetMapping("/{expedienteId}/constancia-interna-remision/download/{filename}")
    public ResponseEntity<Resource> descargarConstancia(
        @PathVariable Integer expedienteId,
        @PathVariable String filename
    ) {
        try {
            Path root = Paths.get(".").toAbsolutePath().normalize();
            Path archivo = root.resolve("uploads/constancias/" + filename);
            Resource resource = new UrlResource(archivo.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{folio}/constancia-preview")
    public ResponseEntity<ConstanciaPreviewResponse> obtenerPreview(@PathVariable String folio) {
        return ResponseEntity.ok(service.obtenerPreview(folio));
    }

    private String obtenerIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
