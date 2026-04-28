package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Model.SeguimientoCorrespondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Port.ISeguimientoCorrespondenciaPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Mapper.SeguimientoCorrespondenciaMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Repository.SeguimientoCorrespondenciaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeguimientoCorrespondenciaAdapter implements ISeguimientoCorrespondenciaPort {

    private final SeguimientoCorrespondenciaJpaRepository repository;
    private final SeguimientoCorrespondenciaMapper mapper;

    @Override
    public SeguimientoCorrespondencia guardar(SeguimientoCorrespondencia seguimientoCorrespondencia) {
        var entity = mapper.toEntity(seguimientoCorrespondencia);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<SeguimientoCorrespondencia> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeguimientoCorrespondencia> listarPorCorrespondenciaId(Integer idCorrespondencia) {
        return repository.findByCorrespondencia_IdCorrespondencia(idCorrespondencia.longValue())
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
