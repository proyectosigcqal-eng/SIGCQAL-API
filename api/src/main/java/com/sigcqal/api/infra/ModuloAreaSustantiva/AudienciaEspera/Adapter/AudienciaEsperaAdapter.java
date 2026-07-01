package com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaEspera.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Model.AudienciaEspera;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Port.AudienciaEsperaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaEspera.Entity.AudienciaEsperaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaEspera.Mapper.AudienciaEsperaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaEspera.Repository.AudienciaEsperaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AudienciaEsperaAdapter implements AudienciaEsperaRepositoryPort {

    private final AudienciaEsperaJpaRepository repository;
    private final AudienciaEsperaMapper mapper;

    @Override
    public AudienciaEspera save(AudienciaEspera audienciaEspera) {
        AudienciaEsperaEntity entity = mapper.toEntity(audienciaEspera);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<AudienciaEspera> findById(Integer idAudienciaEspera) {
        return repository.findById(idAudienciaEspera).map(mapper::toDomain);
    }

    @Override
    public List<AudienciaEspera> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Integer idAudienciaEspera) {
        return repository.existsById(idAudienciaEspera);
    }
}
