package com.sigcqal.api.infra.Catalogo.Autoridad.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Autoridad.Model.Autoridad;
import com.sigcqal.api.domain.Catalogo.Autoridad.Port.AutoridadRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Autoridad.Mapper.AutoridadMapper;
import com.sigcqal.api.infra.Catalogo.Autoridad.Repository.AutoridadJpaRepository;

@Component
public class AutoridadRepositoryAdapter implements AutoridadRepositoryPort {
    private final AutoridadJpaRepository jpaRepository;
    private final AutoridadMapper mapper;

    public AutoridadRepositoryAdapter(AutoridadJpaRepository jpaRepository, AutoridadMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Autoridad> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Autoridad> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
