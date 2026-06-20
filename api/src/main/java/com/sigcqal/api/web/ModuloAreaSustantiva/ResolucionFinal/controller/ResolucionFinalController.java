package com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.ResolucionFinal.ResolucionFinalService;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/modulo-area-sustantiva/resolucion-final")
@RequiredArgsConstructor
public class ResolucionFinalController {

    private final ResolucionFinalService service;

    @PostMapping
    public ResponseEntity<ResolucionFinalResponseDTO> guardar(
            @Valid @RequestBody ResolucionFinalRequestDTO request) {
        ResolucionFinalResponseDTO response = service.guardar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{idResolucionFinal}")
    public ResponseEntity<ResolucionFinalResponseDTO> buscarPorId(
            @PathVariable Integer idResolucionFinal) {
        return ResponseEntity.ok(service.buscarPorId(idResolucionFinal));
    }

    @GetMapping
    public ResponseEntity<List<ResolucionFinalResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/expediente/{idExpediente}")
    public ResponseEntity<List<ResolucionFinalResponseDTO>> listarPorExpediente(
            @PathVariable Integer idExpediente) {
        return ResponseEntity.ok(service.listarPorExpediente(idExpediente));
    }

    /**
     * Genera el Acuerdo de Cierre (.docx). Los query params coinciden
     * EXACTAMENTE con los campos del formulario en
     * EditorResolucionFinal.jsx, para que el frontend pueda mandarlos
     * directo sin transformación.
     */
    @PostMapping("/{idResolucionFinal}/generar-oficio")
    public ResponseEntity<ResolucionFinalResponseDTO> generarOficio(
            @PathVariable Integer idResolucionFinal,
            @RequestParam(required = false) String folio,
            @RequestParam(required = false) String expedienteNum,
            @RequestParam(required = false) String autoridadFiscal,
            @RequestParam(required = false) String fechaSolicitud,
            @RequestParam(required = false) String nombreContribuyente,
            @RequestParam(required = false) String motivoQueja,
            @RequestParam(required = false) String oficioNumero,
            @RequestParam(required = false) String fechaOficio,
            @RequestParam(required = false) String fechaIngresoOficio,
            @RequestParam(required = false) String numeroCreditoMulta,
            @RequestParam(required = false) String contactoVia,
            @RequestParam(required = false) String iniciales) {

        ResolucionFinalResponseDTO response = service.generarOficio(
                idResolucionFinal, folio, expedienteNum, autoridadFiscal, fechaSolicitud,
                nombreContribuyente, motivoQueja, oficioNumero, fechaOficio,
                fechaIngresoOficio, numeroCreditoMulta, contactoVia, iniciales);

        return ResponseEntity.ok(response);
    }
}