package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AreaResponsable;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.CorrespondenciaQueryPort;

@Service
public class ConsultarDetalleFolioService {
    @Autowired
    private CorrespondenciaQueryPort correspondenciaQueryPort;

    public FolioConRetraso consultarDetalle(Long idFolio) {
        FolioConRetraso detalle = correspondenciaQueryPort.findDetalleFolioById(idFolio);
        if (detalle == null) {
            throw new ResourceNotFoundException("Folio", idFolio);
        }
        AreaResponsable area = correspondenciaQueryPort.obtenerAreaResponsable(idFolio);
        String estatusActual = correspondenciaQueryPort.obtenerEstatusActualFolio(idFolio);

        detalle.setIdAreaResponsable(area != null ? area.getIdArea() : null);
        detalle.setNombreAreaResponsable(area != null ? area.getNombreArea() : null);
        detalle.setNombreEstatus(estatusActual);

        LocalDate hoy = LocalDate.now();
        if (detalle.getFechaRegistro() == null) {
            detalle.setDiasAtraso(0L);
        } else {
            detalle.setDiasAtraso(ChronoUnit.DAYS.between(detalle.getFechaRegistro(), hoy));
        }
        return detalle;
    }
}
