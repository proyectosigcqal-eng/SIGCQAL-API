package com.sigcqal.api.web.ModuloAreaSustantiva.DetalleAsesoria.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.ModuloAreaSustantiva.DetalleAsesoria.DetalleAsesoriaService;
import com.sigcqal.api.web.ModuloAreaSustantiva.DetalleAsesoria.Dto.DetalleAsesoriaResponseDTO;


@RestController
@RequestMapping("/api/v1/expedientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DetalleAsesoriaController {

    private final DetalleAsesoriaService service;

    @GetMapping("/{folio}/detalle-asesoria")
    public ResponseEntity<DetalleAsesoriaResponseDTO> obtenerDetalle(
            @PathVariable String folio) {
        return ResponseEntity.ok(service.obtenerDetalle(folio));
    }
}