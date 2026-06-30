package com.sigcqal.api.infra.Catalogo.Personal.Mapper;


import com.sigcqal.api.domain.Catalogo.Personal.Model.Personal;
import com.sigcqal.api.infra.Catalogo.Personal.Entity.PersonalEntity;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.Catalogo.Persona.Mapper.PersonaMapper; 

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonalMapper {

    private final PersonaMapper personaMapper;

    public Personal toDomain(PersonalEntity entity) {
        if (entity == null) return null;
        
        
        return Personal.builder()
            .idPersonal(entity.getIdPersonal())
            .persona(personaMapper.toDomain(entity.getPersona())) // Mapeo de relación
            .fechaRegistro(entity.getFechaRegistro())
            .activo(entity.getActivo())
            .build();
    }

     public PersonalEntity toEntity(Personal domain) {
        if (domain == null) return null;
        
        // Mapeamos la persona, pero dejamos la dirección como NULL
        // para que el Adapter se encargue de gestionarla correctamente.
        PersonaEntity personaEntity = personaMapper.toEntity(domain.getPersona());
        personaEntity.setDireccion(null); 

        return PersonalEntity.builder()
            .idPersonal(domain.getIdPersonal())
            .persona(personaEntity)
            .fechaRegistro(domain.getFechaRegistro())
            .activo(domain.getActivo())
            .build();
    }
}