package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Model.QuejasAri;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Port.QuejasAriRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Entity.QuejasAriEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Mapper.QuejasAriMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Repository.QuejasAriJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuejasAriAdapter implements QuejasAriRepositoryPort {

    private final QuejasAriJpaRepository jpaRepository;
    private final QuejasAriMapper mapper;

    @Override
    public QuejasAri save(QuejasAri quejasAri) {
        var entity = mapper.toEntity(quejasAri);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existeNumExpediente(String numExpediente) {
        return jpaRepository.findByNumExpedienteOficial(numExpediente).isPresent();
    }

    @Override
    public List<QuejasAri> findAll() {
        List<QuejasAriEntity> entities = jpaRepository.findAll();
        return entities.stream()
                       .map(mapper::toDomain)
                       .collect(Collectors.toList());
    }

    @Override
    public List<QuejasAri> findByIdQueja(Long idQueja) {
        List<QuejasAriEntity> entities = jpaRepository.findByIdQueja(idQueja);
        return entities.stream()
                       .map(mapper::toDomain)
                       .collect(Collectors.toList());
    }

    @Override
    public Optional<QuejasAri> buscarPorId(Long id) {
        return jpaRepository.findByIdWithRelations(id)
                            .map(mapper::toDomain);
    }
}
