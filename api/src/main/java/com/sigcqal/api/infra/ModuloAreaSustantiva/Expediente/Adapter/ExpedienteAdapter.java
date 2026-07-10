package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Adapter;

import java.util.List;
import java.util.Optional;

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
    public Optional<Expediente> findByFolio(String folio) {
        Optional<ExpedienteEntity> entityOptional = repository.findByFolioGobiernoConRelaciones(folio);
    
    return entityOptional.map(entity -> mapper.toDomain(entity));

    }

    @Override
    public Optional<Expediente> findTopByFolioPrefix(String prefix) {
        Optional<ExpedienteEntity> entityOptional = repository.findTopByFolioGobiernoStartingWithOrderByFolioGobiernoDesc(prefix);
        return entityOptional.map(entity -> mapper.toDomain(entity));
    }


    @Override
    public List<Expediente> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean existsByFolio(String folio) {
        return repository.existsByFolioGobierno(folio);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id.intValue());
    }

@Override
public Optional<ExpedienteEntity> findEntityByFolio(String folio) {
    return repository.findByFolioGobiernoConRelaciones(folio);
}

@Override
public Optional<Expediente> findById(Integer id) {
    return repository.findByIdConRelaciones(id)
            .map(mapper::toDomain);
}

    public Optional<Expediente> buscarPorId(Long id) {
        Optional<ExpedienteEntity> entityOptional = repository.findByIdConRelaciones(id.intValue());
        
        return entityOptional.map(entity -> mapper.toDomain(entity));
    }
  
}
