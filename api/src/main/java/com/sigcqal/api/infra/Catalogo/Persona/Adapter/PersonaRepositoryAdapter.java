package com.sigcqal.api.infra.Catalogo.Persona.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Persona.Mapper.PersonaMapper;
import com.sigcqal.api.infra.Catalogo.Persona.Repository.PersonaJpaRepository;

@Component
public class PersonaRepositoryAdapter implements PersonaRepositoryPort {
    private final PersonaJpaRepository jpaRepository;
    private final PersonaMapper mapper;

    public PersonaRepositoryAdapter(PersonaJpaRepository jpaRepository, PersonaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Persona> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Persona> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Persona save(Persona persona) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(persona)));
    }
}
