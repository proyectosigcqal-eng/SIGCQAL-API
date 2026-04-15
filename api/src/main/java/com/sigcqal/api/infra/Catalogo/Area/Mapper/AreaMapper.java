package com.sigcqal.api.infra.Catalogo.Area.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Area.Model.Area;
import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;

@Component
public class AreaMapper {
    public Area toDomain(AreaEntity entity) {
        if (entity == null) return null;

        Area domain = new Area();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        return domain;
    }

    public AreaEntity toEntity(Area domain) {
        if (domain == null) return null;

        AreaEntity entity = new AreaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        return entity;
    }
}
