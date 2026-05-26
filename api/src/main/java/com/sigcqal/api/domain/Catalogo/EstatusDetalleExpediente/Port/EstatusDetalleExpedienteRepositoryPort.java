package com.sigcqal.api.domain.Catalogo.EstatusDetalleExpediente.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.EstatusDetalleExpediente.Model.EstatusDetalleExpediente;

public interface EstatusDetalleExpedienteRepositoryPort {
    Optional<EstatusDetalleExpediente> findById(Long id);
    List<EstatusDetalleExpediente> findAll();
}
