package com.sigcqal.api.web.Catalogo.Contribuyente.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.Catalogo.Contribuyente.ContribuyenteService;
import com.sigcqal.api.web.Catalogo.Contribuyente.Dto.ContribuyenteDto;
import com.sigcqal.api.web.Catalogo.Contribuyente.Dto.ContribuyenteRequestDto;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("catalogos/contribuyentes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor 
public class ContribuyenteController {

    private final ContribuyenteService service;

    @PostMapping
    public ResponseEntity<ContribuyenteDto> guardar(
            @RequestBody ContribuyenteRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContribuyenteDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ContribuyenteDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContribuyenteDto> actualizar(
            @PathVariable Long id,
            @RequestBody ContribuyenteRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }
}