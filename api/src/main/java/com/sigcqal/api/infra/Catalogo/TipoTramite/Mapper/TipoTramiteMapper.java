package com.sigcqal.api.infra.Catalogo.TipoTramite.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoTramite.Model.TipoTramite;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;

@Component
public class TipoTramiteMapper {
    public TipoTramite toDomain(TipoTramiteEntity entity) {
        if (entity == null) return null;

        TipoTramite dom = new TipoTramite();
        dom.setId(entity.getId());
        dom.setNombre(entity.getNombre());
        return dom;
    }

    public TipoTramiteEntity toEntity(TipoTramite dom) {
        if (dom == null) return null;

        TipoTramiteEntity entity = new TipoTramiteEntity();
        entity.setId(dom.getId());
        entity.setNombre(dom.getNombre());
        return entity;
    }
}
