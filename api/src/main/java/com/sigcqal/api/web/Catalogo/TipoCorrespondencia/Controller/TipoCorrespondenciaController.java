package com.sigcqal.api.web.Catalogo.TipoCorrespondencia.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.TipoCorrespondencia.TipoCorrespondenciaService;
import com.sigcqal.api.web.Catalogo.TipoCorrespondencia.Dto.TipoCorrespondenciaDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/tipos-correspondencia")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoCorrespondenciaController {
    private final TipoCorrespondenciaService service;

    @GetMapping
    @Operation(summary = "Listar tipos de correspondencia")
    public ResponseEntity<List<TipoCorrespondenciaDTO>> listarTipos() {
        return ResponseEntity.ok(service.obtenerTipos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo de correspondencia por id")
    public ResponseEntity<TipoCorrespondenciaDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
