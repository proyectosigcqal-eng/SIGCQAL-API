package com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Model.AudienciaCelebrada;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Port.AudienciaCelebradaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Entity.AudienciaCelebradaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Mapper.AudienciaCelebradaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Repository.AudienciaCelebradaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AudienciaCelebradaAdapter implements AudienciaCelebradaRepositoryPort {

    private final AudienciaCelebradaJpaRepository repository;
    private final AudienciaCelebradaMapper mapper;

    @Override
    public AudienciaCelebrada save(AudienciaCelebrada audienciaCelebrada) {
        AudienciaCelebradaEntity entity = mapper.toEntity(audienciaCelebrada);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<AudienciaCelebrada> findById(Integer idAudienciaCelebrada) {
        return repository.findById(idAudienciaCelebrada).map(mapper::toDomain);
    }

    @Override
    public List<AudienciaCelebrada> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Integer idAudienciaCelebrada) {
        return repository.existsById(idAudienciaCelebrada);
    }
}
