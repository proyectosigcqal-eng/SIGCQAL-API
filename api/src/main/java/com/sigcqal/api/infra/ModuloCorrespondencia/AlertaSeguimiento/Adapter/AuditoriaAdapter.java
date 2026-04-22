package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Adapter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.AuditoriaPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.AuditoriaCorrespondenciaAlertaEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Adapter.AuditoriaCorrespondenciaAlertaJpaRepository;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository.CatEstatusQueryJpaRepository;

@Component
public class AuditoriaAdapter implements AuditoriaPort {
    private final AuditoriaCorrespondenciaAlertaJpaRepository auditoriaRepository;
    private final CatEstatusQueryJpaRepository catEstatusQueryJpaRepository;

    public AuditoriaAdapter(AuditoriaCorrespondenciaAlertaJpaRepository auditoriaRepository, CatEstatusQueryJpaRepository catEstatusQueryJpaRepository) {
        this.auditoriaRepository = auditoriaRepository;
        this.catEstatusQueryJpaRepository = catEstatusQueryJpaRepository;
    }

    @Override
    public void registrarEvento(Long idFolio, Long idUsuarioAccion, String estadoAnterior, String estadoNuevo, String observaciones) {
        Long idEstado = catEstatusQueryJpaRepository.findByNombreEstatusIgnoreCase(estadoAnterior)
                .map(e -> e.getIdEstatus())
                .orElse(null);

        AuditoriaCorrespondenciaAlertaEntity entity = new AuditoriaCorrespondenciaAlertaEntity();
        entity.setIdFolio(idFolio);
        entity.setIdUsuarioAccion(idUsuarioAccion);
        entity.setIdEstadoAnterior(idEstado);
        entity.setIdEstadoNuevo(idEstado);
        entity.setObservaciones(observaciones);
        entity.setFechaMovimiento(LocalDateTime.now());
        auditoriaRepository.save(entity);
    }
}

