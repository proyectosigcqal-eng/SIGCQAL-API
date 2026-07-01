package com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.Expediente.ContribuyenteBusquedaService;
import com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO.ContribuyenteBusquedaDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contribuyentes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContribuyenteBusquedaController {

    private final ContribuyenteBusquedaService service;

    // GET /api/v1/contribuyentes/buscar?texto=juan
    @GetMapping("/buscar")
    public ResponseEntity<List<ContribuyenteBusquedaDTO>> buscar(
            @RequestParam("texto") String texto) {
        return ResponseEntity.ok(service.buscar(texto));
    }
}