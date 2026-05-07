package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Model.AcuseOficio;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Port.AcuseOficioRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Mapper.AcuseOficioMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Repository.AcuseOficioJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AcuseOficioAdapter implements AcuseOficioRepositoryPort {

    private final AcuseOficioJpaRepository repository;
    private final AcuseOficioMapper mapper;

    @Override
    public Optional<AcuseOficio> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public AcuseOficio save(AcuseOficio acuse) {
        var entity = mapper.toEntity(acuse);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<AcuseOficio> findByAreaAndEsDelAreaTrue(Long idArea) {
        return repository.findByEsDelAreaTrueAndOficio_Area_Id(idArea)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorOficio(Long idOficio) {
        return repository.existsByOficio_Id(idOficio);
    }
}