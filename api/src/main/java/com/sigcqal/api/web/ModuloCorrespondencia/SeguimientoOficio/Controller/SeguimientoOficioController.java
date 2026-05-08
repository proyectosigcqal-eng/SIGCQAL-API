package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoOficio.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.ModuloCorrespondencia.SeguimientoOficio.SeguimientoOficioService;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoOficio.Dto.SeguimientoOficioRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoOficio.Dto.SeguimientoOficioResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seguimiento-oficio")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SeguimientoOficioController {

    private final SeguimientoOficioService service;

    @PostMapping(value = "/guardar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SeguimientoOficioResponseDTO> guardar(
            @ModelAttribute SeguimientoOficioRequestDTO request) {
        return ResponseEntity.ok(service.guardar(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SeguimientoOficioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/oficio/{id}")
    public ResponseEntity<List<SeguimientoOficioResponseDTO>> listarPorOficioId(
            @PathVariable Integer id) {
        return ResponseEntity.ok(service.listarPorOficioId(id));
    }
}