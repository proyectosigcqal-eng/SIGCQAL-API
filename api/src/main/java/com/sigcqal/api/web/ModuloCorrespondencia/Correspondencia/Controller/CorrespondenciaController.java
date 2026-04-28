package com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import com.sigcqal.api.application.ModuloCorrespondencia.Correspondencia.RegistrarCorrespondenciaService;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.RegistrarCorrespondenciaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.RegistrarCorrespondenciaResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/correspondencias")
public class CorrespondenciaController {
    @Autowired
    private RegistrarCorrespondenciaService service;

    @PostMapping("/entrada")
    public ResponseEntity<RegistrarCorrespondenciaResponseDTO> registrarEntrada(@Valid @RequestBody RegistrarCorrespondenciaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @GetMapping("/entrada/{id}")
    public ResponseEntity<RegistrarCorrespondenciaResponseDTO> obtenerEntrada(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/entrada/area/{idArea}")
    public ResponseEntity<List<RegistrarCorrespondenciaResponseDTO>> obtenerPorArea(@PathVariable Long idArea) {
    return ResponseEntity.ok(service.obtenerPorArea(idArea));
}
}
