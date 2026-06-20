package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Adapter;

import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Model.QuejasAcci;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Port.QuejasAcciRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Mapper.QuejasAcciMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Repository.QuejasAcciJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuejasAcciAdapter implements QuejasAcciRepositoryPort {

    private final QuejasAcciJpaRepository repository;
    private final QuejasAcciMapper        mapper;

    @Override
    public QuejasAcci guardar(QuejasAcci acci) {
        return mapper.toDomain(repository.save(mapper.toEntity(acci)));
    }

    @Override
    public Optional<QuejasAcci> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<QuejasAcci> findByIdQueja(Long idQueja) {
        return repository.findByIdQueja(idQueja)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}