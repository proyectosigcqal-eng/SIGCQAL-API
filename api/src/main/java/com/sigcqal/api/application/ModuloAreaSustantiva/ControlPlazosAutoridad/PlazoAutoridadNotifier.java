package com.sigcqal.api.application.ModuloAreaSustantiva.ControlPlazosAutoridad;

public interface PlazoAutoridadNotifier {
    void notificarVencimiento(Long expedienteId, String folioGobierno);
}

