package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Adapter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoOficio.Model.SeguimientoOficio;
import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoOficio.Port.SeguimientoOficioPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Mapper.SeguimientoOficioMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Repository.SeguimientoOficioJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeguimientoOficioAdapter implements SeguimientoOficioPort {

    private final SeguimientoOficioJpaRepository repository;
    private final SeguimientoOficioMapper mapper;

    @Override
    public SeguimientoOficio guardar(SeguimientoOficio seguimientoOficio) {
        var entity = mapper.toEntity(seguimientoOficio);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<SeguimientoOficio> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeguimientoOficio> listarPorOficioId(Integer idOficio) {
        return repository.findByOficio_IdOficio(idOficio.longValue())
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}