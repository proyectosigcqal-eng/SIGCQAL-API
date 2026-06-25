package com.sigcqal.api.web.ModuloAreaSustantiva.RepresentacionLegal.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.ModuloAreaSustantiva.RepresentacionLegal.RepresentacionLegalService;
import com.sigcqal.api.web.ModuloAreaSustantiva.RepresentacionLegal.Dto.BandejaIrlResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/representacion-legal")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RepresentacionLegalController {

    private final RepresentacionLegalService service;

    /**
     * GET /api/v1/representacion-legal/bandeja
     *
     * @param esEvolucion false = IRL Asignación Directa, true = IRL Evolución
     * @param search      búsqueda por folio o nombre de contribuyente
     */
    @GetMapping("/bandeja")
    public ResponseEntity<List<BandejaIrlResponseDTO>> bandeja(
            @RequestParam(name = "es_evolucion") Boolean esEvolucion,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(service.obtenerBandeja(esEvolucion, search));
    }
}