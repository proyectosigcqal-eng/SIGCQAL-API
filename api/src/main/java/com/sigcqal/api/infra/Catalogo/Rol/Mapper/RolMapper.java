package com.sigcqal.api.infra.Catalogo.Rol.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Rol.Model.Rol;
import com.sigcqal.api.infra.Catalogo.Rol.Entity.RolEntity;

@Component
public class RolMapper {
    public Rol toDomain(RolEntity entity) {
        if (entity == null) return null;

        Rol domain = new Rol();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        return domain;
    }

    public RolEntity toEntity(Rol domain) {
        if (domain == null) return null;

        RolEntity entity = new RolEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        return entity;
    }
}
