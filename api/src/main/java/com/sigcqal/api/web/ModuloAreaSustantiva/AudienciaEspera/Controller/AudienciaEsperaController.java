package com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaEspera.GuardarAudienciaEsperaUseCase;
import com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaEspera.ObtenerAudienciaEsperaUseCase;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto.AudienciaEsperaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto.AudienciaEsperaResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sustantiva/audiencia-espera")
@RequiredArgsConstructor
public class AudienciaEsperaController {

    private final GuardarAudienciaEsperaUseCase guardarUseCase;
    private final ObtenerAudienciaEsperaUseCase obtenerUseCase;

    @PostMapping
    public ResponseEntity<AudienciaEsperaResponseDTO> guardar(
            @Valid @RequestBody AudienciaEsperaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guardarUseCase.ejecutar(request));
    }

    @GetMapping
    public ResponseEntity<List<AudienciaEsperaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(obtenerUseCase.listarTodos());
    }
}
