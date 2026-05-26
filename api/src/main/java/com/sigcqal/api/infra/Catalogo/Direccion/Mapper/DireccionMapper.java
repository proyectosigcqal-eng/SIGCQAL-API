package com.sigcqal.api.infra.Catalogo.Direccion.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Direccion.Model.Direccion;
import com.sigcqal.api.infra.Catalogo.Direccion.Entity.DireccionEntity;

@Component
public class DireccionMapper {
    public Direccion toDomain(DireccionEntity entity) {
        if (entity == null) return null;

        Direccion domain = new Direccion();
        domain.setId(entity.getId());
        domain.setCalle(entity.getCalle());
        domain.setNumExt(entity.getNumExt());
        domain.setNumInt(entity.getNumInt());
        domain.setColonia(entity.getColonia());
        domain.setCp(entity.getCp());
        domain.setIdMunicipio(entity.getIdMunicipio());
        domain.setIdEstado(entity.getIdEstado());
        return domain;
    }

    public DireccionEntity toEntity(Direccion domain) {
        if (domain == null) return null;

        DireccionEntity entity = new DireccionEntity();
        entity.setId(domain.getId());
        entity.setCalle(domain.getCalle());
        entity.setNumExt(domain.getNumExt());
        entity.setNumInt(domain.getNumInt());
        entity.setColonia(domain.getColonia());
        entity.setCp(domain.getCp());
        entity.setIdMunicipio(domain.getIdMunicipio());
        entity.setIdEstado(domain.getIdEstado());
        return entity;
    }
}
