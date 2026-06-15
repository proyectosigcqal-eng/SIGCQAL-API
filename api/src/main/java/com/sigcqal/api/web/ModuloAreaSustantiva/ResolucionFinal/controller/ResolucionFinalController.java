package com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.ResolucionFinal.ResolucionFinalService;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/resolucion-final")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResolucionFinalController {
    private final ResolucionFinalService service;

    @GetMapping("/expediente/{idExpediente}")
    public ResponseEntity<ResolucionFinalResponseDTO> buscarPorExpediente(
        @PathVariable Integer idExpediente) {

            ResolucionFinalResponseDTO response = service.buscarPorExpediente(idExpediente);
            return ResponseEntity.ok(response);
        }

        @PostMapping
public ResponseEntity<ResolucionFinalResponseDTO> emitirResolucion(
        @RequestBody ResolucionFinalRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.emitirResolucion(request));
}
    }
