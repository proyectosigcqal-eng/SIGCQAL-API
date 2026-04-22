package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Adapter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AlertaSeguimiento;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.AlertaSeguimientoRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.AlertaSeguimientoEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Mapper.AlertaSeguimientoMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository.AlertaSeguimientoJpaRepository;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository.CorrespondenciaTurnoQueryJpaRepository;

@Component
public class AlertaSeguimientoRepositoryAdapter implements AlertaSeguimientoRepositoryPort {
    private final AlertaSeguimientoJpaRepository jpaRepository;
    private final CorrespondenciaTurnoQueryJpaRepository correspondenciaTurnoQueryJpaRepository;
    private final AlertaSeguimientoMapper mapper;

    public AlertaSeguimientoRepositoryAdapter(
            AlertaSeguimientoJpaRepository jpaRepository,
            CorrespondenciaTurnoQueryJpaRepository correspondenciaTurnoQueryJpaRepository,
            AlertaSeguimientoMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.correspondenciaTurnoQueryJpaRepository = correspondenciaTurnoQueryJpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AlertaSeguimiento save(AlertaSeguimiento alerta) {
        Long idTurno = correspondenciaTurnoQueryJpaRepository
                .findTopByIdFolioOrderByIdTurnoDesc(alerta.getIdFolio())
                .map(t -> t.getIdTurno())
                .orElseThrow();

        AlertaSeguimientoEntity entity = mapper.toEntity(alerta);
        entity.setIdTurno(idTurno);
        if (entity.getFechaAlerta() == null) {
            entity.setFechaAlerta(LocalDateTime.now());
        }

        AlertaSeguimientoEntity saved = jpaRepository.save(entity);
        AlertaSeguimiento domain = mapper.toDomain(saved);
        domain.setDiasAtraso(alerta.getDiasAtraso());
        return domain;
    }

    @Override
    public List<AlertaSeguimiento> findByIdFolio(Long idFolio) {
        return jpaRepository.findByIdFolioOrderByFechaAlertaDesc(idFolio).stream().map(mapper::toDomain).toList();
    }
}

