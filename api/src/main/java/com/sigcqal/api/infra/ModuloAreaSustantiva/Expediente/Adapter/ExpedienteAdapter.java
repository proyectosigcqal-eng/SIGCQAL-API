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
        Optional<ExpedienteEntity> entityOptional = repository.findByFolioGobierno(folio);
    
    // 2. Si la encuentra, la mapeamos al modelo de dominio. Si no, devolvemos Optional vacío.
    // Asumiendo que tienes un mapper o un método para convertir:
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
public boolean existsById(Long id) {
    return repository.existsById(id.intValue());
}

@Override
public Optional<ExpedienteEntity> findEntityByFolio(String folio) {
    // Si tu adaptador usa un JpaRepository, será algo así:
    return repository.findByFolioGobierno(folio);
}

@Override
public Optional<Expediente> findById(Integer id) {
    return repository.findById(id)
            .map(mapper::toDomain); // Map your entity back to the domain model
}

    public Optional<Expediente> buscarPorId(Long id) {
        // Buscamos la entidad en la base de datos (usando id.intValue() siguiendo tu lógica de existsById)
        Optional<ExpedienteEntity> entityOptional = repository.findById(id.intValue());
        
        // Si existe, la mapeamos al dominio; si no, devuelve un Optional vacío de manera limpia
        return entityOptional.map(entity -> mapper.toDomain(entity));
    }
  
}
