package com.sigcqal.api.infra.Catalogo.Estatus.Maper;

import com.sigcqal.api.domain.Catalogo.Estatus.Model.Estatus;
import com.sigcqal.api.infra.Catalogo.Estatus.Entity.EstatusEntity;

public class EstatusMaper {

public Estatus toDomain(EstatusEntity entity) {
    if (entity == null) return null;

    Estatus domain = new Estatus();
    domain.setIdEstatus(entity.getIdEstatus());
    domain.setNombreEstatus(entity.getNombreEstatus());
    domain.setDescripcion(entity.getDescripcion());

    return domain;

}

public EstatusEntity toEntity(Estatus domain) {
    if (domain == null) return null;

    EstatusEntity entity = new EstatusEntity();
    entity.setIdEstatus(domain.getIdEstatus());
    entity.setNombreEstatus(domain.getNombreEstatus());
    entity.setDescripcion(domain.getDescripcion());

    return entity;
}

}
