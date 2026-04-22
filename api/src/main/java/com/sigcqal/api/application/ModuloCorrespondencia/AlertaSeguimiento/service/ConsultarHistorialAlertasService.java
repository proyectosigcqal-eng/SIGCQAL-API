package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AlertaSeguimiento;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.AlertaSeguimientoRepositoryPort;

@Service
public class ConsultarHistorialAlertasService {
    @Autowired
    private AlertaSeguimientoRepositoryPort alertaSeguimientoRepositoryPort;

    public List<AlertaSeguimiento> consultarPorFolio(Long idFolio) {
        return alertaSeguimientoRepositoryPort.findByIdFolio(idFolio);
    }
}

