package com.sigcqal.api.infra.Catalogo.TipoPersona.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoPersona.Model.TipoPersona;
import com.sigcqal.api.domain.Catalogo.TipoPersona.Port.TipoPersonaRepositoryPort;
import com.sigcqal.api.infra.Catalogo.TipoPersona.Mapper.TipoPersonaMapper;
import com.sigcqal.api.infra.Catalogo.TipoPersona.Repository.TipoPersonaJpaRepository;

@Component
public class TipoPersonaRepositoryAdapter implements TipoPersonaRepositoryPort {
    private final TipoPersonaJpaRepository jpaRepository;
    private final TipoPersonaMapper mapper;

    public TipoPersonaRepositoryAdapter(TipoPersonaJpaRepository jpaRepository, TipoPersonaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<TipoPersona> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<TipoPersona> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public TipoPersona save(TipoPersona tipoPersona) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(tipoPersona)));
    }
}
