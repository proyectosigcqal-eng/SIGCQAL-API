package com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/{idResolucionFinal}/oficio")
    public ResponseEntity<byte[]> generarOficio(@PathVariable Integer idResolucionFinal) {
        byte[] documento = service.generarOficio(idResolucionFinal);

        String nombreArchivo = "Oficio_ResolucionFinal_" + idResolucionFinal + ".docx";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                .body(documento);
    }
}