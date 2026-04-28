package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.SeguimientoCorrespondencia.SeguimientoCorrespondenciaService;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Dto.SeguimientoCorrespondenciaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Dto.SeguimientoCorrespondenciaResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seguimiento-correspondencia")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SeguimientoCorrespondenciaController {

    private final SeguimientoCorrespondenciaService service;

    @PostMapping("/guardar")
    public ResponseEntity<SeguimientoCorrespondenciaResponseDTO> guardar(
            @RequestBody SeguimientoCorrespondenciaRequestDTO request) {
        return ResponseEntity.ok(service.guardar(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SeguimientoCorrespondenciaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/correspondencia/{id}")
    public ResponseEntity<List<SeguimientoCorrespondenciaResponseDTO>> listarPorCorrespondenciaId(
            @PathVariable Integer id) {
        return ResponseEntity.ok(service.listarPorCorrespondenciaId(id));
    }
}
