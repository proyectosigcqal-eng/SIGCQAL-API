package com.sigcqal.api.web.Catalogo.Autoridad.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Autoridad.AutoridadService;
import com.sigcqal.api.web.Catalogo.Autoridad.Dto.AutoridadDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/autoridades")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AutoridadController {
    private final AutoridadService autoridadService;

    @GetMapping
    @Operation(summary = "Listar autoridades")
    public ResponseEntity<List<AutoridadDTO>> listarAutoridades() {
        return ResponseEntity.ok(autoridadService.obtenerAutoridades());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener autoridad por id")
    public ResponseEntity<AutoridadDTO> obtenerAutoridad(@PathVariable Long id) {
        return ResponseEntity.ok(autoridadService.obtenerAutoridad(id));
    }
}
