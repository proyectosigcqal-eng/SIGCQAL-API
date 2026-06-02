package com.sigcqal.api.infra.Catalogo.Direccion.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Direccion.Model.Direccion;
import com.sigcqal.api.domain.Catalogo.Direccion.Port.DireccionRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Direccion.Entity.DireccionEntity;
import com.sigcqal.api.infra.Catalogo.Direccion.Mapper.DireccionMapper;
import com.sigcqal.api.infra.Catalogo.Direccion.Repository.DireccionJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DireccionRepositoryAdapter implements DireccionRepositoryPort {

    private final DireccionJpaRepository repository;
    private final DireccionMapper mapper;

    @Override
    public Direccion save(Direccion direccion) {
        DireccionEntity entity = mapper.toEntity(direccion);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Direccion> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Direccion> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

}