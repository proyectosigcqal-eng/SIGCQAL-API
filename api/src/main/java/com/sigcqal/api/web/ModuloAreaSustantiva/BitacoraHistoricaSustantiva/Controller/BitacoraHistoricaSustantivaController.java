package com.sigcqal.api.web.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Controller;

import com.sigcqal.api.application.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.BitacoraHistoricaSustantivaService;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Model.BitacoraHistoricaSustantiva;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
@RequiredArgsConstructor
public class BitacoraHistoricaSustantivaController {

    private final BitacoraHistoricaSustantivaService bitacoraService;

    @GetMapping("/{idQueja}")
    public ResponseEntity<List<BitacoraHistoricaSustantiva>> obtenerHistorial(@PathVariable Integer idQueja) {
        List<BitacoraHistoricaSustantiva> historial = bitacoraService.obtenerHistorial(idQueja);
        return ResponseEntity.ok(historial);
    }
}