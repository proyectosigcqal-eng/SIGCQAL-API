package com.sigcqal.api.web.ModuloCorrespondencia.AcuseOficio.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sigcqal.api.application.ModuloCorrespondencia.AcuseOficio.AcuseOficioService;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseOficio.Dto.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/acuse-oficio")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class AcuseOficioController {

    private final AcuseOficioService service;

    @PostMapping("/crear")
    public ResponseEntity<AcuseOficioResponseDTO> crear(@RequestBody AcuseOficioRequestDTO request) {
        return ResponseEntity.ok(service.crearAcuseAutomatico(request));
    }

    @GetMapping("/area/{idArea}")
    public ResponseEntity<List<AcuseOficioResponseDTO>> listarPorArea(@PathVariable Long idArea) {
        return ResponseEntity.ok(service.listarPorArea(idArea));
    }
}