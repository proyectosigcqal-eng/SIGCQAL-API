package com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionMemorandum.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.ReasignacionMemorandum.ReasignacionMemorandumService;
import com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionMemorandum.Dto.ReasignacionMemorandumResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/correspondencia/reasignacion")
@RequiredArgsConstructor
public class ReasignacionMemorandumController {

    private final ReasignacionMemorandumService service;

    @GetMapping("/pendientes")
    public ResponseEntity<List<ReasignacionMemorandumResponseDTO>> pendientes() {
        return ResponseEntity.ok(service.pendientes());
    }
}

