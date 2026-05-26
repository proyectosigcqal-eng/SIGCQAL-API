package com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoActoEmitido.Model.TipoActoEmitido;
import com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Entity.TipoActoEmitidoEntity;

@Component
public class TipoActoEmitidoMapper {
    public TipoActoEmitido toDomain(TipoActoEmitidoEntity entity) {
        if (entity == null) return null;

        TipoActoEmitido dom = new TipoActoEmitido();
        dom.setId(entity.getId());
        dom.setNombre(entity.getNombre());
        return dom;
    }

    public TipoActoEmitidoEntity toEntity(TipoActoEmitido dom) {
        if (dom == null) return null;

        TipoActoEmitidoEntity entity = new TipoActoEmitidoEntity();
        entity.setId(dom.getId());
        entity.setNombre(dom.getNombre());
        return entity;
    }
}
