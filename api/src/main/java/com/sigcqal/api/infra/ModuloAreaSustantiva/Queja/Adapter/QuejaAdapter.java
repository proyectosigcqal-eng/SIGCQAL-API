package com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.Queja;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Port.QuejaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Mapper.QuejaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuejaAdapter implements QuejaRepositoryPort {

    private final QuejaJPARepository repository;
    private final QuejaMapper mapper;

    @Override
    public Optional<Queja> findById(Integer idQueja) {
        return repository.findById(idQueja).map(mapper::toDomain);
    }

    @Override
    public List<Queja> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}