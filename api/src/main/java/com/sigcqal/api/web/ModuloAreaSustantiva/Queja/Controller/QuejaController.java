package com.sigcqal.api.web.ModuloAreaSustantiva.Queja.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.Queja.QuejaService;
import com.sigcqal.api.web.ModuloAreaSustantiva.Queja.DTO.QuejaResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/quejas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuejaController {

    private final QuejaService service;

    @GetMapping("/{idQueja}")
    public ResponseEntity<QuejaResponseDTO> obtenerPorId(@PathVariable Integer idQueja) {
        return ResponseEntity.ok(service.obtenerPorId(idQueja));
    }

    @GetMapping
    public ResponseEntity<List<QuejaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
}