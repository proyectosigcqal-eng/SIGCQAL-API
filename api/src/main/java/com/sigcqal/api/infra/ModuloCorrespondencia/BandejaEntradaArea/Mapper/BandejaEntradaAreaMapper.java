package com.sigcqal.api.infra.ModuloCorrespondencia.BandejaEntradaArea.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.BandejaEntradaArea.Model.BandejaEntradaArea;
import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.BandejaEntradaArea.Entity.BandejaEntradaAreaEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.BandejaEntradaArea.Dto.BandejaEntradaAreaResponseDTO;

@Component
public class BandejaEntradaAreaMapper {
    public BandejaEntradaArea toDomain(BandejaEntradaAreaEntity entity) {
        if (entity == null) return null;

        BandejaEntradaArea dom = new BandejaEntradaArea();
        dom.setId(entity.getId());
        dom.setEstatus(entity.getEstatus());
        dom.setFechaAsignacion(entity.getFechaAsignacion());
        dom.setObservaciones(entity.getObservaciones());

        if (entity.getCorrespondencia() != null) {
            dom.setIdCorrespondencia(entity.getCorrespondencia().getId());
        }

        if (entity.getArea() != null) {
            dom.setIdArea(entity.getArea().getId());
        }

        if (entity.getUsuarioAsignado() != null) {
            dom.setIdUsuarioAsignado(entity.getUsuarioAsignado().getId());
        }

        return dom;
    }

    public BandejaEntradaAreaEntity toEntity(BandejaEntradaArea domain) {
        if (domain == null) return null;

        BandejaEntradaAreaEntity entity = new BandejaEntradaAreaEntity();
        entity.setId(domain.getId());
        entity.setEstatus(domain.getEstatus());
        entity.setFechaAsignacion(domain.getFechaAsignacion());
        entity.setObservaciones(domain.getObservaciones());

        if (domain.getIdCorrespondencia() != null) {
            CorrespondenciaEntity correspondencia = new CorrespondenciaEntity();
            correspondencia.setId(domain.getIdCorrespondencia());
            entity.setCorrespondencia(correspondencia);
        }

        if (domain.getIdArea() != null) {
            AreaEntity area = new AreaEntity();
            area.setId(domain.getIdArea());
            entity.setArea(area);
        }

        if (domain.getIdUsuarioAsignado() != null) {
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setId(domain.getIdUsuarioAsignado());
            entity.setUsuarioAsignado(usuario);
        }

        return entity;
    }

    public BandejaEntradaAreaResponseDTO toResponse(
            BandejaEntradaArea domain,
            String nombreArea,
            String folioCorrespondencia,
            String asuntoCorrespondencia) {
        if (domain == null) return null;

        BandejaEntradaAreaResponseDTO dto = new BandejaEntradaAreaResponseDTO();
        dto.setId(domain.getId());
        dto.setIdCorrespondencia(domain.getIdCorrespondencia());
        dto.setIdArea(domain.getIdArea());
        dto.setIdUsuarioAsignado(domain.getIdUsuarioAsignado());
        dto.setEstatus(domain.getEstatus());
        dto.setFechaAsignacion(domain.getFechaAsignacion() != null ? domain.getFechaAsignacion().toString() : null);
        dto.setObservaciones(domain.getObservaciones());
        dto.setNombreArea(nombreArea);
        dto.setFolioCorrespondencia(folioCorrespondencia);
        dto.setAsuntoCorrespondencia(asuntoCorrespondencia);
        return dto;
    }
}

