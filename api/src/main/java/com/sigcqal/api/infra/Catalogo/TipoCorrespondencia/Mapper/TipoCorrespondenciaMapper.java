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
        dom.setDescripcion(entity.getDescripcion());

        return dom;
    }

    public TipoCorrespondenciaEntity toEntity(TipoCorrespondencia domain) {
        if (domain == null) return null;

        TipoCorrespondenciaEntity entity = new TipoCorrespondenciaEntity();
        entity.setIdTipo(domain.getIdTipo());
        entity.setDescripcion(domain.getDescripcion());

        return entity;
    }
}
