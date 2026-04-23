package com.sigcqal.api.web.Catalogo.Area.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Area.AreaService;
import com.sigcqal.api.web.Catalogo.Area.Dto.AreaDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/areas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class AreaController {
    private final AreaService areaService;

    @GetMapping
    @Operation(summary = "Listar áreas")
    public ResponseEntity<List<AreaDTO>> listarAreas() {
        return ResponseEntity.ok(areaService.obtenerAreas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener área por id")
    public ResponseEntity<AreaDTO> obtenerArea(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.obtenerArea(id));
    }
}
