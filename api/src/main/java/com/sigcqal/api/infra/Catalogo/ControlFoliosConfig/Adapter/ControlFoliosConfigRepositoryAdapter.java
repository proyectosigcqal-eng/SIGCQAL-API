package com.sigcqal.api.infra.Catalogo.ControlFoliosConfig.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.ControlFoliosConfig.Model.ControlFoliosConfig;
import com.sigcqal.api.domain.Catalogo.ControlFoliosConfig.Port.ControlFoliosConfigRepositoryPort;
import com.sigcqal.api.infra.Catalogo.ControlFoliosConfig.Mapper.ControlFoliosConfigMapper;
import com.sigcqal.api.infra.Catalogo.ControlFoliosConfig.Repository.ControlFoliosConfigJpaRepository;

@Component
public class ControlFoliosConfigRepositoryAdapter implements ControlFoliosConfigRepositoryPort {
    private final ControlFoliosConfigJpaRepository jpaRepository;
    private final ControlFoliosConfigMapper mapper;

    public ControlFoliosConfigRepositoryAdapter(ControlFoliosConfigJpaRepository jpaRepository, ControlFoliosConfigMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ControlFoliosConfig> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ControlFoliosConfig> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
