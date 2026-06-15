package com.sigcqal.api.application.ModuloAreaSustantiva.ControlPlazosAutoridad;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingPlazoAutoridadNotifier implements PlazoAutoridadNotifier {

    @Override
    public void notificarVencimiento(Long expedienteId, String folioGobierno) {
        log.warn("[PlazoAutoridad] Expediente vencido. idExpediente={} folio={}", expedienteId, folioGobierno);
    }
}

