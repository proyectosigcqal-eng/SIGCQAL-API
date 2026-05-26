package com.sigcqal.api.infra.Catalogo.CatTipoProceso.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.CatTipoProceso.Model.CatTipoProceso;
import com.sigcqal.api.infra.Catalogo.CatTipoProceso.Entity.CatTipoProcesoEntity;

@Component
public class CatTipoProcesoMapper {
    public CatTipoProceso toDomain(CatTipoProcesoEntity entity) {
        if (entity == null) return null;

        CatTipoProceso domain = new CatTipoProceso();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        return domain;
    }

    public CatTipoProcesoEntity toEntity(CatTipoProceso domain) {
        if (domain == null) return null;

        CatTipoProcesoEntity entity = new CatTipoProcesoEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        return entity;
    }
}
