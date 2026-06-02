package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Contribuyente;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port.ContribuyenteRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ContribuyenteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Mapper.ContribuyenteMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ContribuyenteJPARepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContribuyenteAdapter implements ContribuyenteRepositoryPort {

    private final ContribuyenteJPARepository repository;
    private final ContribuyenteMapper mapper;

    @Override
    public Contribuyente save(Contribuyente contribuyente) {
        ContribuyenteEntity entity = mapper.toEntity(contribuyente);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Contribuyente> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Contribuyente> findByIdPersona(Long idPersona) {
        return repository.findByIdPersona(idPersona).map(mapper::toDomain);
    }

    @Override
    public List<Contribuyente> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }



}