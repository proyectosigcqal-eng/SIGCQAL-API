package com.sigcqal.api.infra.Expediente.Adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Expediente.Model.Contribuyente;
import com.sigcqal.api.domain.Expediente.Port.ContribuyenteRepositoryPort;
import com.sigcqal.api.infra.Expediente.Entity.ContribuyenteEntity;
import com.sigcqal.api.infra.Expediente.Mapper.ContribuyenteMapper;

import com.sigcqal.api.infra.Expediente.Repository.ContribuyenteJPARepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContribuyenteAdapter implements ContribuyenteRepositoryPort {

    private final ContribuyenteJPARepository repository;
    private final ContribuyenteMapper mapper;

    @Override
    public Contribuyente save(Contribuyente contribuyente) {
        ContribuyenteEntity entity = mapper.toEntity(contribuyente);
        ContribuyenteEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Contribuyente> findByRfc(String rfc) {
        return repository.findByRfc(rfc).map(mapper::toDomain);
    }

    @Override
    public Optional<Contribuyente> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
