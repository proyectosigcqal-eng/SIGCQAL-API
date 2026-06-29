package com.sigcqal.api.infra.Catalogo.Personal.Mapper;

import com.sigcqal.api.domain.Catalogo.Personal.Model.Personal;
import com.sigcqal.api.infra.Catalogo.Personal.Entity.PersonalEntity;
// Necesitas importar tu mapper de personas para manejar la relación
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
        
        return PersonalEntity.builder()
            .idPersonal(domain.getIdPersonal())
            .persona(personaMapper.toEntity(domain.getPersona())) // Mapeo de relación
            .fechaRegistro(domain.getFechaRegistro())
            .activo(domain.getActivo())
            .build();
    }
}