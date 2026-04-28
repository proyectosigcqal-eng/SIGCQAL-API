package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Model.SeguimientoCorrespondencia;
import com.sigcqal.api.infra.Catalogo.Estatus.Entity.EstatusEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Entity.SeguimientoCorrespondenciaEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Dto.SeguimientoCorrespondenciaResponseDTO;

@Component
public class SeguimientoCorrespondenciaMapper {

    public SeguimientoCorrespondencia toDomain(SeguimientoCorrespondenciaEntity entity) {
        if (entity == null) {
            return null;
        }
        return SeguimientoCorrespondencia.builder()
                .idSeguimientoCorrespondencia(entity.getIdSeguimientoCorrespondencia())
                .idCorrespondencia(entity.getCorrespondencia() != null ? entity.getCorrespondencia().getId().intValue() : null)
                .folioRespuesta(entity.getFolioRespuesta())
                .respuestaSeguimientoCorrespondencia(entity.getRespuesta())
                .fechaResolucion(entity.getFechaResolucion())
                .horaResolucion(entity.getHoraResolucion())
                .archivoAdjunto(entity.getArchivoAdjunto())
                .idUsuario(entity.getUsuario() != null ? entity.getUsuario().getId().intValue() : null)
                .idEstatus(entity.getEstatus() != null ? entity.getEstatus().getIdEstatus() : null)
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }

    public SeguimientoCorrespondenciaEntity toEntity(SeguimientoCorrespondencia domain) {
        if (domain == null) {
            return null;
        }
        SeguimientoCorrespondenciaEntity entity = new SeguimientoCorrespondenciaEntity();
        if (domain.getIdSeguimientoCorrespondencia() != null) {
            entity.setIdSeguimientoCorrespondencia(domain.getIdSeguimientoCorrespondencia());
        }
        if (domain.getIdCorrespondencia() != null) {
            CorrespondenciaEntity correspondencia = new CorrespondenciaEntity();
            correspondencia.setId(domain.getIdCorrespondencia().longValue());
            entity.setCorrespondencia(correspondencia);
        }
        entity.setFolioRespuesta(domain.getFolioRespuesta());
        entity.setRespuesta(domain.getRespuestaSeguimientoCorrespondencia());
        entity.setFechaResolucion(domain.getFechaResolucion());
        entity.setHoraResolucion(domain.getHoraResolucion());
        entity.setArchivoAdjunto(domain.getArchivoAdjunto());
        if (domain.getIdUsuario() != null) {
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setId(domain.getIdUsuario().longValue());
            entity.setUsuario(usuario);
        }
        if (domain.getIdEstatus() != null) {
            EstatusEntity estatus = new EstatusEntity();
            estatus.setIdEstatus(domain.getIdEstatus());
            entity.setEstatus(estatus);
        }
        entity.setFechaRegistro(domain.getFechaRegistro());
        return entity;
    }

    public SeguimientoCorrespondenciaResponseDTO toResponse(SeguimientoCorrespondencia domain) {
        if (domain == null) {
            return null;
        }
        SeguimientoCorrespondenciaResponseDTO dto = new SeguimientoCorrespondenciaResponseDTO();
        dto.setIdSeguimientoCorrespondencia(domain.getIdSeguimientoCorrespondencia());
        dto.setIdCorrespondencia(domain.getIdCorrespondencia());
        dto.setFolioRespuesta(domain.getFolioRespuesta());
        dto.setRespuestaSeguimientoCorrespondencia(domain.getRespuestaSeguimientoCorrespondencia());
        dto.setFechaResolucion(domain.getFechaResolucion() != null ? domain.getFechaResolucion().toString() : null);
        dto.setHoraResolucion(domain.getHoraResolucion() != null ? domain.getHoraResolucion().toString() : null);
        dto.setArchivoAdjunto(domain.getArchivoAdjunto());
        dto.setIdUsuario(domain.getIdUsuario());
        dto.setIdEstatus(domain.getIdEstatus());
        dto.setFechaRegistro(domain.getFechaRegistro() != null ? domain.getFechaRegistro().toString() : null);
        return dto;
    }
}
