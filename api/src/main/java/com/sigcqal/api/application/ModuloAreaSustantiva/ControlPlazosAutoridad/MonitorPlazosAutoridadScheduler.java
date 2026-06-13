package com.sigcqal.api.application.ModuloAreaSustantiva.ControlPlazosAutoridad;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonitorPlazosAutoridadScheduler {

    private final ControlPlazosAutoridadService service;

    @Scheduled(cron = "0 0 7 * * MON-FRI", zone = "America/Mexico_City")
    public void recalcularPlazos() {
        try {
            service.recalcularSemaforosVencidos();
        } catch (Exception ex) {
            log.error("[PlazoAutoridad] Error en el proceso programado", ex);
        }
    }
}

