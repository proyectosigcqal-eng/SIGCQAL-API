package com.sigcqal.api.infra.Catalogo.CatAutoridad.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.CatAutoridad.Model.CatAutoridad;
import com.sigcqal.api.domain.Catalogo.CatAutoridad.Port.CatAutoridadRepositoryPort;
import com.sigcqal.api.infra.Catalogo.CatAutoridad.Mapper.CatAutoridadMapper;
import com.sigcqal.api.infra.Catalogo.CatAutoridad.Repository.CatAutoridadJpaRepository;

@Component
public class CatAutoridadRepositoryAdapter implements CatAutoridadRepositoryPort {
    private final CatAutoridadJpaRepository jpaRepository;
    private final CatAutoridadMapper mapper;

    public CatAutoridadRepositoryAdapter(CatAutoridadJpaRepository jpaRepository, CatAutoridadMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CatAutoridad> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<CatAutoridad> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
