package com.sigcqal.api.web.Catalogo.EstatusRepresentacionLegal.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.EstatusRepresentacionLegal.EstatusRepresentacionLegalService;
import com.sigcqal.api.web.Catalogo.EstatusRepresentacionLegal.Dto.EstatusRepresentacionLegalDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/catalogos/estatus-representacion-legal")
@CrossOrigin(origins = "*")
@Tag(name = "Catálogo Estatus Representación Legal", description = "Endpoints para el catálogo de estatus de representación legal")
public class EstatusRepresentacionLegalController {

    private final EstatusRepresentacionLegalService service;

    public EstatusRepresentacionLegalController(EstatusRepresentacionLegalService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar estatus representación legal")
    public ResponseEntity<List<EstatusRepresentacionLegalDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}
