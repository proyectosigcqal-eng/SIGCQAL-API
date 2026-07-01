package com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaEjecutoria.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Model.SentenciaEjecutoria;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Port.SentenciaEjecutoriaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaEjecutoria.Entity.SentenciaEjecutoriaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaEjecutoria.Mapper.SentenciaEjecutoriaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaEjecutoria.Repository.SentenciaEjecutoriaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SentenciaEjecutoriaAdapter implements SentenciaEjecutoriaRepositoryPort {

    private final SentenciaEjecutoriaJpaRepository repository;
    private final SentenciaEjecutoriaMapper mapper;

    @Override
    public SentenciaEjecutoria save(SentenciaEjecutoria sentenciaEjecutoria) {
        SentenciaEjecutoriaEntity entity = mapper.toEntity(sentenciaEjecutoria);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<SentenciaEjecutoria> findById(Integer idSentenciaEjecutoria) {
        return repository.findById(idSentenciaEjecutoria).map(mapper::toDomain);
    }

    @Override
    public List<SentenciaEjecutoria> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Integer idSentenciaEjecutoria) {
        return repository.existsById(idSentenciaEjecutoria);
    }
}
