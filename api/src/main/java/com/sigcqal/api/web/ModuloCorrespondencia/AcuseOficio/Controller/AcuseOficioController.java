package com.sigcqal.api.web.ModuloCorrespondencia.AcuseOficio.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sigcqal.api.application.ModuloCorrespondencia.AcuseOficio.AcuseOficioService;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseOficio.Dto.*;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseReciboInterno.Dto.AcuseReciboInternoResponseDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoOficio.Dto.SeguimientoOficioRequestDTO;

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

    @GetMapping("/todos")
public ResponseEntity<List<AcuseOficioResponseDTO>> listarTodos() {
    return ResponseEntity.ok(service.listarTodos());
}

    @GetMapping("/area/{idArea}")
    public ResponseEntity<List<AcuseOficioResponseDTO>> listarPorArea(@PathVariable Long idArea) {
        return ResponseEntity.ok(service.listarPorArea(idArea));
    }
    @GetMapping("/{id}")
public ResponseEntity<AcuseOficioResponseDTO> obtenerPorId(@PathVariable Long id) {
    return ResponseEntity.ok(service.obtenerPorId(id));
}

@GetMapping("/oficio/{idOficio}")
public ResponseEntity<List<AcuseOficioResponseDTO>> listarPorOficio(
        @PathVariable Long idOficio) {
    return ResponseEntity.ok(service.listarPorOficio(idOficio));
}

    
}