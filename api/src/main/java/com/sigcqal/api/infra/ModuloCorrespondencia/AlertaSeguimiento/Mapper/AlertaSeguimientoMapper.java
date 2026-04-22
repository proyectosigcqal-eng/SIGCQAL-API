package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AlertaSeguimiento;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.AlertaSeguimientoEntity;

@Component
public class AlertaSeguimientoMapper {
    public AlertaSeguimientoEntity toEntity(AlertaSeguimiento domain) {
        if (domain == null) return null;

        AlertaSeguimientoEntity entity = new AlertaSeguimientoEntity();
        entity.setIdAlerta(domain.getIdAlerta());
        entity.setIdUsuarioEmisor(domain.getIdUsuarioEmisor());
        entity.setMensajeUrgencia(domain.getMensajeUrgencia());
        entity.setFechaAlerta(domain.getFechaEnvio());
        entity.setLeida(Boolean.FALSE);
        return entity;
    }

    public AlertaSeguimiento toDomain(AlertaSeguimientoEntity entity) {
        if (entity == null) return null;

        AlertaSeguimiento domain = new AlertaSeguimiento();
        domain.setIdAlerta(entity.getIdAlerta());
        domain.setIdUsuarioEmisor(entity.getIdUsuarioEmisor());
        domain.setMensajeUrgencia(entity.getMensajeUrgencia());
        domain.setFechaEnvio(entity.getFechaAlerta());

        if (entity.getTurno() != null) {
            domain.setIdFolio(entity.getTurno().getIdFolio());
            domain.setIdAreaDestinataria(entity.getTurno().getIdAreaDestino());

            if (entity.getTurno().getAreaDestino() != null) {
                domain.setNombreAreaDestinataria(entity.getTurno().getAreaDestino().getNombre());
            }
            if (entity.getTurno().getCorrespondencia() != null) {
                domain.setFolioUnico(entity.getTurno().getCorrespondencia().getFolioUnico());
            }
        }

        if (entity.getUsuarioEmisor() != null) {
            domain.setNombreUsuarioEmisor(entity.getUsuarioEmisor().getUsuarioLogin());
        }
        return domain;
    }
}

