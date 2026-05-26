package com.sigcqal.api.domain.Catalogo.EstatusExpediente.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.EstatusExpediente.Model.EstatusExpediente;

public interface EstatusExpedienteRepositoryPort {
    Optional<EstatusExpediente> findById(Long id);

    List<EstatusExpediente> findAll();
}
