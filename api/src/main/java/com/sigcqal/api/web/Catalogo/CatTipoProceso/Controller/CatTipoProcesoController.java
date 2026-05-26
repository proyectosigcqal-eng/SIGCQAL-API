package com.sigcqal.api.web.Catalogo.CatTipoProceso.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.CatTipoProceso.CatTipoProcesoService;
import com.sigcqal.api.web.Catalogo.CatTipoProceso.Dto.CatTipoProcesoDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/cat-tipo-proceso")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CatTipoProcesoController {
    private final CatTipoProcesoService catTipoProcesoService;

    @GetMapping
    @Operation(summary = "Listar catTipoProceso")
    public ResponseEntity<List<CatTipoProcesoDTO>> listarCatTipoProcesos() {
        return ResponseEntity.ok(catTipoProcesoService.obtenerCatTipoProcesos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener catTipoProceso por id")
    public ResponseEntity<CatTipoProcesoDTO> obtenerCatTipoProceso(@PathVariable Long id) {
        return ResponseEntity.ok(catTipoProcesoService.obtenerCatTipoProceso(id));
    }
}
