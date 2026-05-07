package com.sigcqal.api.infra.Catalogo.TipoCorrespondencia.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoCorrespondencia.Model.TipoCorrespondencia;
import com.sigcqal.api.infra.Catalogo.TipoCorrespondencia.Entity.TipoCorrespondenciaEntity;

@Component
public class TipoCorrespondenciaMapper {
    public TipoCorrespondencia toDomain(TipoCorrespondenciaEntity entity) {
        if (entity == null) return null;

        TipoCorrespondencia dom = new TipoCorrespondencia();
        dom.setIdTipo(entity.getIdTipo());
        dom.setIdNatural(entity.getIdNatural());
        dom.setDescripcion(entity.getDescripcion());
        dom.setActivo(entity.getActivo());
        return dom;
    }

    public TipoCorrespondenciaEntity toEntity(TipoCorrespondencia domain) {
        if (domain == null) return null;

        TipoCorrespondenciaEntity entity = new TipoCorrespondenciaEntity();
        entity.setIdTipo(domain.getIdTipo());
        entity.setIdNatural(domain.getIdNatural());
        entity.setDescripcion(domain.getDescripcion());
        entity.setActivo(domain.getActivo());
        return entity;
    }
}
