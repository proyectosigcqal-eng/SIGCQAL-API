package com.sigcqal.api.infra.Catalogo.Autoridad.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Autoridad.Model.Autoridad;
import com.sigcqal.api.infra.Catalogo.Autoridad.Entity.AutoridadEntity;

@Component
public class AutoridadMapper {
    public Autoridad toDomain(AutoridadEntity entity) {
        if (entity == null) return null;

        Autoridad domain = new Autoridad();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        return domain;
    }

    public AutoridadEntity toEntity(Autoridad domain) {
        if (domain == null) return null;

        AutoridadEntity entity = new AutoridadEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        return entity;
    }
}
