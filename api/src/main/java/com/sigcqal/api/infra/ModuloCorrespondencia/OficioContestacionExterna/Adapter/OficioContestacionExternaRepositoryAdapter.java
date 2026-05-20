package com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Adapter;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Model.OficioContestacionExterna;
import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Port.OficioContestacionExternaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Mapper.OficioContestacionExternaMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Repository.OficioContestacionExternaJpaRepository;

public class OficioContestacionExternaRepositoryAdapter implements OficioContestacionExternaRepositoryPort {
    private final OficioContestacionExternaJpaRepository jpaRepository;
    private final OficioContestacionExternaMapper mapper;

    public OficioContestacionExternaRepositoryAdapter(
            OficioContestacionExternaJpaRepository jpaRepository,
            OficioContestacionExternaMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public OficioContestacionExterna guardar(OficioContestacionExterna oficio) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(oficio)));
    }

    @Override
    public Optional<OficioContestacionExterna> buscarPorCorrespondencia(Long idCorrespondencia) {
        return jpaRepository.findByCorrespondencia_Id(idCorrespondencia).map(mapper::toDomain);
    }

    @Override
    public List<OficioContestacionExterna> listarTodos() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
