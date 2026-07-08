package com.sigcqal.api.web.Catalogo.Usuario.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Usuario.UsuarioService;
import com.sigcqal.api.web.Catalogo.Usuario.Dto.ActualizarUsuarioRequestDTO;
import com.sigcqal.api.web.Catalogo.Usuario.Dto.UsuarioDTO;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ── Endpoints originales ──────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuario(id));
    }

    // ── Endpoints adaptados del externo ───────────────────────────────

    /**
     * GET /catalogos/usuarios/me
     * Devuelve el usuario actualmente autenticado por su login.
     */
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioActual(Authentication authentication) {
        String login = authentication.getName();
        return ResponseEntity.ok(usuarioService.obtenerPorLogin(login));
    }

    /**
     * PUT /catalogos/usuarios/{id}
     * Actualiza los datos del usuario. Solo el propio usuario o un ADMIN puede hacerlo.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarUsuarioRequestDTO request,
            Authentication authentication) {
        String loginActual = authentication.getName();
        return ResponseEntity.ok(usuarioService.actualizar(id, request, loginActual));
    }

    /**
     * DELETE /catalogos/usuarios/{id}
     * Da de baja un usuario. Solo ADMIN puede ejecutarlo.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> darBajaUsuario(@PathVariable Long id) {
        usuarioService.darBaja(id);
        return ResponseEntity.noContent().build();
    }
}