package com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.controller;

import jakarta.servlet.http.HttpServletRequest;

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
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.ConstanciaPreviewResponse;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.GenerarConstanciaRequest;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.GenerarConstanciaResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/expedientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConstanciaInternaRemisionController {

    private final ConstanciaInternaRemisionService service;

    @GetMapping("/{expedienteId}/constancia-interna-remision/preview")
    public ResponseEntity<ConstanciaPreviewResponse> obtenerPreview(@PathVariable Integer expedienteId) {
        return ResponseEntity.ok(service.obtenerPreview(expedienteId));
    }

    @PostMapping("/{expedienteId}/constancia-interna-remision")
    public ResponseEntity<GenerarConstanciaResponse> generarConstancia(
        @PathVariable Integer expedienteId,
        @RequestBody GenerarConstanciaRequest request,
        HttpServletRequest http
    ) {
        GenerarConstanciaRequest body = request != null ? request : new GenerarConstanciaRequest();
        validarExpedienteId(expedienteId, body.getExpedienteId());
        body.setExpedienteId(expedienteId);
        body.setIpCliente(obtenerIp(http));
        return ResponseEntity.ok(service.generar(expedienteId, body));
    }

    @PostMapping("/constancia-interna-remision")
    public ResponseEntity<GenerarConstanciaResponse> generarConstanciaCompat(
        @RequestBody GenerarConstanciaRequest request,
        HttpServletRequest http
    ) {
        GenerarConstanciaRequest body = request != null ? request : new GenerarConstanciaRequest();
        if (body.getExpedienteId() == null || body.getExpedienteId() <= 0) {
            throw new InvalidRequestException("El expedienteId debe ser mayor a 0");
        }
        body.setIpCliente(obtenerIp(http));
        return ResponseEntity.ok(service.generar(body.getExpedienteId(), body));
    }

    @GetMapping("/{folio}/constancia-pdf")
    public ResponseEntity<byte[]> obtenerConstancia(@PathVariable String folio) {
        var resultOpt = service.obtenerExistente(folio);
        if (resultOpt.isEmpty()) return ResponseEntity.notFound().build();
        var result = resultOpt.get();
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + result.nombreArchivo() + "\"")
            .body(result.pdf());
    }

    private void validarExpedienteId(Integer expedienteIdPath, Integer expedienteIdBody) {
        if (expedienteIdBody != null && !expedienteIdBody.equals(expedienteIdPath)) {
            throw new InvalidRequestException("El expedienteId del path no coincide con el body");
        }
    }

    private String obtenerIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            String[] parts = xff.split(",");
            if (parts.length > 0 && parts[0] != null) {
                return parts[0].trim();
            }
        }
        return request.getRemoteAddr();
    }
}
