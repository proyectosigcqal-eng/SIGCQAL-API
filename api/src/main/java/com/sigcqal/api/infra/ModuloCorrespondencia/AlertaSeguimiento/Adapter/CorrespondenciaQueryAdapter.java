package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Adapter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AreaResponsable;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.CorrespondenciaQueryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository.AreaRefJpaRepository;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository.CorrespondenciaQueryJpaRepository;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository.CorrespondenciaTurnoQueryJpaRepository;

@Component
public class CorrespondenciaQueryAdapter implements CorrespondenciaQueryPort {
    private final CorrespondenciaQueryJpaRepository correspondenciaQueryJpaRepository;
    private final CorrespondenciaTurnoQueryJpaRepository correspondenciaTurnoQueryJpaRepository;
    private final AreaRefJpaRepository areaRefJpaRepository;

    public CorrespondenciaQueryAdapter(
            CorrespondenciaQueryJpaRepository correspondenciaQueryJpaRepository,
            CorrespondenciaTurnoQueryJpaRepository correspondenciaTurnoQueryJpaRepository,
            AreaRefJpaRepository areaRefJpaRepository
    ) {
        this.correspondenciaQueryJpaRepository = correspondenciaQueryJpaRepository;
        this.correspondenciaTurnoQueryJpaRepository = correspondenciaTurnoQueryJpaRepository;
        this.areaRefJpaRepository = areaRefJpaRepository;
    }

    @Override
    public List<FolioConRetraso> findFoliosConRetraso(String estatusFiltro, Long idArea, LocalDate fechaInicio, LocalDate fechaFin, String numFolio, String dependencia) {
        return correspondenciaQueryJpaRepository.findFoliosConRetraso(estatusFiltro, idArea, fechaInicio, fechaFin, numFolio, dependencia);
    }

    @Override
    public FolioConRetraso findDetalleFolioById(Long idFolio) {
        return correspondenciaQueryJpaRepository.findDetalleFolio(idFolio).orElse(null);
    }

    @Override
    public String obtenerEstatusActualFolio(Long idFolio) {
        return correspondenciaQueryJpaRepository.obtenerEstatusActualFolio(idFolio).orElse(null);
    }

    @Override
    public AreaResponsable obtenerAreaResponsable(Long idFolio) {
        return correspondenciaTurnoQueryJpaRepository.findTopByIdFolioOrderByIdTurnoDesc(idFolio)
                .map(turno -> {
                    String nombre = turno.getAreaDestino() != null ? turno.getAreaDestino().getNombre() : null;
                    if (nombre == null && turno.getIdAreaDestino() != null) {
                        nombre = areaRefJpaRepository.findById(turno.getIdAreaDestino()).map(a -> a.getNombre()).orElse(null);
                    }
                    return new AreaResponsable(turno.getIdAreaDestino(), nombre, turno.getIdUsuarioResponsable());
                })
                .orElse(null);
    }
}

