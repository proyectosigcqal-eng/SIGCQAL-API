package com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.EstatusDetalleExpediente.Model.EstatusDetalleExpediente;
import com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Entity.EstatusDetalleExpedienteEntity;

@Component
public class EstatusDetalleExpedienteMapper {
    public EstatusDetalleExpediente toDomain(EstatusDetalleExpedienteEntity entity) {
        if (entity == null) return null;

        EstatusDetalleExpediente domain = new EstatusDetalleExpediente();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        return domain;
    }

    public EstatusDetalleExpedienteEntity toEntity(EstatusDetalleExpediente domain) {
        if (domain == null) return null;

        EstatusDetalleExpedienteEntity entity = new EstatusDetalleExpedienteEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        return entity;
    }
}
