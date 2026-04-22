package com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port;

import java.time.LocalDate;
import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AreaResponsable;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso;

public interface CorrespondenciaQueryPort {
    List<FolioConRetraso> findFoliosConRetraso(String estatusFiltro, Long idArea, LocalDate fechaInicio, LocalDate fechaFin, String numFolio, String dependencia);

    FolioConRetraso findDetalleFolioById(Long idFolio);

    String obtenerEstatusActualFolio(Long idFolio);

    AreaResponsable obtenerAreaResponsable(Long idFolio);
}

