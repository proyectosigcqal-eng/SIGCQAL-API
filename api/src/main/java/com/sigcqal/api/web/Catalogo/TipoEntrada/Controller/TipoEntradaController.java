package com.sigcqal.api.web.Catalogo.TipoEntrada.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.TipoEntrada.TipoEntradaService;
import com.sigcqal.api.web.Catalogo.TipoEntrada.Dto.TipoEntradaDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/tipo-entrada")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoEntradaController {
    private final TipoEntradaService tipoEntradaService;

    @GetMapping
    @Operation(summary = "Listar tipo entrada")
    public ResponseEntity<List<TipoEntradaDTO>> listarTipoEntrada() {
        return ResponseEntity.ok(tipoEntradaService.obtenerTipoEntradas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo entrada por id")
    public ResponseEntity<TipoEntradaDTO> obtenerTipoEntrada(@PathVariable Long id) {
        return ResponseEntity.ok(tipoEntradaService.obtenerTipoEntrada(id));
    }
}
