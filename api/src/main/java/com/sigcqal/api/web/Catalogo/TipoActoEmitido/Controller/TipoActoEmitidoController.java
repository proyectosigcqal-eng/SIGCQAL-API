package com.sigcqal.api.web.Catalogo.TipoActoEmitido.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.TipoActoEmitido.TipoActoEmitidoService;
import com.sigcqal.api.web.Catalogo.TipoActoEmitido.Dto.TipoActoEmitidoDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/tipo-acto-emitido")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoActoEmitidoController {
    private final TipoActoEmitidoService tipoActoEmitidoService;

    @GetMapping
    @Operation(summary = "Listar tipo acto emitido")
    public ResponseEntity<List<TipoActoEmitidoDTO>> listarTipoActoEmitido() {
        return ResponseEntity.ok(tipoActoEmitidoService.obtenerTipoActoEmitidos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo acto emitido por id")
    public ResponseEntity<TipoActoEmitidoDTO> obtenerTipoActoEmitido(@PathVariable Long id) {
        return ResponseEntity.ok(tipoActoEmitidoService.obtenerTipoActoEmitido(id));
    }
}
