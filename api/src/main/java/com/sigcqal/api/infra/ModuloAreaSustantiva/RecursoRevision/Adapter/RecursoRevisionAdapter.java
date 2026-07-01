package com.sigcqal.api.infra.ModuloAreaSustantiva.RecursoRevision.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Model.RecursoRevision;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Port.RecursoRevisionRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RecursoRevision.Entity.RecursoRevisionEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RecursoRevision.Mapper.RecursoRevisionMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RecursoRevision.Repository.RecursoRevisionJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecursoRevisionAdapter implements RecursoRevisionRepositoryPort {

    private final RecursoRevisionJpaRepository repository;
    private final RecursoRevisionMapper mapper;

    @Override
    public RecursoRevision save(RecursoRevision recursoRevision) {
        RecursoRevisionEntity entity = mapper.toEntity(recursoRevision);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<RecursoRevision> findById(Integer idRecursoRevision) {
        return repository.findById(idRecursoRevision).map(mapper::toDomain);
    }

    @Override
    public List<RecursoRevision> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Integer idRecursoRevision) {
        return repository.existsById(idRecursoRevision);
    }
}
