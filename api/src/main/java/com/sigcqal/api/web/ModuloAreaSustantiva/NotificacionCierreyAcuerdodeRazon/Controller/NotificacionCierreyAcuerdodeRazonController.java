package com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Model.NotificacionCierreyAcuerdodeRazon;
import com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.DTO.NotificacionCierreyAcuerdodeRazonRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.DTO.NotificacionCierreyAcuerdodeRazonResponseDTO;
import com.sigcqal.api.application.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.NotificacionCierreyAcuerdodeRazonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/notificacion-cierre-acuerdo-razon")
@RequiredArgsConstructor
public class NotificacionCierreyAcuerdodeRazonController {
    
private final NotificacionCierreyAcuerdodeRazonService service;

    @PostMapping
    public ResponseEntity<NotificacionCierreyAcuerdodeRazonResponseDTO> registrar(
            @Valid @RequestBody NotificacionCierreyAcuerdodeRazonRequestDTO dto) {

        // 1. Mapeo: DTO -> Modelo de Dominio
        NotificacionCierreyAcuerdodeRazon domain = NotificacionCierreyAcuerdodeRazon.builder()
                .idExpediente(dto.getIdExpediente())
                .medioNotificacion(dto.getMedioNotificacion())
                .rutaArchivoAcuerdo(dto.getRutaArchivoAcuerdo())
                .idUsuarioCierre(dto.getIdUsuarioCierre())
                .build();

        // 2. Ejecutar la lógica de negocio (aquí se valida, bloquea el exp y guarda)
        NotificacionCierreyAcuerdodeRazon guardado = service.registrarCierreDefinitivo(domain);

        // 3. Mapeo: Dominio -> ResponseDTO
        NotificacionCierreyAcuerdodeRazonResponseDTO response = NotificacionCierreyAcuerdodeRazonResponseDTO.builder()
                .idCierre(guardado.getIdCierre())
                .idExpediente(guardado.getIdExpediente())
                .medioNotificacion(guardado.getMedioNotificacion())
                .rutaArchivoAcuerdo(guardado.getRutaArchivoAcuerdo())
                .fechaCierre(guardado.getFechaCierre())
                .idUsuarioCierre(guardado.getIdUsuarioCierre())
                .build();

        return ResponseEntity.ok(response);
    }
}