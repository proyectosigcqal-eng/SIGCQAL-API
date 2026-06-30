package com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaEjecutoria.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Model.SentenciaEjecutoria;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaEjecutoria.Entity.SentenciaEjecutoriaEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto.SentenciaEjecutoriaResponseDTO;

@Component
public class SentenciaEjecutoriaMapper {

    public SentenciaEjecutoriaEntity toEntity(SentenciaEjecutoria domain) {
        if (domain == null) {
            return null;
        }
        return SentenciaEjecutoriaEntity.builder()
                .idSentenciaEjecutoria(domain.getIdSentenciaEjecutoria())
                .idSentencia(domain.getIdSentencia())
                .idRecursoRevision(domain.getIdRecursoRevision())
                .numeroOficioEjecutoria(domain.getNumeroOficioEjecutoria())
                .fechaDeclaracionEjecutoria(domain.getFechaDeclaracionEjecutoria())
                .requerimientoCumplimiento(domain.getRequerimientoCumplimiento())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }

    public SentenciaEjecutoria toDomain(SentenciaEjecutoriaEntity entity) {
        if (entity == null) {
            return null;
        }
        return SentenciaEjecutoria.builder()
                .idSentenciaEjecutoria(entity.getIdSentenciaEjecutoria())
                .idSentencia(entity.getIdSentencia())
                .idRecursoRevision(entity.getIdRecursoRevision())
                .numeroOficioEjecutoria(entity.getNumeroOficioEjecutoria())
                .fechaDeclaracionEjecutoria(entity.getFechaDeclaracionEjecutoria())
                .requerimientoCumplimiento(entity.getRequerimientoCumplimiento())
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }

    public SentenciaEjecutoriaResponseDTO toResponse(SentenciaEjecutoria domain) {
        if (domain == null) {
            return null;
        }
        return SentenciaEjecutoriaResponseDTO.builder()
                .idSentenciaEjecutoria(domain.getIdSentenciaEjecutoria())
                .idSentencia(domain.getIdSentencia())
                .idRecursoRevision(domain.getIdRecursoRevision())
                .numeroOficioEjecutoria(domain.getNumeroOficioEjecutoria())
                .fechaDeclaracionEjecutoria(domain.getFechaDeclaracionEjecutoria())
                .requerimientoCumplimiento(domain.getRequerimientoCumplimiento())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }
}
