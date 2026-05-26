package com.sigcqal.api.infra.Catalogo.Persona.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;

@Component
public class PersonaMapper {
    public Persona toDomain(PersonaEntity entity) {
        if (entity == null) return null;

        Persona domain = new Persona();
        domain.setId(entity.getId());
        domain.setIdDireccion(entity.getIdDireccion());
        domain.setNombre(entity.getNombre());
        domain.setApellidoPaterno(entity.getApellidoPaterno());
        domain.setApellidoMaterno(entity.getApellidoMaterno());
        domain.setCurp(entity.getCurp());
        domain.setTelefono(entity.getTelefono());
        return domain;
    }

    public PersonaEntity toEntity(Persona domain) {
        if (domain == null) return null;

        PersonaEntity entity = new PersonaEntity();
        entity.setId(domain.getId());
        entity.setIdDireccion(domain.getIdDireccion());
        entity.setNombre(domain.getNombre());
        entity.setApellidoPaterno(domain.getApellidoPaterno());
        entity.setApellidoMaterno(domain.getApellidoMaterno());
        entity.setCurp(domain.getCurp());
        entity.setTelefono(domain.getTelefono());
        return entity;
    }
}
