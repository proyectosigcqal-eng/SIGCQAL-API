package com.sigcqal.api.web.Catalogo.Persona.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Persona.PersonaService;
import com.sigcqal.api.web.Catalogo.Persona.Dto.PersonaDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/personas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PersonaController {
    private final PersonaService personaService;

    @GetMapping
    @Operation(summary = "Listar personas")
    public ResponseEntity<List<PersonaDTO>> listarPersonas() {
        return ResponseEntity.ok(personaService.obtenerPersonas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener persona por id")
    public ResponseEntity<PersonaDTO> obtenerPersona(@PathVariable Long id) {
        return ResponseEntity.ok(personaService.obtenerPersona(id));
    }
}
