package com.sigcqal.api.web.Expediente.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Expediente.ExpedienteService;
import com.sigcqal.api.web.Expediente.DTO.ExpedienteRequestDTO;
import com.sigcqal.api.web.Expediente.DTO.ExpedienteResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clasificacion-juridica/expedientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExpedienteController {

    private final ExpedienteService service;

    @PostMapping
    public ResponseEntity<ExpedienteResponseDTO> guardar(@RequestBody ExpedienteRequestDTO request) {
        ExpedienteResponseDTO response = service.guardar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
