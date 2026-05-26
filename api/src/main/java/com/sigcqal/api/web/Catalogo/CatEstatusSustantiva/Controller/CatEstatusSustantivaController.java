package com.sigcqal.api.web.Catalogo.CatEstatusSustantiva.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.CatEstatusSustantiva.CatEstatusSustantivaService;
import com.sigcqal.api.web.Catalogo.CatEstatusSustantiva.Dto.CatEstatusSustantivaDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/cat-estatus-sustantiva")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CatEstatusSustantivaController {
    private final CatEstatusSustantivaService catEstatusSustantivaService;

    @GetMapping
    @Operation(summary = "Listar catEstatusSustantiva")
    public ResponseEntity<List<CatEstatusSustantivaDTO>> listarCatEstatusSustantiva() {
        return ResponseEntity.ok(catEstatusSustantivaService.obtenerCatEstatusSustantiva());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener catEstatusSustantiva por id")
    public ResponseEntity<CatEstatusSustantivaDTO> obtenerCatEstatusSustantiva(@PathVariable Long id) {
        return ResponseEntity.ok(catEstatusSustantivaService.obtenerCatEstatusSustantiva(id));
    }
}
