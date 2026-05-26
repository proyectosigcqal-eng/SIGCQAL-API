package com.sigcqal.api.web.Catalogo.CatAutoridad.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.CatAutoridad.CatAutoridadService;
import com.sigcqal.api.web.Catalogo.CatAutoridad.Dto.CatAutoridadDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/cat-autoridades")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CatAutoridadController {
    private final CatAutoridadService catAutoridadService;

    @GetMapping
    @Operation(summary = "Listar catAutoridades")
    public ResponseEntity<List<CatAutoridadDTO>> listarCatAutoridades() {
        return ResponseEntity.ok(catAutoridadService.obtenerCatAutoridades());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener catAutoridad por id")
    public ResponseEntity<CatAutoridadDTO> obtenerCatAutoridad(@PathVariable Long id) {
        return ResponseEntity.ok(catAutoridadService.obtenerCatAutoridad(id));
    }
}
