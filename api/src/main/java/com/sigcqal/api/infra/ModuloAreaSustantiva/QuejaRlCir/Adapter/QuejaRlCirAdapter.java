package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejaRlCir.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejaRlCir.Model.QuejaRlCir;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejaRlCir.Port.QuejaRlCirRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejaRlCir.Entity.QuejaRlCirEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejaRlCir.Mapper.QuejaRlCirMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejaRlCir.Repository.QuejaRlCirJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuejaRlCirAdapter implements QuejaRlCirRepositoryPort {

    private final QuejaRlCirJpaRepository jpaRepository;
    private final QuejaRlCirMapper mapper;

    @Override
    public QuejaRlCir save(QuejaRlCir quejaRlCir) {
        QuejaRlCirEntity entity = mapper.toEntity(quejaRlCir);
        QuejaRlCirEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<QuejaRlCir> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuejaRlCir> findByIdResolucionFinal(Long idResolucionFinal) {
        return jpaRepository.findByIdResolucionFinal(idResolucionFinal).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<QuejaRlCir> buscarPorId(Long id) {
        return jpaRepository.findByIdWithRelations(id)
                .map(mapper::toDomain);
    }
}