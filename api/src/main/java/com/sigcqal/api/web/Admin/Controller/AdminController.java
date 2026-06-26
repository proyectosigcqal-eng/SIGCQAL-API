package com.sigcqal.api.web.Admin.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.Catalogo.Asesor.AsesorService;
import com.sigcqal.api.application.Catalogo.Usuario.UsuarioService;
import com.sigcqal.api.web.Admin.Dto.AsesorAdminRequestDTO;
import com.sigcqal.api.web.Admin.Dto.ActualizarRolesRequestDTO;
import com.sigcqal.api.web.Admin.Dto.UsuarioAdminRequestDTO;
import com.sigcqal.api.web.Catalogo.Asesor.Dto.AsesorDTO;
import com.sigcqal.api.web.Catalogo.Usuario.Dto.UsuarioDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final UsuarioService usuarioService;
    private final AsesorService  asesorService;

    // ── USUARIOS ──────────────────────────────────────────────────────────

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> crearUsuario(
            @RequestBody UsuarioAdminRequestDTO request) {
        return ResponseEntity.ok(usuarioService.crearUsuario(request));
    }

    @PutMapping("/usuarios/{id}/roles")
    public ResponseEntity<Void> actualizarRoles(
            @PathVariable Long id,
            @RequestBody ActualizarRolesRequestDTO request) {
        usuarioService.actualizarRoles(id, request.getIdRoles());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/usuarios/{id}/baja")
    public ResponseEntity<Void> darBajaUsuario(@PathVariable Long id) {
        usuarioService.darBajaUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // ── ASESORES ──────────────────────────────────────────────────────────

    @PostMapping("/asesores")
    public ResponseEntity<AsesorDTO> crearAsesor(
            @RequestBody AsesorAdminRequestDTO request) {
        return ResponseEntity.ok(asesorService.crearAsesor(request));
    }

    @PutMapping("/asesores/{id}")
    public ResponseEntity<AsesorDTO> actualizarAsesor(
            @PathVariable Long id,
            @RequestBody AsesorAdminRequestDTO request) {
        return ResponseEntity.ok(asesorService.actualizarAsesor(id, request));
    }

    @PatchMapping("/asesores/{id}/baja")
    public ResponseEntity<Void> darBajaAsesor(@PathVariable Long id) {
        asesorService.darBajaAsesor(id);
        return ResponseEntity.noContent().build();
    }

    // AdminController.java — agrega estos GET
@GetMapping("/asesores")
public ResponseEntity<List<AsesorDTO>> listarTodosAsesores() {
    return ResponseEntity.ok(asesorService.obtenerTodos()); // sin filtro activo
}

@GetMapping("/usuarios")  
public ResponseEntity<List<UsuarioDTO>> listarTodosUsuarios() {
    return ResponseEntity.ok(usuarioService.obtenerTodos()); // sin filtro activo
}
}