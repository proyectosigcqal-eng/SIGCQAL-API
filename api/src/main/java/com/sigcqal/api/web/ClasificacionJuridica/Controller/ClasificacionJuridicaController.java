package com.sigcqal.api.web.ClasificacionJuridica.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ClasificacionJuridica.ClasificacionJuridicaService;
import com.sigcqal.api.web.ClasificacionJuridica.DTO.ClasificacionJuridicaRequestDTO;
import com.sigcqal.api.web.ClasificacionJuridica.DTO.ClasificacionJuridicaResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clasificacion-juridica")
@RequiredArgsConstructor
public class ClasificacionJuridicaController {

    private final ClasificacionJuridicaService service;

    @PostMapping
    public ResponseEntity<ClasificacionJuridicaResponseDTO> guardar(
            @RequestBody ClasificacionJuridicaRequestDTO request) {

        ClasificacionJuridicaResponseDTO response = service.guardar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/expediente/{idExpediente}")
    public ResponseEntity<List<ClasificacionJuridicaResponseDTO>> buscarPorFolio(
            @PathVariable Integer idExpediente) {

        List<ClasificacionJuridicaResponseDTO> response = service.buscarPorFolio(idExpediente);
        return ResponseEntity.ok(response);
    }
}