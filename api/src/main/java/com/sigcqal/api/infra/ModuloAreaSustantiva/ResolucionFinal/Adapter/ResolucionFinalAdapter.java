package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port.ResolucionFinalRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper.ResolucionFinalMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Repository.ResolucionFinalJPARepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResolucionFinalAdapter implements ResolucionFinalRepositoryPort {

    private final ResolucionFinalJPARepository repository;
    private final ResolucionFinalMapper        mapper;

    @Override
    public ResolucionFinal save(ResolucionFinal resolucionFinal) {
        ResolucionFinalEntity entity = mapper.toEntity(resolucionFinal);
        ResolucionFinalEntity guardado = repository.save(entity);
        return mapper.toDomain(guardado);
    }

    @Override
    public Optional<ResolucionFinal> findById(Integer idResolucionFinal) {
        return repository.findById(idResolucionFinal)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<ResolucionFinalEntity> findEntityById(Integer idResolucionFinal) {
        return repository.findById(idResolucionFinal);
    }

    @Override
    public List<ResolucionFinal> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResolucionFinal> findByIdExpediente(Integer idExpediente) {
        return repository.findByIdExpediente(idExpediente)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByIdExpediente(Integer idExpediente) {
        return repository.existsByIdExpediente(idExpediente);
    }
}