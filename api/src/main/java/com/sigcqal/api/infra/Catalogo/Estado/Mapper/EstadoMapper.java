package com.sigcqal.api.infra.Catalogo.Estado.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Estado.Model.Estado;
import com.sigcqal.api.infra.Catalogo.Estado.Entity.EstadoEntity;

@Component
public class EstadoMapper {


public Estado toDomain(EstadoEntity entity) {
        if (entity == null) return null;

        Estado domain = new Estado();
        domain.setIdEstado(entity.getIdEstado()); 
        domain.setNombreEstado(entity.getNombreEstado());

        return domain;
    }

    public EstadoEntity toEntity(Estado domain) {
        if (domain == null) return null;

        EstadoEntity entity = new EstadoEntity();
        entity.setIdEstado(domain.getIdEstado());
        entity.setNombreEstado(domain.getNombreEstado());

        return entity;
    }
}