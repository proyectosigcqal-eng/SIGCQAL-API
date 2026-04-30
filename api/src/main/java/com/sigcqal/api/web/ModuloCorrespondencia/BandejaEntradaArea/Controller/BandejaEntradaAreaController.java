package com.sigcqal.api.web.ModuloCorrespondencia.BandejaEntradaArea.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.BandejaEntradaArea.BandejaEntradaAreaService;
import com.sigcqal.api.web.ModuloCorrespondencia.BandejaEntradaArea.Dto.BandejaEntradaAreaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.BandejaEntradaArea.Dto.BandejaEntradaAreaResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bandeja-entrada")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BandejaEntradaAreaController {
    private final BandejaEntradaAreaService service;

    @PostMapping
    public ResponseEntity<BandejaEntradaAreaResponseDTO> crearEntrada(@RequestBody BandejaEntradaAreaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearEntrada(request));
    }

    @GetMapping
    public ResponseEntity<List<BandejaEntradaAreaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/area/{idArea}")
    public ResponseEntity<List<BandejaEntradaAreaResponseDTO>> listarPorArea(@PathVariable Long idArea) {
        return ResponseEntity.ok(service.listarPorArea(idArea));
    }
}

