package com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaEjecutoria.GuardarSentenciaEjecutoriaUseCase;
import com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaEjecutoria.ObtenerSentenciaEjecutoriaUseCase;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto.SentenciaEjecutoriaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto.SentenciaEjecutoriaResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sustantiva/sentencia-ejecutoria")
@RequiredArgsConstructor
public class SentenciaEjecutoriaController {

    private final GuardarSentenciaEjecutoriaUseCase guardarUseCase;
    private final ObtenerSentenciaEjecutoriaUseCase obtenerUseCase;

    @PostMapping
    public ResponseEntity<SentenciaEjecutoriaResponseDTO> guardar(
            @Valid @RequestBody SentenciaEjecutoriaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guardarUseCase.ejecutar(request));
    }

    @GetMapping
    public ResponseEntity<List<SentenciaEjecutoriaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(obtenerUseCase.listarTodos());
    }
}
