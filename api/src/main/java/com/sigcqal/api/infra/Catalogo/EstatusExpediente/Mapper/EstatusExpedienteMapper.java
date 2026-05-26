package com.sigcqal.api.infra.Catalogo.EstatusExpediente.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.EstatusExpediente.Model.EstatusExpediente;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;

@Component
public class EstatusExpedienteMapper {
    public EstatusExpediente toDomain(EstatusExpedienteEntity entity) {
        if (entity == null) return null;

        EstatusExpediente dom = new EstatusExpediente();
        dom.setId(entity.getId());
        dom.setNombre(entity.getNombre());
        return dom;
    }

    public EstatusExpedienteEntity toEntity(EstatusExpediente dom) {
        if (dom == null) return null;

        EstatusExpedienteEntity entity = new EstatusExpedienteEntity();
        entity.setId(dom.getId());
        entity.setNombre(dom.getNombre());
        return entity;
    }
}
