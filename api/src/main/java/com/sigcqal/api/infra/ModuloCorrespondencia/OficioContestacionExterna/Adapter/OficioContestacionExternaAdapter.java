package com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Model.OficioContestacionExterna;
import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Port.OficioContestacionExternaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Mapper.OficioContestacionExternaMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Repository.OficioContestacionExternaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OficioContestacionExternaAdapter implements OficioContestacionExternaRepositoryPort {

    private final OficioContestacionExternaJpaRepository repository;
    private final OficioContestacionExternaMapper mapper;

    @Override
    public OficioContestacionExterna guardar(OficioContestacionExterna oficio) {
        var entity = mapper.toEntity(oficio);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<OficioContestacionExterna> buscarPorCorrespondencia(Long idCorrespondencia) {
        return repository.findByCorrespondencia_Id(idCorrespondencia).map(mapper::toDomain);
    }

    @Override
    public List<OficioContestacionExterna> listarTodos() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}
