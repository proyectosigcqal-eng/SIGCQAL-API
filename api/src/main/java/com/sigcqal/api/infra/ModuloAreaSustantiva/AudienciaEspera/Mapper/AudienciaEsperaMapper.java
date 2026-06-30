package com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaEspera.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Model.AudienciaEspera;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaEspera.Entity.AudienciaEsperaEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto.AudienciaEsperaResponseDTO;

@Component
public class AudienciaEsperaMapper {

    public AudienciaEsperaEntity toEntity(AudienciaEspera domain) {
        if (domain == null) {
            return null;
        }
        return AudienciaEsperaEntity.builder()
                .idAudienciaEspera(domain.getIdAudienciaEspera())
                .idDemandaAmparo(domain.getIdDemandaAmparo())
                .numeroOficioAdmision(domain.getNumeroOficioAdmision())
                .fechaNotificacionOficio(domain.getFechaNotificacionOficio())
                .fechaHoraAudienciaProg(domain.getFechaHoraAudienciaProg())
                .observaciones(domain.getObservaciones())
                .rutaPdfOficio(domain.getRutaPdfOficio())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }

    public AudienciaEspera toDomain(AudienciaEsperaEntity entity) {
        if (entity == null) {
            return null;
        }
        return AudienciaEspera.builder()
                .idAudienciaEspera(entity.getIdAudienciaEspera())
                .idDemandaAmparo(entity.getIdDemandaAmparo())
                .numeroOficioAdmision(entity.getNumeroOficioAdmision())
                .fechaNotificacionOficio(entity.getFechaNotificacionOficio())
                .fechaHoraAudienciaProg(entity.getFechaHoraAudienciaProg())
                .observaciones(entity.getObservaciones())
                .rutaPdfOficio(entity.getRutaPdfOficio())
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }

    public AudienciaEsperaResponseDTO toResponse(AudienciaEspera domain) {
        if (domain == null) {
            return null;
        }
        return AudienciaEsperaResponseDTO.builder()
                .idAudienciaEspera(domain.getIdAudienciaEspera())
                .idDemandaAmparo(domain.getIdDemandaAmparo())
                .numeroOficioAdmision(domain.getNumeroOficioAdmision())
                .fechaNotificacionOficio(domain.getFechaNotificacionOficio())
                .fechaHoraAudienciaProg(domain.getFechaHoraAudienciaProg())
                .observaciones(domain.getObservaciones())
                .rutaPdfOficio(domain.getRutaPdfOficio())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }
}
