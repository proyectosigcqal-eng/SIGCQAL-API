package com.sigcqal.api.infra.Catalogo.CatEstatusSustantiva.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.CatEstatusSustantiva.Model.CatEstatusSustantiva;
import com.sigcqal.api.infra.Catalogo.CatEstatusSustantiva.Entity.CatEstatusSustantivaEntity;

@Component
public class CatEstatusSustantivaMapper {
    public CatEstatusSustantiva toDomain(CatEstatusSustantivaEntity entity) {
        if (entity == null) return null;

        CatEstatusSustantiva domain = new CatEstatusSustantiva();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        return domain;
    }

    public CatEstatusSustantivaEntity toEntity(CatEstatusSustantiva domain) {
        if (domain == null) return null;

        CatEstatusSustantivaEntity entity = new CatEstatusSustantivaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        return entity;
    }
}
