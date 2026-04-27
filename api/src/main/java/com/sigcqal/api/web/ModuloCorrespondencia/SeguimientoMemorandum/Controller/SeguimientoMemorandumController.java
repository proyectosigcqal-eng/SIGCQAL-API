package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.SeguimientoMemorandum.SeguimientoMemorandumService;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto.SeguimientoMemorandumRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto.SeguimientoMemorandumResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seguimiento-memorandum")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SeguimientoMemorandumController {

    private final SeguimientoMemorandumService service;

    @PostMapping("/guardar")
    public ResponseEntity<SeguimientoMemorandumResponseDTO> guardar(
            @RequestBody SeguimientoMemorandumRequestDTO request) {
        return ResponseEntity.ok(service.guardar(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SeguimientoMemorandumResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/memorandum/{idMemo}")
    public ResponseEntity<List<SeguimientoMemorandumResponseDTO>> listarPorMemorandum(
            @PathVariable Long idMemo) {
        return ResponseEntity.ok(service.listarPorMemorandumId(idMemo));
    }
}
