package com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoActoEmitido.Model.TipoActoEmitido;
import com.sigcqal.api.domain.Catalogo.TipoActoEmitido.Port.TipoActoEmitidoRepositoryPort;
import com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Mapper.TipoActoEmitidoMapper;
import com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Repository.TipoActoEmitidoJpaRepository;

@Component
public class TipoActoEmitidoRepositoryAdapter implements TipoActoEmitidoRepositoryPort {
    private final TipoActoEmitidoJpaRepository jpaRepository;
    private final TipoActoEmitidoMapper mapper;

    public TipoActoEmitidoRepositoryAdapter(TipoActoEmitidoJpaRepository jpaRepository, TipoActoEmitidoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<TipoActoEmitido> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<TipoActoEmitido> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
