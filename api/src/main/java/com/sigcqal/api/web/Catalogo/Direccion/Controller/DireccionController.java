package com.sigcqal.api.web.Catalogo.Direccion.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Direccion.DireccionService;
import com.sigcqal.api.web.Catalogo.Direccion.Dto.DireccionDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/direcciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DireccionController {
    private final DireccionService direccionService;

    @GetMapping
    @Operation(summary = "Listar direcciones")
    public ResponseEntity<List<DireccionDTO>> listarDirecciones() {
        return ResponseEntity.ok(direccionService.obtenerDirecciones());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener dirección por id")
    public ResponseEntity<DireccionDTO> obtenerDireccion(@PathVariable Long id) {
        return ResponseEntity.ok(direccionService.obtenerDireccion(id));
    }
}
