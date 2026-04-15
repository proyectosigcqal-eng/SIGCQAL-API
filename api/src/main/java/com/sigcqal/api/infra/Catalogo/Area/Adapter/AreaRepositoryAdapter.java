package com.sigcqal.api.infra.Catalogo.Area.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Area.Model.Area;
import com.sigcqal.api.domain.Catalogo.Area.Port.AreaRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Area.Mapper.AreaMapper;
import com.sigcqal.api.infra.Catalogo.Area.Repository.AreaJpaRepository;

@Component
public class AreaRepositoryAdapter implements AreaRepositoryPort {
    private final AreaJpaRepository jpaRepository;
    private final AreaMapper mapper;

    public AreaRepositoryAdapter(AreaJpaRepository jpaRepository, AreaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Area> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Area> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
