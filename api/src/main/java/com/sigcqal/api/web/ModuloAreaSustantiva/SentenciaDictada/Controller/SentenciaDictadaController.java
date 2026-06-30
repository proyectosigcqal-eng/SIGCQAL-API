package com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaDictada.GuardarSentenciaDictadaUseCase;
import com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaDictada.ObtenerSentenciaDictadaUseCase;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto.SentenciaDictadaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto.SentenciaDictadaResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sustantiva/sentencia-dictada")
@RequiredArgsConstructor
public class SentenciaDictadaController {

    private final GuardarSentenciaDictadaUseCase guardarUseCase;
    private final ObtenerSentenciaDictadaUseCase obtenerUseCase;

    @PostMapping
    public ResponseEntity<SentenciaDictadaResponseDTO> guardar(
            @Valid @RequestBody SentenciaDictadaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guardarUseCase.ejecutar(request));
    }

    @GetMapping
    public ResponseEntity<List<SentenciaDictadaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(obtenerUseCase.listarTodos());
    }
}
