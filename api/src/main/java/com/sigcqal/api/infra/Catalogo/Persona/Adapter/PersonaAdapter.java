package com.sigcqal.api.infra.Catalogo.Persona.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.Catalogo.Persona.Mapper.PersonaMapper;
import com.sigcqal.api.infra.Catalogo.Persona.Repository.PersonaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaRepositoryPort {

    private final PersonaJpaRepository repository;
    private final PersonaMapper mapper;

    @Override
    public Persona save(Persona persona) {
        PersonaEntity entity = mapper.toEntity(persona);
        PersonaEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Persona> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Persona> findByRfc(String rfc) {
        return repository.findByRfc(rfc).map(mapper::toDomain);
    }

    @Override
    public List<Persona> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

}