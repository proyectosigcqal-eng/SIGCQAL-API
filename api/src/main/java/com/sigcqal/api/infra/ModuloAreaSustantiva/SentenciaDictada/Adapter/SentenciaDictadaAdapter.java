package com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Model.SentenciaDictada;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Port.SentenciaDictadaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Entity.SentenciaDictadaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Mapper.SentenciaDictadaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Repository.SentenciaDictadaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SentenciaDictadaAdapter implements SentenciaDictadaRepositoryPort {

    private final SentenciaDictadaJpaRepository repository;
    private final SentenciaDictadaMapper mapper;

    @Override
    public SentenciaDictada save(SentenciaDictada sentenciaDictada) {
        SentenciaDictadaEntity entity = mapper.toEntity(sentenciaDictada);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<SentenciaDictada> findById(Integer idSentencia) {
        return repository.findById(idSentencia).map(mapper::toDomain);
    }

    @Override
    public List<SentenciaDictada> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Integer idSentencia) {
        return repository.existsById(idSentencia);
    }
}
