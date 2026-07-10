package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Model.AcuseReciboInterno;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Port.AcuseReciboInternoRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Mapper.AcuseReciboInternoMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Repository.AcuseReciboInternoJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AcuseReciboInternoAdapter implements AcuseReciboInternoRepositoryPort {

    private final AcuseReciboInternoJpaRepository repository;
    private final AcuseReciboInternoMapper mapper;

    @Override
    public List<AcuseReciboInterno> findByUsuario(Long idUsuario) {
        return repository.findByUsuarioRevisor_IdConRelaciones(idUsuario)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AcuseReciboInterno> findById(Long id) {
        return repository.findByIdConRelaciones(id).map(mapper::toDomain);
    }

    @Override
public AcuseReciboInterno save(AcuseReciboInterno acuse) {
    var entity = mapper.toEntity(acuse);
    
    var saved = repository.save(entity);
    return mapper.toDomain(saved);
}

@Override
public boolean existePorMemorandum(Long idMemorandum) {
    return repository.existsByMemorandum_Id(idMemorandum);
}

@Override
public List<AcuseReciboInterno> findByArea(Long idArea) {
    return repository.findByEsDelAreaTrueAndMemorandum_Area_IdConRelaciones(idArea)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
}

@Override
public List<AcuseReciboInterno> findByIdMemorandum(Long idMemorandum) {
    return repository.findByMemorandum_IdConRelaciones(idMemorandum)
        .stream()
        .map(mapper::toDomain)
        .collect(Collectors.toList());
}
@Override
public List<AcuseReciboInterno> findAll() {
    return repository.findAllConRelaciones()
        .stream()
        .map(mapper::toDomain)
        .collect(Collectors.toList());
}
}