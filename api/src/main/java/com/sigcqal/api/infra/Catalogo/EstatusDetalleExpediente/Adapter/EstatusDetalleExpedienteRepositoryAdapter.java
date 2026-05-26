package com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.EstatusDetalleExpediente.Model.EstatusDetalleExpediente;
import com.sigcqal.api.domain.Catalogo.EstatusDetalleExpediente.Port.EstatusDetalleExpedienteRepositoryPort;
import com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Mapper.EstatusDetalleExpedienteMapper;
import com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Repository.EstatusDetalleExpedienteJpaRepository;

@Component
public class EstatusDetalleExpedienteRepositoryAdapter implements EstatusDetalleExpedienteRepositoryPort {
    private final EstatusDetalleExpedienteJpaRepository jpaRepository;
    private final EstatusDetalleExpedienteMapper mapper;

    public EstatusDetalleExpedienteRepositoryAdapter(EstatusDetalleExpedienteJpaRepository jpaRepository, EstatusDetalleExpedienteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<EstatusDetalleExpediente> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<EstatusDetalleExpediente> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
