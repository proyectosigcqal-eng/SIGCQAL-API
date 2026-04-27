package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Model.SeguimientoMemorandum;
import com.sigcqal.api.infra.Catalogo.Estatus.Entity.EstatusEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Entity.MemorandumEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Entity.SeguimientoMemorandumEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto.SeguimientoMemorandumResponseDTO;

@Component
public class SeguimientoMemorandumMapper {

    public SeguimientoMemorandum toDomain(SeguimientoMemorandumEntity entity) {
        if (entity == null) {
            return null;
        }
        return SeguimientoMemorandum.builder()
                .idSeguimientoMemorandum(entity.getId())
                .idMemo(entity.getMemorandum() != null ? entity.getMemorandum().getId() : null)
                .folioRespuesta(entity.getFolioRespuesta())
                .respuestaSeguimientoMemorandum(entity.getRespuestaSeguimientoMemorandum())
                .fechaResolucion(entity.getFechaResolucion())
                .horaResolucion(entity.getHoraResolucion())
                .archivoAdjunto(entity.getArchivoAdjunto())
                .idUsuario(entity.getUsuario() != null ? entity.getUsuario().getId() : null)
                .idEstatus(entity.getEstatus() != null ? entity.getEstatus().getIdEstatus().longValue() : null)
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }

    public SeguimientoMemorandumEntity toEntity(SeguimientoMemorandum domain) {
        if (domain == null) {
            return null;
        }
        SeguimientoMemorandumEntity entity = new SeguimientoMemorandumEntity();
        if (domain.getIdSeguimientoMemorandum() != null) {
            entity.setId(domain.getIdSeguimientoMemorandum());
        }
        if (domain.getIdMemo() != null) {
            MemorandumEntity memorandum = new MemorandumEntity();
            memorandum.setId(domain.getIdMemo());
            entity.setMemorandum(memorandum);
        }
        entity.setRespuestaSeguimientoMemorandum(domain.getRespuestaSeguimientoMemorandum());
        entity.setFechaResolucion(domain.getFechaResolucion());
        entity.setHoraResolucion(domain.getHoraResolucion());
        entity.setArchivoAdjunto(domain.getArchivoAdjunto());
        if (domain.getIdUsuario() != null) {
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setId(domain.getIdUsuario());
            entity.setUsuario(usuario);
        }
        if (domain.getIdEstatus() != null) {
            EstatusEntity estatus = new EstatusEntity();
            estatus.setIdEstatus(domain.getIdEstatus().intValue());
            entity.setEstatus(estatus);
        }
        entity.setFechaRegistro(domain.getFechaRegistro());
        return entity;
    }

    public SeguimientoMemorandumResponseDTO toResponse(SeguimientoMemorandum domain) {
        if (domain == null) {
            return null;
        }
        SeguimientoMemorandumResponseDTO dto = new SeguimientoMemorandumResponseDTO();
        dto.setIdSeguimientoMemorandum(domain.getIdSeguimientoMemorandum());
        dto.setIdMemo(domain.getIdMemo());
        dto.setFolioRespuesta(domain.getFolioRespuesta());
        dto.setRespuestaSeguimientoMemorandum(domain.getRespuestaSeguimientoMemorandum());
        dto.setFechaResolucion(
                domain.getFechaResolucion() != null ? domain.getFechaResolucion().toString() : null);
        dto.setHoraResolucion(
                domain.getHoraResolucion() != null ? domain.getHoraResolucion().toString() : null);
        dto.setArchivoAdjunto(domain.getArchivoAdjunto());
        dto.setIdUsuario(domain.getIdUsuario());
        dto.setIdEstatus(domain.getIdEstatus());
        dto.setFechaRegistro(
                domain.getFechaRegistro() != null ? domain.getFechaRegistro().toString() : null);
        return dto;
    }
}
