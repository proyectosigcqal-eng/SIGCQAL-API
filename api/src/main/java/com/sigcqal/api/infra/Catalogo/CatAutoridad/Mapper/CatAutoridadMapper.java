package com.sigcqal.api.infra.Catalogo.CatAutoridad.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.CatAutoridad.Model.CatAutoridad;
import com.sigcqal.api.infra.Catalogo.CatAutoridad.Entity.CatAutoridadEntity;

@Component
public class CatAutoridadMapper {
    public CatAutoridad toDomain(CatAutoridadEntity entity) {
        if (entity == null) return null;

        CatAutoridad domain = new CatAutoridad();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        return domain;
    }

    public CatAutoridadEntity toEntity(CatAutoridad domain) {
        if (domain == null) return null;

        CatAutoridadEntity entity = new CatAutoridadEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        return entity;
    }
}
