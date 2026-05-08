package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoOficio.Model.SeguimientoOficio;
import com.sigcqal.api.infra.Catalogo.Estatus.Entity.EstatusEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Entity.OficioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Entity.SeguimientoOficioEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoOficio.Dto.SeguimientoOficioResponseDTO;

@Component
public class SeguimientoOficioMapper {

    public SeguimientoOficio toDomain(SeguimientoOficioEntity entity) {
        if (entity == null) return null;
        
        return SeguimientoOficio.builder()
                .idSeguimientoOficio(entity.getIdSeguimientoOficio())
                .idOficio(entity.getOficio() != null ? entity.getOficio().getId().intValue() : null)
                .folioRespuesta(entity.getFolioRespuesta())
                .respuestasSeguimientoOficio(entity.getRespuestas())
                .fechaResolucion(entity.getFechaResolucion())
                .horaResolucion(entity.getHoraResolucion())
                .archivoAdjunto(entity.getArchivoAdjunto())
                .idUsuario(entity.getUsuario() != null ? entity.getUsuario().getId().intValue() : null)
                .idEstatus(entity.getEstatus() != null ? entity.getEstatus().getIdEstatus() : null)
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }

    public SeguimientoOficioEntity toEntity(SeguimientoOficio domain) {
        if (domain == null) return null;

        SeguimientoOficioEntity entity = new SeguimientoOficioEntity();
        if (domain.getIdSeguimientoOficio() != null) {
            entity.setIdSeguimientoOficio(domain.getIdSeguimientoOficio());
        }
        if (domain.getIdOficio() != null) {
            OficioEntity oficio = new OficioEntity();
            oficio.setId(domain.getIdOficio().longValue());
            entity.setOficio(oficio);
        }
        entity.setFolioRespuesta(domain.getFolioRespuesta());
        entity.setRespuestas(domain.getRespuestasSeguimientoOficio());
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

    public SeguimientoOficioResponseDTO toResponse(SeguimientoOficio domain) {
        if (domain == null) return null;

        SeguimientoOficioResponseDTO dto = new SeguimientoOficioResponseDTO();
        dto.setIdSeguimientoOficio(domain.getIdSeguimientoOficio());
        dto.setIdOficio(domain.getIdOficio());
        dto.setFolioRespuesta(domain.getFolioRespuesta());
        dto.setRespuestasSeguimientoOficio(domain.getRespuestasSeguimientoOficio());
        dto.setFechaResolucion(domain.getFechaResolucion() != null ? domain.getFechaResolucion().toString() : null);
        dto.setHoraResolucion(domain.getHoraResolucion() != null ? domain.getHoraResolucion().toString() : null);
        dto.setArchivoAdjunto(domain.getArchivoAdjunto());
        dto.setIdUsuario(domain.getIdUsuario());
        dto.setIdEstatus(domain.getIdEstatus());
        dto.setFechaRegistro(domain.getFechaRegistro() != null ? domain.getFechaRegistro().toString() : null);
        return dto;
    }
}