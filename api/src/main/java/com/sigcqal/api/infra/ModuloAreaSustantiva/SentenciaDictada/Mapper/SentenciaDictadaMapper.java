package com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Model.SentenciaDictada;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Entity.SentenciaDictadaEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto.SentenciaDictadaResponseDTO;

@Component
public class SentenciaDictadaMapper {

    public SentenciaDictadaEntity toEntity(SentenciaDictada domain) {
        if (domain == null) {
            return null;
        }
        return SentenciaDictadaEntity.builder()
                .idSentencia(domain.getIdSentencia())
                .idAudienciaCelebrada(domain.getIdAudienciaCelebrada())
                .fechaDictado(domain.getFechaDictado())
                .fechaNotificacionSentencia(domain.getFechaNotificacionSentencia())
                .sentidoFallo(domain.getSentidoFallo())
                .puntosResolutivos(domain.getPuntosResolutivos())
                .numeroOficioSentencia(domain.getNumeroOficioSentencia())
                .rutaArchivoSentencia(domain.getRutaArchivoSentencia())
                .rutaPdfOficio(domain.getRutaPdfOficio())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }

    public SentenciaDictada toDomain(SentenciaDictadaEntity entity) {
        if (entity == null) {
            return null;
        }
        return SentenciaDictada.builder()
                .idSentencia(entity.getIdSentencia())
                .idAudienciaCelebrada(entity.getIdAudienciaCelebrada())
                .fechaDictado(entity.getFechaDictado())
                .fechaNotificacionSentencia(entity.getFechaNotificacionSentencia())
                .sentidoFallo(entity.getSentidoFallo())
                .puntosResolutivos(entity.getPuntosResolutivos())
                .numeroOficioSentencia(entity.getNumeroOficioSentencia())
                .rutaArchivoSentencia(entity.getRutaArchivoSentencia())
                .rutaPdfOficio(entity.getRutaPdfOficio())
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }

    public SentenciaDictadaResponseDTO toResponse(SentenciaDictada domain) {
        if (domain == null) {
            return null;
        }
        return SentenciaDictadaResponseDTO.builder()
                .idSentencia(domain.getIdSentencia())
                .idAudienciaCelebrada(domain.getIdAudienciaCelebrada())
                .fechaDictado(domain.getFechaDictado())
                .fechaNotificacionSentencia(domain.getFechaNotificacionSentencia())
                .sentidoFallo(domain.getSentidoFallo())
                .puntosResolutivos(domain.getPuntosResolutivos())
                .numeroOficioSentencia(domain.getNumeroOficioSentencia())
                .rutaArchivoSentencia(domain.getRutaArchivoSentencia())
                .rutaPdfOficio(domain.getRutaPdfOficio())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }
}
