package com.sigcqal.api.application.ModuloAreaSustantiva.CierreAutomatico;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CierreAutomaticoScheduler {

    private final CierreAutomaticoService service;

    /**
     * Corre todos los días hábiles a las 00:01 AM.
     * Cron: segundo minuto hora día mes díaSemana
     * "0 1 0 * * MON-FRI" = lunes a viernes a las 00:01:00
     */
    @Scheduled(cron = "0 1 0 * * MON-FRI")
    public void ejecutarCierreAutomatico() {
        log.info("[CierreAutomatico] Iniciando verificación programada...");
        try {
            service.verificarYCerrarVencidos();
        } catch (Exception e) {
            log.error("[CierreAutomatico] Error en el proceso programado: {}",
                    e.getMessage(), e);
        }
    }

    /**
     * Endpoint manual para disparar el proceso sin esperar al cron.
     * Útil para pruebas o correcciones manuales.
     * Se expone vía controller abajo.
     */
    public void ejecutarManual() {
        log.info("[CierreAutomatico] Ejecución MANUAL iniciada...");
        service.verificarYCerrarVencidos();
    }
}
