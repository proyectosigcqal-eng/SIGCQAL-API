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

    @GetMapping("/bandeja")
    public ResponseEntity<List<BandejaIrlResponseDTO>> bandeja(
            @RequestParam(name = "es_evolucion") Boolean esEvolucion,
            @RequestParam(required = false) String search,

            @RequestParam(required = false) Integer id_estatus,
            @RequestParam(required = false) Integer id_asesor) {
        return ResponseEntity.ok(service.obtenerBandeja(esEvolucion, search, id_estatus, id_asesor));

    }
}