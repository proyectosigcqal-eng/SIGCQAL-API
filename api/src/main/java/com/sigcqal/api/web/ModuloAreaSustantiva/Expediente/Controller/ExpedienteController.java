package com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.application.ModuloAreaSustantiva.Expediente.ExpedienteService;
import com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO.ExpedienteRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO.ExpedienteResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clasificacion-juridica/expedientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExpedienteController {

    private final ExpedienteService service;

    @PostMapping
    public ResponseEntity<ExpedienteResponseDTO> guardar(@RequestBody ExpedienteRequestDTO request) {
        ExpedienteResponseDTO response = service.guardar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{folio}/documento-personalidad")
public ResponseEntity<ExpedienteResponseDTO> subirDocumento(
    @PathVariable String folio,
    @RequestParam("archivo") MultipartFile archivo
) throws IOException {
    return ResponseEntity.ok(service.guardarDocumentoPersonalidad(folio, archivo.getBytes()));
}

@GetMapping("/{folio}")
public ResponseEntity<List<ExpedienteResponseDTO>> obtenerPorFolio(@PathVariable String folio) {
    // Asumiendo que tienes este método en tu service
    List<ExpedienteResponseDTO> response = service.buscarPorFolio(folio); 
    return ResponseEntity.ok(response);
}
}
