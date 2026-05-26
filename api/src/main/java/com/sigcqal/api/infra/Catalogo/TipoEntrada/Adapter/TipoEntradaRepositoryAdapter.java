package com.sigcqal.api.infra.Catalogo.TipoEntrada.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoEntrada.Model.TipoEntrada;
import com.sigcqal.api.domain.Catalogo.TipoEntrada.Port.TipoEntradaRepositoryPort;
import com.sigcqal.api.infra.Catalogo.TipoEntrada.Mapper.TipoEntradaMapper;
import com.sigcqal.api.infra.Catalogo.TipoEntrada.Repository.TipoEntradaJpaRepository;

@Component
public class TipoEntradaRepositoryAdapter implements TipoEntradaRepositoryPort {
    private final TipoEntradaJpaRepository jpaRepository;
    private final TipoEntradaMapper mapper;

    public TipoEntradaRepositoryAdapter(TipoEntradaJpaRepository jpaRepository, TipoEntradaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<TipoEntrada> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<TipoEntrada> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
