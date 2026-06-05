package com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.ConstanciaInternaRemision.ConstanciaInternaRemisionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/expedientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConstanciaInternaRemisionController {

    private final ConstanciaInternaRemisionService service;

    @PostMapping("/{folio}/constancia-interna-remision-pdf")
    public ResponseEntity<byte[]> generar(@PathVariable String folio) {
        var result = service.generar(folio);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + result.nombreArchivo() + "\"")
            .body(result.pdf());
    }

    @GetMapping("/{folio}/constancia-pdf")
    public ResponseEntity<byte[]> obtenerConstancia(@PathVariable String folio) {
        var resultOpt = service.obtenerExistente(folio);
        if (resultOpt.isEmpty()) return ResponseEntity.notFound().build();
        var result = resultOpt.get();
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + result.nombreArchivo() + "\"")
            .body(result.pdf());
    }
}
