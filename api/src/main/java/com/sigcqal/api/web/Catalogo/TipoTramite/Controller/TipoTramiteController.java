package com.sigcqal.api.web.Catalogo.TipoTramite.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.TipoTramite.TipoTramiteService;
import com.sigcqal.api.web.Catalogo.TipoTramite.Dto.TipoTramiteDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/tipo-tramite")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoTramiteController {
    private final TipoTramiteService tipoTramiteService;

    @GetMapping
    @Operation(summary = "Listar tipo tramite")
    public ResponseEntity<List<TipoTramiteDTO>> listarTipoTramite() {
        return ResponseEntity.ok(tipoTramiteService.obtenerTipoTramites());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo tramite por id")
    public ResponseEntity<TipoTramiteDTO> obtenerTipoTramite(@PathVariable Long id) {
        return ResponseEntity.ok(tipoTramiteService.obtenerTipoTramite(id));
    }
}
