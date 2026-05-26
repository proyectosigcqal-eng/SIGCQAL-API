package com.sigcqal.api.web.Catalogo.EstatusExpediente.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.EstatusExpediente.EstatusExpedienteService;
import com.sigcqal.api.web.Catalogo.EstatusExpediente.Dto.EstatusExpedienteDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/estatus-expediente")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EstatusExpedienteController {
    private final EstatusExpedienteService estatusExpedienteService;

    @GetMapping
    @Operation(summary = "Listar estatus expediente")
    public ResponseEntity<List<EstatusExpedienteDTO>> listarEstatusExpediente() {
        return ResponseEntity.ok(estatusExpedienteService.obtenerEstatusExpedientes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estatus expediente por id")
    public ResponseEntity<EstatusExpedienteDTO> obtenerEstatusExpediente(@PathVariable Long id) {
        return ResponseEntity.ok(estatusExpedienteService.obtenerEstatusExpediente(id));
    }
}
