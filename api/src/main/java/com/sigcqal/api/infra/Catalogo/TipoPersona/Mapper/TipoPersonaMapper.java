package com.sigcqal.api.infra.Catalogo.TipoPersona.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoPersona.Model.TipoPersona;
import com.sigcqal.api.infra.Catalogo.TipoPersona.Entity.TipoPersonaEntity;

@Component
public class TipoPersonaMapper {
    public TipoPersona toDomain(TipoPersonaEntity entity) {
        if (entity == null) return null;

        TipoPersona domain = new TipoPersona();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        return domain;
    }

    public TipoPersonaEntity toEntity(TipoPersona domain) {
        if (domain == null) return null;

        TipoPersonaEntity entity = new TipoPersonaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        return entity;
    }
}
