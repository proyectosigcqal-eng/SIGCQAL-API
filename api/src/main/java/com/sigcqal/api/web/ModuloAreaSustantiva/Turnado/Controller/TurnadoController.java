package com.sigcqal.api.web.ModuloAreaSustantiva.Turnado.Controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sigcqal.api.application.ModuloAreaSustantiva.Turnado.TurnadoService;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Model.AsesorDisponible;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Model.ResultadoTurnado;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Port.TurnadoPort;
import com.sigcqal.api.web.ModuloAreaSustantiva.Turnado.Dto.TurnadoRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.Turnado.Dto.TurnadoResponseDTO;


@RestController
@RequestMapping("/api/v1/quejas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TurnadoController {

    private final TurnadoService service;
    private final TurnadoPort turnadoPort;

    /**
     * POST /api/v1/quejas/{folio}/turnar
     * Asigna el siguiente asesor en Round Robin al expediente.
     */
    @PostMapping("/{folio}/turnar")
    public ResponseEntity<TurnadoResponseDTO> turnar(
            @PathVariable String folio,
            @RequestBody(required = false) TurnadoRequestDTO request,
            HttpServletRequest httpRequest) {

        Integer idUsuario = request != null ? request.getIdUsuario() : null;
        String  ip        = obtenerIp(httpRequest);

        ResultadoTurnado resultado = service.turnarExpediente(folio, idUsuario, ip);

        return ResponseEntity.ok(TurnadoResponseDTO.builder()
                .folioExpediente(resultado.getFolioExpediente())
                .idAsesorAsignado(resultado.getIdAsesorAsignado())
                .nombreAsesor(resultado.getNombreAsesor())
                .fechaAsignacion(resultado.getFechaAsignacion().toString())
                .mensaje("Expediente turnado correctamente al asesor "
                        + resultado.getNombreAsesor())
                .build());
    }

    private String obtenerIp(HttpServletRequest req) {
        String forwarded = req.getHeader("X-Forwarded-For");
        return (forwarded != null && !forwarded.isBlank())
                ? forwarded.split(",")[0].trim()
                : req.getRemoteAddr();
    }

    @GetMapping("/siguiente-asesor")
public ResponseEntity<TurnadoResponseDTO> siguienteAsesor() {
    List<AsesorDisponible> asesores = turnadoPort.findAsesoresDisponibles();

    if (asesores.isEmpty()) {
        return ResponseEntity.noContent().build();
    }

    AsesorDisponible siguiente = asesores.get(0);

    return ResponseEntity.ok(TurnadoResponseDTO.builder()
            .idAsesorAsignado(siguiente.getIdAsesor())
            .nombreAsesor(siguiente.getNombreCompleto())
            .fechaAsignacion(LocalDateTime.now().toString())
            .mensaje("Próximo asesor en Round Robin")
            .build());
}
}