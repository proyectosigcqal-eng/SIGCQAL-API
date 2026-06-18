package com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Model.NotificacionCierreyAcuerdodeRazon;


public interface NotificacionCierreyAcuerdodeRazonRepositoryPort {
    NotificacionCierreyAcuerdodeRazon save(NotificacionCierreyAcuerdodeRazon cierre);
    // Mantenemos esto tal cual lo tenías, ya que es el contrato original
    List<NotificacionCierreyAcuerdodeRazon> findById(Integer idExpediente);
}
