package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.CorrespondenciaQueryPort;

@Service
public class ConsultarFoliosConRetrasoService {
    @Autowired
    private CorrespondenciaQueryPort correspondenciaQueryPort;

    public List<FolioConRetraso> consultar(String estatusFiltro, Long idArea, LocalDate fechaInicio, LocalDate fechaFin, String numFolio, String dependencia) {
        List<FolioConRetraso> folios = correspondenciaQueryPort.findFoliosConRetraso(estatusFiltro, idArea, fechaInicio, fechaFin, numFolio, dependencia);

        LocalDate hoy = LocalDate.now();
        for (FolioConRetraso folio : folios) {
            if (folio.getFechaRegistro() == null) {
                folio.setDiasAtraso(0L);
            } else {
                folio.setDiasAtraso(ChronoUnit.DAYS.between(folio.getFechaRegistro(), hoy));
            }
        }

        folios.sort(Comparator.comparing(FolioConRetraso::getDiasAtraso, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        return folios;
    }
}

