package com.sigcqal.api.web.Catalogo.Rol.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Rol.RolService;
import com.sigcqal.api.web.Catalogo.Rol.Dto.RolDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Roles")
public class RolController {
    private final RolService rolService;

    @GetMapping
    @Operation(summary = "Listar roles")
    public ResponseEntity<List<RolDTO>> listarRoles() {
        return ResponseEntity.ok(rolService.obtenerRoles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por id")
    public ResponseEntity<RolDTO> obtenerRol(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.obtenerRol(id));
    }
}
