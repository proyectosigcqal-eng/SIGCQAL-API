package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Model.SeguimientoMemorandum;
import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Port.ISeguimientoMemorandumPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Mapper.SeguimientoMemorandumMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Repository.SeguimientoMemorandumJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeguimientoMemorandumAdapter implements ISeguimientoMemorandumPort {

    private final SeguimientoMemorandumJpaRepository repository;
    private final SeguimientoMemorandumMapper mapper;

    @Override
    public SeguimientoMemorandum guardar(SeguimientoMemorandum seguimientoMemorandum) {
        var entity = mapper.toEntity(seguimientoMemorandum);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<SeguimientoMemorandum> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeguimientoMemorandum> listarPorMemorandumId(Long idMemo) {
        return repository.findByMemorandum_Id(idMemo)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}

