package com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.sigcqal.api.application.ModuloCorrespondencia.Correspondencia.RegistrarCorrespondenciaService;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.ActualizarTipoCorrespondenciaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.AsignarAreaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.RegistrarCorrespondenciaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.RegistrarCorrespondenciaResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/correspondencias")
@CrossOrigin(origins = "*")
public class CorrespondenciaController {

    @Autowired
    private RegistrarCorrespondenciaService service;

    @PostMapping("/entrada")
    public ResponseEntity<RegistrarCorrespondenciaResponseDTO> registrarEntrada(
            @Valid @RequestBody RegistrarCorrespondenciaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @GetMapping("/entrada")
    public ResponseEntity<List<RegistrarCorrespondenciaResponseDTO>> listarEntradas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // ✅ RUTAS ESPECÍFICAS PRIMERO (antes del {id})
    @GetMapping("/entrada/pendiente-revision")
    public ResponseEntity<List<RegistrarCorrespondenciaResponseDTO>> listarPendientesRevision() {
        return ResponseEntity.ok(service.listarPendientesDeRevision());
    }

    @GetMapping("/entrada/tipo/{tipo}")
    public ResponseEntity<List<RegistrarCorrespondenciaResponseDTO>> listarPorTipo(
            @PathVariable String tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    @GetMapping("/entrada/pendienteacuse/area/{idArea}")
    public ResponseEntity<List<RegistrarCorrespondenciaResponseDTO>> obtenerPorArea(
            @PathVariable Long idArea) {
        return ResponseEntity.ok(service.obtenerPorArea(idArea));
    }

    // ✅ RUTAS CON {id} AL FINAL
    @GetMapping("/entrada/{id}")
    public ResponseEntity<RegistrarCorrespondenciaResponseDTO> obtenerEntrada(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PatchMapping("/entrada/{id}")
    public ResponseEntity<RegistrarCorrespondenciaResponseDTO> asignarArea(
            @PathVariable Long id,
            @Valid @RequestBody AsignarAreaRequestDTO request) {
        return ResponseEntity.ok(service.asignarArea(id, request.getIdArea()));
    }

    @PatchMapping("/entrada/{id}/tipo-correspondencia")
    public ResponseEntity<RegistrarCorrespondenciaResponseDTO> actualizarTipoCorrespondencia(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarTipoCorrespondenciaRequestDTO request) {
        return ResponseEntity.ok(service.actualizarTipoCorrespondencia(id, request.getIdTipoCorrespondencia()));
    }
}