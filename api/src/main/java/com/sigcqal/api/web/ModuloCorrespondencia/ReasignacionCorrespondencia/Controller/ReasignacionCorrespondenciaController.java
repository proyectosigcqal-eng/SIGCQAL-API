package com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.ReasignacionCorrespondencia.ReasignacionCorrespondenciaService;
import com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Dto.ReasignacionCorrespondenciaResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/correspondencia")
@RequiredArgsConstructor
public class ReasignacionCorrespondenciaController {

    private final ReasignacionCorrespondenciaService service;

    @GetMapping("/reasignacion/{id}")
    public ResponseEntity<ReasignacionCorrespondenciaResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id).orElse(null));
    }

    @GetMapping("/reasignacion/area/{idArea}")
    public ResponseEntity<List<ReasignacionCorrespondenciaResponseDTO>> listarPorArea(@PathVariable Long idArea) {
        return ResponseEntity.ok(service.listarPorArea(idArea));
    }

    @GetMapping("/reasignacion/estatus/rechazado")
    public ResponseEntity<List<ReasignacionCorrespondenciaResponseDTO>> listarPorEstatusRechazadoFalse() {
        return ResponseEntity.ok(service.listarPorEstatusRechazado(false));
    }
}
