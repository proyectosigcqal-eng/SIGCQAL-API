package com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.RecursoRevision.GuardarRecursoRevisionUseCase;
import com.sigcqal.api.application.ModuloAreaSustantiva.RecursoRevision.ObtenerRecursoRevisionUseCase;
import com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto.RecursoRevisionRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto.RecursoRevisionResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sustantiva/recurso-revision")
@RequiredArgsConstructor
public class RecursoRevisionController {

    private final GuardarRecursoRevisionUseCase guardarUseCase;
    private final ObtenerRecursoRevisionUseCase obtenerUseCase;

    @PostMapping
    public ResponseEntity<RecursoRevisionResponseDTO> guardar(
            @Valid @RequestBody RecursoRevisionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guardarUseCase.ejecutar(request));
    }

    @GetMapping
    public ResponseEntity<List<RecursoRevisionResponseDTO>> listarTodos() {
        return ResponseEntity.ok(obtenerUseCase.listarTodos());
    }
}
