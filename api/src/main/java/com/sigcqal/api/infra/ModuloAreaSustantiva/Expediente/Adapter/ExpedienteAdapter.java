package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port.ExpedienteRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Mapper.ExpedienteMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;

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

    @Override
    public List<Expediente> findByFolio(String folio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByFolio'");
    }

    @Override
    public List<Expediente> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
}
