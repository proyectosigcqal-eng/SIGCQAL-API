package com.sigcqal.api.infra.Catalogo.Rol.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Rol.Model.Rol;
import com.sigcqal.api.domain.Catalogo.Rol.Port.RolRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Rol.Mapper.RolMapper;
import com.sigcqal.api.infra.Catalogo.Rol.Repository.RolJpaRepository;

@Component
public class RolRepositoryAdapter implements RolRepositoryPort {
    private final RolJpaRepository jpaRepository;
    private final RolMapper mapper;

    public RolRepositoryAdapter(RolJpaRepository jpaRepository, RolMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Rol> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Rol> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
