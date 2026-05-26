package com.sigcqal.api.infra.Catalogo.Direccion.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Direccion.Model.Direccion;
import com.sigcqal.api.domain.Catalogo.Direccion.Port.DireccionRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Direccion.Mapper.DireccionMapper;
import com.sigcqal.api.infra.Catalogo.Direccion.Repository.DireccionJpaRepository;

@Component
public class DireccionRepositoryAdapter implements DireccionRepositoryPort {
    private final DireccionJpaRepository jpaRepository;
    private final DireccionMapper mapper;

    public DireccionRepositoryAdapter(DireccionJpaRepository jpaRepository, DireccionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Direccion> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Direccion> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
