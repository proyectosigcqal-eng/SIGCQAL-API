package com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAcci.Controller;

import com.sigcqal.api.application.ModuloAreaSustantiva.QuejasAcci.QuejasAcciService;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAcci.Dto.QuejasAcciRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAcci.Dto.QuejasAcciResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quejas-acci")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuejasAcciController {

    private final QuejasAcciService service;

    @PostMapping
    public ResponseEntity<QuejasAcciResponseDTO> guardar(
            @RequestBody QuejasAcciRequestDTO request) {
        return ResponseEntity.ok(service.guardar(request));
    }

    @GetMapping("/queja/{idQueja}")
    public ResponseEntity<List<QuejasAcciResponseDTO>> listarPorQueja(
            @PathVariable Long idQueja) {
        return ResponseEntity.ok(service.listarPorQueja(idQueja));
    }
}