package com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaCelebrada.GuardarAudienciaCelebradaUseCase;
import com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaCelebrada.ObtenerAudienciaCelebradaUseCase;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sustantiva/audiencia-celebrada")
@RequiredArgsConstructor
public class AudienciaCelebradaController {

    private final GuardarAudienciaCelebradaUseCase guardarUseCase;
    private final ObtenerAudienciaCelebradaUseCase obtenerUseCase;

    @PostMapping
    public ResponseEntity<AudienciaCelebradaResponseDTO> guardar(
            @Valid @RequestBody AudienciaCelebradaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guardarUseCase.ejecutar(request));
    }

    @GetMapping
    public ResponseEntity<List<AudienciaCelebradaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(obtenerUseCase.listarTodos());
    }
}
