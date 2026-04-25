package com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.ReasignacionCorrespondencia.ReasignacionCorrespondenciaService;
import com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Dto.ReasignacionCorrespondenciaResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/correspondencia/reasignacion")
@RequiredArgsConstructor
public class ReasignacionCorrespondenciaController {

    private final ReasignacionCorrespondenciaService service;

    @GetMapping("/pendientes")
    public ResponseEntity<List<ReasignacionCorrespondenciaResponseDTO>> pendientes() {
        return ResponseEntity.ok(service.pendientes());
    }
}

