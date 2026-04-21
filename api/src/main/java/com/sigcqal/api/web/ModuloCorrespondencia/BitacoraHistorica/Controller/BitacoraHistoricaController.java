package com.sigcqal.api.web.ModuloCorrespondencia.BitacoraHistorica.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.BitacoraHistorica.BitacoraHistoricaService;
import com.sigcqal.api.web.ModuloCorrespondencia.BitacoraHistorica.Dto.BitacoraHistoricaResponseDTO;


    @RestController
@RequestMapping("/api/v1/correspondencia/bitacora")
@CrossOrigin(origins = "*") 
public class BitacoraHistoricaController {

    @Autowired
    private BitacoraHistoricaService bitacoraService;

    @GetMapping("/{idCorrespondencia}")
    public ResponseEntity<List<BitacoraHistoricaResponseDTO>> consultarBitacora(
            @PathVariable Long idCorrespondencia) {
        
        List<BitacoraHistoricaResponseDTO> historial = bitacoraService.obtenerLineaDeTiempo(idCorrespondencia);
        
        if (historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(historial);
    }
}
