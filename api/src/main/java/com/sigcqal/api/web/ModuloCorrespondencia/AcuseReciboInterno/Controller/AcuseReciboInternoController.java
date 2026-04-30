package com.sigcqal.api.web.ModuloCorrespondencia.AcuseReciboInterno.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.ModuloCorrespondencia.AcuseReciboInterno.AcuseReciboInternoService;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseReciboInterno.Dto.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/acuse-interno")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class AcuseReciboInternoController {

    private final AcuseReciboInternoService service;

    // 🔹 LISTA
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<AcuseReciboInternoResponseDTO>> listar(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
    }

    // 🔹 DETALLE
    @GetMapping("/{idAcuse}")
    public ResponseEntity<AcuseReciboInternoResponseDTO> detalle(@PathVariable Long idAcuse) {
        return ResponseEntity.ok(service.obtenerDetalle(idAcuse));
    }

    // 🔹 RESPONDER
    @PostMapping("/responder")
    public ResponseEntity<Void> responder(@RequestBody AcuseReciboInternoRequestDTO request) {
        service.responder(request);
        return ResponseEntity.ok().build();
    }

    //Por area y sean true
    @GetMapping("/area/{idArea}")
    public ResponseEntity<List<AcuseReciboInternoResponseDTO>> listarPorArea(@PathVariable Long idArea) {
    return ResponseEntity.ok(service.listarPorArea(idArea));
    }
}