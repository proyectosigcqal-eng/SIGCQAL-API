package com.sigcqal.api.infra.Catalogo.Municipio.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Municipio.Model.Municipio;
import com.sigcqal.api.infra.Catalogo.Municipio.Entity.MunicipioEntity;

@Component
public class MunicipioMapper {


    public Municipio toDomain(MunicipioEntity entity) {
        if (entity == null) return null;
        Municipio domain = new Municipio();
        domain.setId(entity.getId());
        domain.setNombreMunicipio(entity.getNombreMunicipio());
        return domain;
    }

    public MunicipioEntity toEntity(Municipio domain) {
        if (domain == null) return null;
        MunicipioEntity entity = new MunicipioEntity();
        entity.setId(domain.getId());
        entity.setNombreMunicipio(domain.getNombreMunicipio());
        return entity;
    }
}