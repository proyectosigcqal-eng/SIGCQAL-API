package com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.NotificacionSentenciaCumplida.GuardarNotificacionSentenciaCumplidaUseCase;
import com.sigcqal.api.application.ModuloAreaSustantiva.NotificacionSentenciaCumplida.ObtenerNotificacionSentenciaCumplidaUseCase;
import com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Dto.NotificacionSentenciaCumplidaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Dto.NotificacionSentenciaCumplidaResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sustantiva/notificacion-sentencia-cumplida")
@RequiredArgsConstructor
public class NotificacionSentenciaCumplidaController {

    private final GuardarNotificacionSentenciaCumplidaUseCase guardarUseCase;
    private final ObtenerNotificacionSentenciaCumplidaUseCase obtenerUseCase;

    @PostMapping
    public ResponseEntity<NotificacionSentenciaCumplidaResponseDTO> guardar(
            @Valid @RequestBody NotificacionSentenciaCumplidaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guardarUseCase.ejecutar(request));
    }

    @GetMapping
    public ResponseEntity<List<NotificacionSentenciaCumplidaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(obtenerUseCase.listarTodos());
    }
}
