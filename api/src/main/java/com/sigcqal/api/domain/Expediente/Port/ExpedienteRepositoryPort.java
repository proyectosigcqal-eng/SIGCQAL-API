package com.sigcqal.api.domain.Expediente.Port;

import com.sigcqal.api.domain.Expediente.Model.Expediente;

public interface ExpedienteRepositoryPort {
    Expediente save(Expediente expediente);
}
