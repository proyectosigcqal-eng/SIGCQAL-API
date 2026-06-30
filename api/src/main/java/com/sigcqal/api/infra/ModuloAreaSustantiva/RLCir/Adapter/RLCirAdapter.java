package com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RLCir.Model.RLCir;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RLCir.Port.RLCirRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Entity.RLCirEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Mapper.RLCirMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Repository.RLCirJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RLCirAdapter implements RLCirRepositoryPort {

    private final RLCirJpaRepository jpaRepository;
    private final RLCirMapper mapper;

    @Override
    public RLCir save(RLCir rlCir) {
        RLCirEntity entity = mapper.toEntity(rlCir);
        RLCirEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<RLCir> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<RLCir> findByIdExpediente(Long idExpediente) {
        return jpaRepository.findByIdExpediente(idExpediente).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RLCir> buscarPorId(Long id) {
        return jpaRepository.findByIdWithRelations(id)
                .map(mapper::toDomain);
    }
}