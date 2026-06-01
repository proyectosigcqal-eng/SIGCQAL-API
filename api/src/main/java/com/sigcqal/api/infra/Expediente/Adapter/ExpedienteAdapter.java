package com.sigcqal.api.infra.Expediente.Adapter;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Expediente.Model.Expediente;
import com.sigcqal.api.domain.Expediente.Port.ExpedienteRepositoryPort;
import com.sigcqal.api.infra.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.Expediente.Mapper.ExpedienteMapper;
import com.sigcqal.api.infra.Expediente.Repository.ExpedienteJPARepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExpedienteAdapter implements ExpedienteRepositoryPort {

    private final ExpedienteJPARepository repository;
    private final ExpedienteMapper mapper;

    @Override
    public Expediente save(Expediente expediente) {
        ExpedienteEntity entity = mapper.toEntity(expediente);
        ExpedienteEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}
