package com.sigcqal.api.infra.Catalogo.ControlFoliosConfig.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.ControlFoliosConfig.Model.ControlFoliosConfig;
import com.sigcqal.api.infra.Catalogo.ControlFoliosConfig.Entity.ControlFoliosConfigEntity;

@Component
public class ControlFoliosConfigMapper {
    public ControlFoliosConfig toDomain(ControlFoliosConfigEntity entity) {
        if (entity == null) return null;

        ControlFoliosConfig dom = new ControlFoliosConfig();
        dom.setId(entity.getId());
        dom.setNombre(entity.getNombre());
        return dom;
    }

    public ControlFoliosConfigEntity toEntity(ControlFoliosConfig dom) {
        if (dom == null) return null;

        ControlFoliosConfigEntity entity = new ControlFoliosConfigEntity();
        entity.setId(dom.getId());
        entity.setNombre(dom.getNombre());
        return entity;
    }
}
