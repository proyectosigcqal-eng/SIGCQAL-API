package com.sigcqal.api.infra.Catalogo.TipoEntrada.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoEntrada.Model.TipoEntrada;
import com.sigcqal.api.infra.Catalogo.TipoEntrada.Entity.TipoEntradaEntity;

@Component
public class TipoEntradaMapper {
    public TipoEntrada toDomain(TipoEntradaEntity entity) {
        if (entity == null) return null;

        TipoEntrada dom = new TipoEntrada();
        dom.setId(entity.getId());
        dom.setNombre(entity.getNombre());
        return dom;
    }

    public TipoEntradaEntity toEntity(TipoEntrada dom) {
        if (dom == null) return null;

        TipoEntradaEntity entity = new TipoEntradaEntity();
        entity.setId(dom.getId());
        entity.setNombre(dom.getNombre());
        return entity;
    }
}
