package com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AlertaSeguimiento;

public interface AlertaSeguimientoRepositoryPort {
    AlertaSeguimiento save(AlertaSeguimiento alerta);

    List<AlertaSeguimiento> findByIdFolio(Long idFolio);
}

