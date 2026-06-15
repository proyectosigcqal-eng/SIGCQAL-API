package com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.application.ModuloAreaSustantiva.ControlPlazosAutoridad.ControlPlazosAutoridadService;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.RegistroInformeAutoridadRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.RegistroInformeAutoridadResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.SemaforoAutoridadResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/expedientes/{expedienteId}/plazo-autoridad")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ControlPlazosAutoridadController {

    private final ControlPlazosAutoridadService service;

    @GetMapping("/semaforo")
    public ResponseEntity<SemaforoAutoridadResponseDTO> obtenerSemaforo(@PathVariable Long expedienteId) {
        return ResponseEntity.ok(service.obtenerSemaforo(expedienteId));
    }

    @PostMapping(value = "/registrar-informe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RegistroInformeAutoridadResponseDTO> registrarInforme(
            @PathVariable Long expedienteId,
            @RequestPart("request") RegistroInformeAutoridadRequestDTO request,
            @RequestPart("pdf") MultipartFile pdf) throws IOException {
        return ResponseEntity.ok(service.registrarInforme(expedienteId, request, pdf));
    }
}

