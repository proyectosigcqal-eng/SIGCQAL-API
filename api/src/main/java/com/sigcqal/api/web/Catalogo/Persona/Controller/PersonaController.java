package com.sigcqal.api.web.Catalogo.Persona.Controller;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.Catalogo.Persona.PersonaService;
import com.sigcqal.api.web.Catalogo.Persona.Dto.PersonaDTO;
import com.sigcqal.api.web.Catalogo.Persona.Dto.PersonaRequestDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/catalogo/personas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PersonaController {

    private final PersonaService service;

    @PostMapping
    public ResponseEntity<PersonaDTO> guardar(@RequestBody PersonaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PersonaDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> actualizar(
            @PathVariable Long id,
            @RequestBody PersonaRequestDTO request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

}