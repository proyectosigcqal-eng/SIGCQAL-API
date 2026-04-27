package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Model.AcuseCorrespondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Port.AcuseCorrespondenciaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Mapper.AcuseCorrespondenciaMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Repository.AcuseCorrespondenciaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AcuseCorrespondenciaAdapter implements AcuseCorrespondenciaRepositoryPort {

    private final AcuseCorrespondenciaJpaRepository repository;
    private final AcuseCorrespondenciaMapper mapper;

    @Override
    public AcuseCorrespondencia save(AcuseCorrespondencia acuse) {
        var saved = repository.save(mapper.toEntity(acuse));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<AcuseCorrespondencia> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<AcuseCorrespondencia> findByArea(Long idArea) {
        return repository
                .findByEsDelAreaTrueAndCorrespondencia_Area_Id(idArea)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}