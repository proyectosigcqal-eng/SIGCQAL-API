package com.sigcqal.api.infra.ModuloAreaSustantiva.InformeAutoridad.Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sigcqal.api.application.ModuloAreaSustantiva.InformeAutoridad.InformeAutoridadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertaPlazoInformeAutoridadScheduler {

    private final InformeAutoridadService informeAutoridadService;

    @Scheduled(cron = "0 0 8 ? * MON-FRI", zone = "America/Mexico_City")
    public void actualizarAlertasPlazo() {
        log.info("Ejecutando actualización programada de alertas para informe de autoridad");
        informeAutoridadService.actualizarAlertasProgramadas();
    }
}

