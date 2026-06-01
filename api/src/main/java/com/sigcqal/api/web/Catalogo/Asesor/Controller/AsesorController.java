package com.sigcqal.api.web.Catalogo.Asesor.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Asesor.AsesorService;
import com.sigcqal.api.web.Catalogo.Asesor.Dto.AsesorDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/asesores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AsesorController {
    private final AsesorService asesorService;

    @GetMapping
    @Operation(summary = "Listar asesores")
    public ResponseEntity<List<AsesorDTO>> listarAsesores() {
        return ResponseEntity.ok(asesorService.obtenerAsesores());
    }
}
