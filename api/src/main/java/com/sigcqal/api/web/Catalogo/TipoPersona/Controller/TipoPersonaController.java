package com.sigcqal.api.web.Catalogo.TipoPersona.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.TipoPersona.TipoPersonaService;
import com.sigcqal.api.web.Catalogo.TipoPersona.Dto.TipoPersonaDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/tipos-personas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoPersonaController {
    private final TipoPersonaService tipoPersonaService;

    @GetMapping
    @Operation(summary = "Listar tipos de personas")
    public ResponseEntity<List<TipoPersonaDTO>> listarTiposPersonas() {
        return ResponseEntity.ok(tipoPersonaService.obtenerTiposPersonas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo de persona por id")
    public ResponseEntity<TipoPersonaDTO> obtenerTipoPersona(@PathVariable Long id) {
        return ResponseEntity.ok(tipoPersonaService.obtenerTipoPersona(id));
    }
}
