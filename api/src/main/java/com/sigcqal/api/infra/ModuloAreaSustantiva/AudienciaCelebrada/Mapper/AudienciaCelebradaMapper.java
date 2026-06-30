package com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Model.AudienciaCelebrada;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Entity.AudienciaCelebradaEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaResponseDTO;

@Component
public class AudienciaCelebradaMapper {

    public AudienciaCelebradaEntity toEntity(AudienciaCelebrada domain) {
        if (domain == null) {
            return null;
        }
        return AudienciaCelebradaEntity.builder()
                .idAudienciaCelebrada(domain.getIdAudienciaCelebrada())
                .idAudienciaEspera(domain.getIdAudienciaEspera())
                .fechaHoraCelebracion(domain.getFechaHoraCelebracion())
                .numeroOficioActa(domain.getNumeroOficioActa())
                .salaOModalidad(domain.getSalaOModalidad())
                .resultadoAudiencia(domain.getResultadoAudiencia())
                .asistioAutoridad(domain.getAsistioAutoridad())
                .rutaPdfOficio(domain.getRutaPdfOficio())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }

    public AudienciaCelebrada toDomain(AudienciaCelebradaEntity entity) {
        if (entity == null) {
            return null;
        }
        return AudienciaCelebrada.builder()
                .idAudienciaCelebrada(entity.getIdAudienciaCelebrada())
                .idAudienciaEspera(entity.getIdAudienciaEspera())
                .fechaHoraCelebracion(entity.getFechaHoraCelebracion())
                .numeroOficioActa(entity.getNumeroOficioActa())
                .salaOModalidad(entity.getSalaOModalidad())
                .resultadoAudiencia(entity.getResultadoAudiencia())
                .asistioAutoridad(entity.getAsistioAutoridad())
                .rutaPdfOficio(entity.getRutaPdfOficio())
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }

    public AudienciaCelebradaResponseDTO toResponse(AudienciaCelebrada domain) {
        if (domain == null) {
            return null;
        }
        return AudienciaCelebradaResponseDTO.builder()
                .idAudienciaCelebrada(domain.getIdAudienciaCelebrada())
                .idAudienciaEspera(domain.getIdAudienciaEspera())
                .fechaHoraCelebracion(domain.getFechaHoraCelebracion())
                .numeroOficioActa(domain.getNumeroOficioActa())
                .salaOModalidad(domain.getSalaOModalidad())
                .resultadoAudiencia(domain.getResultadoAudiencia())
                .asistioAutoridad(domain.getAsistioAutoridad())
                .rutaPdfOficio(domain.getRutaPdfOficio())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }
}
