package com.sigcqal.api.web.Catalogo.Estado.Controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.sigcqal.api.application.Catalogo.Estado.EstadoService;
import com.sigcqal.api.web.Catalogo.Estado.Dto.EstadoDTO;

@RestController
@RequestMapping("/catalogos/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listarEstados() {
        return ResponseEntity.ok(estadoService.obtenerEstados());
        
    }
}