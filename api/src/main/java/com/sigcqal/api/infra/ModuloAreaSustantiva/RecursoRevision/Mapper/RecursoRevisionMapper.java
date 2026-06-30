package com.sigcqal.api.infra.ModuloAreaSustantiva.RecursoRevision.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Model.RecursoRevision;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RecursoRevision.Entity.RecursoRevisionEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto.RecursoRevisionResponseDTO;

@Component
public class RecursoRevisionMapper {

    public RecursoRevisionEntity toEntity(RecursoRevision domain) {
        if (domain == null) {
            return null;
        }
        return RecursoRevisionEntity.builder()
                .idRecursoRevision(domain.getIdRecursoRevision())
                .idSentencia(domain.getIdSentencia())
                .numeroOficioInterposicion(domain.getNumeroOficioInterposicion())
                .numeroExpedienteRevision(domain.getNumeroExpedienteRevision())
                .tribunalColegiadoAsig(domain.getTribunalColegiadoAsig())
                .fechaInterposicion(domain.getFechaInterposicion())
                .observacionesSeguimiento(domain.getObservacionesSeguimiento())
                .rutaPdfOficio(domain.getRutaPdfOficio())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }

    public RecursoRevision toDomain(RecursoRevisionEntity entity) {
        if (entity == null) {
            return null;
        }
        return RecursoRevision.builder()
                .idRecursoRevision(entity.getIdRecursoRevision())
                .idSentencia(entity.getIdSentencia())
                .numeroOficioInterposicion(entity.getNumeroOficioInterposicion())
                .numeroExpedienteRevision(entity.getNumeroExpedienteRevision())
                .tribunalColegiadoAsig(entity.getTribunalColegiadoAsig())
                .fechaInterposicion(entity.getFechaInterposicion())
                .observacionesSeguimiento(entity.getObservacionesSeguimiento())
                .rutaPdfOficio(entity.getRutaPdfOficio())
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }

    public RecursoRevisionResponseDTO toResponse(RecursoRevision domain) {
        if (domain == null) {
            return null;
        }
        return RecursoRevisionResponseDTO.builder()
                .idRecursoRevision(domain.getIdRecursoRevision())
                .idSentencia(domain.getIdSentencia())
                .numeroOficioInterposicion(domain.getNumeroOficioInterposicion())
                .numeroExpedienteRevision(domain.getNumeroExpedienteRevision())
                .tribunalColegiadoAsig(domain.getTribunalColegiadoAsig())
                .fechaInterposicion(domain.getFechaInterposicion())
                .observacionesSeguimiento(domain.getObservacionesSeguimiento())
                .rutaPdfOficio(domain.getRutaPdfOficio())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }
}
