package com.sigcqal.api.web.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Controller;

import com.sigcqal.api.application.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.BitacoraHistoricaSustantivaService;
import com.sigcqal.api.infra.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Mapper.BitacoraHistoricaSustantivaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Dto.BitacoraHistoricaSustantivaResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bitacora")
@RequiredArgsConstructor // <-- Esto genera el constructor automáticamente para TODOS los campos final
public class BitacoraHistoricaSustantivaController {

    private final BitacoraHistoricaSustantivaService bitacoraService;
    private final BitacoraHistoricaSustantivaMapper mapper; // <-- Agregado para solucionar 'mapper cannot be resolved'

    @GetMapping("/{folio}")
    public ResponseEntity<List<BitacoraHistoricaSustantivaResponseDto>> obtenerHistorial(@PathVariable String folio) {
        // Corregido el flujo del stream y la asignación del tipo de dato correcto (ResponseDto)
        List<BitacoraHistoricaSustantivaResponseDto> historialDto = bitacoraService.obtenerHistorialPorFolio(folio)
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(historialDto);
    }
}