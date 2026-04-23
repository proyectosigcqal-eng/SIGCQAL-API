package com.sigcqal.api.web.Catalogo.Usuario.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Usuario.UsuarioService;
import com.sigcqal.api.web.Catalogo.Usuario.Dto.UsuarioDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuario(id));
    }
}
