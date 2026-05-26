package com.sigcqal.api.infra.Catalogo.EstatusExpediente.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.EstatusExpediente.Model.EstatusExpediente;
import com.sigcqal.api.domain.Catalogo.EstatusExpediente.Port.EstatusExpedienteRepositoryPort;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Mapper.EstatusExpedienteMapper;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Repository.EstatusExpedienteJpaRepository;

@Component
public class EstatusExpedienteRepositoryAdapter implements EstatusExpedienteRepositoryPort {
    private final EstatusExpedienteJpaRepository jpaRepository;
    private final EstatusExpedienteMapper mapper;

    public EstatusExpedienteRepositoryAdapter(EstatusExpedienteJpaRepository jpaRepository, EstatusExpedienteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<EstatusExpediente> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<EstatusExpediente> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
