package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Model.NotificacionSentenciaCumplida;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Entity.NotificacionSentenciaCumplidaEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Dto.NotificacionSentenciaCumplidaResponseDTO;

@Component
public class NotificacionSentenciaCumplidaMapper {

    public NotificacionSentenciaCumplidaEntity toEntity(NotificacionSentenciaCumplida domain) {
        if (domain == null) {
            return null;
        }
        return NotificacionSentenciaCumplidaEntity.builder()
                .idSentenciaCumplida(domain.getIdSentenciaCumplida())
                .idSentenciaEjecutoria(domain.getIdSentenciaEjecutoria())
                .numeroOficioCumplimiento(domain.getNumeroOficioCumplimiento())
                .numeroOficioArchivo(domain.getNumeroOficioArchivo())
                .fechaNotificacionArchivo(domain.getFechaNotificacionArchivo())
                .observacionesFinales(domain.getObservacionesFinales())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }

    public NotificacionSentenciaCumplida toDomain(NotificacionSentenciaCumplidaEntity entity) {
        if (entity == null) {
            return null;
        }
        return NotificacionSentenciaCumplida.builder()
                .idSentenciaCumplida(entity.getIdSentenciaCumplida())
                .idSentenciaEjecutoria(entity.getIdSentenciaEjecutoria())
                .numeroOficioCumplimiento(entity.getNumeroOficioCumplimiento())
                .numeroOficioArchivo(entity.getNumeroOficioArchivo())
                .fechaNotificacionArchivo(entity.getFechaNotificacionArchivo())
                .observacionesFinales(entity.getObservacionesFinales())
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }

    public NotificacionSentenciaCumplidaResponseDTO toResponse(NotificacionSentenciaCumplida domain) {
        if (domain == null) {
            return null;
        }
        return NotificacionSentenciaCumplidaResponseDTO.builder()
                .idSentenciaCumplida(domain.getIdSentenciaCumplida())
                .idSentenciaEjecutoria(domain.getIdSentenciaEjecutoria())
                .numeroOficioCumplimiento(domain.getNumeroOficioCumplimiento())
                .numeroOficioArchivo(domain.getNumeroOficioArchivo())
                .fechaNotificacionArchivo(domain.getFechaNotificacionArchivo())
                .observacionesFinales(domain.getObservacionesFinales())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }
}
