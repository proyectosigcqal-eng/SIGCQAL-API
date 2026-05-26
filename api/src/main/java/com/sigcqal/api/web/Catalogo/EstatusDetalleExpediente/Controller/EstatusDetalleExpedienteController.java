package com.sigcqal.api.web.Catalogo.EstatusDetalleExpediente.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.EstatusDetalleExpediente.EstatusDetalleExpedienteService;
import com.sigcqal.api.web.Catalogo.EstatusDetalleExpediente.Dto.EstatusDetalleExpedienteDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/estatus-detalle-expediente")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EstatusDetalleExpedienteController {
    private final EstatusDetalleExpedienteService estatusDetalleExpedienteService;

    @GetMapping
    @Operation(summary = "Listar estatus detalle expediente")
    public ResponseEntity<List<EstatusDetalleExpedienteDTO>> listarEstatusDetalleExpediente() {
        return ResponseEntity.ok(estatusDetalleExpedienteService.obtenerEstatusDetalleExpedientes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estatus detalle expediente por id")
    public ResponseEntity<EstatusDetalleExpedienteDTO> obtenerEstatusDetalleExpediente(@PathVariable Long id) {
        return ResponseEntity.ok(estatusDetalleExpedienteService.obtenerEstatusDetalleExpediente(id));
    }
}
