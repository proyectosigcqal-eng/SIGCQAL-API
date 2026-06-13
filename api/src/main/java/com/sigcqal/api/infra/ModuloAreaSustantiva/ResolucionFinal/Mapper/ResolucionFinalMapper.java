package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalResponseDTO;

@Component
public class ResolucionFinalMapper {
    public ResolucionFinalEntity toEntity(ResolucionFinal domain) {
        ResolucionFinalEntity entity = new ResolucionFinalEntity();
        entity.setId(domain.getId());
        entity.setIdExpediente(domain.getIdExpediente());
        entity.setTipoResolucion(domain.getTipoResolucion());
        entity.setRutaDocumento(domain.getRutaDocumento());
        entity.setFechaEmision(domain.getFechaEmision());
        if (domain.getIdEstatus() != null) {
            EstatusExpedienteEntity estatus = new EstatusExpedienteEntity();
            estatus.setId(domain.getIdEstatus().longValue());
            entity.setEstatusExpediente(estatus);
        }
        return entity;
    }

    public ResolucionFinal toDomain(ResolucionFinalEntity entity) {
        ResolucionFinal domain = new ResolucionFinal();
        domain.setId(entity.getId());
        domain.setIdExpediente(entity.getIdExpediente());
        domain.setTipoResolucion(entity.getTipoResolucion());
        domain.setRutaDocumento(entity.getRutaDocumento());
        domain.setFechaEmision(entity.getFechaEmision());
        if (entity.getEstatusExpediente() != null) {
            domain.setIdEstatus(entity.getEstatusExpediente().getId().intValue());
            domain.setNombreEstatus(entity.getEstatusExpediente().getNombre());
        }
        return domain;
    }

    public ResolucionFinalResponseDTO toResponse(ResolucionFinal domain) {
        if (domain == null) return null;
        ResolucionFinalResponseDTO dto = new ResolucionFinalResponseDTO();
        dto.setTipoResolucion(domain.getTipoResolucion());
        dto.setRutaDocumento(domain.getRutaDocumento());
        dto.setFechaEmision(domain.getFechaEmision());
        dto.setNombreEstatus(domain.getNombreEstatus());
        return dto;
    }
}
