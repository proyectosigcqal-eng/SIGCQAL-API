package com.sigcqal.api.web.ModuloCorrespondencia.AcuseCorrespondencia.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.ModuloCorrespondencia.AcuseCorrespondencia.AcuseCorrespondenciaService;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseCorrespondencia.Dto.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/acuse-correspondencia")
@RequiredArgsConstructor
public class AcuseCorrespondenciaController {

    private final AcuseCorrespondenciaService service;

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody AcuseCorrespondenciaRequestDTO request) {
        service.crear(request);
        return ResponseEntity.ok().build();
    }

    
    @GetMapping("/area/{idArea}")
    public ResponseEntity<List<AcuseCorrespondenciaResponseDTO>> listarPorArea(@PathVariable Long idArea) {
        return ResponseEntity.ok(service.listarPorArea(idArea));
    }
}