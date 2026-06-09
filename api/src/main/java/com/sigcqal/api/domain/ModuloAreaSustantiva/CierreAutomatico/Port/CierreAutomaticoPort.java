package com.sigcqal.api.domain.ModuloAreaSustantiva.CierreAutomatico.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloAreaSustantiva.CierreAutomatico.Model.CierreAutomatico;

public interface CierreAutomaticoPort {
    List<CierreAutomatico> findExpedientesEnPrevencion();
    void cerrarExpedienteNoPresentado(Integer idExpediente);
}
