package com.sigcqal.api.web.ModuloAreaSustantiva.CierreAutomatico;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sigcqal.api.application.ModuloAreaSustantiva.CierreAutomatico.CierreAutomaticoScheduler;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CierreAutomaticoController {

    private final CierreAutomaticoScheduler scheduler;

    /**
     * POST /api/v1/admin/cierre-automatico/ejecutar
     * Dispara el proceso manualmente — solo para pruebas/admin.
     */
    @PostMapping("/cierre-automatico/ejecutar")
    public ResponseEntity<String> ejecutarManual() {
        scheduler.ejecutarManual();
        return ResponseEntity.ok("Proceso de cierre automático ejecutado correctamente.");
    }
}