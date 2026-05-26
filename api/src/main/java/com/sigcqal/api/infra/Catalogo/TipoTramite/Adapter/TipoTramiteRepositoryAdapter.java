package com.sigcqal.api.infra.Catalogo.TipoTramite.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoTramite.Model.TipoTramite;
import com.sigcqal.api.domain.Catalogo.TipoTramite.Port.TipoTramiteRepositoryPort;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Mapper.TipoTramiteMapper;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Repository.TipoTramiteJpaRepository;

@Component
public class TipoTramiteRepositoryAdapter implements TipoTramiteRepositoryPort {
    private final TipoTramiteJpaRepository jpaRepository;
    private final TipoTramiteMapper mapper;

    public TipoTramiteRepositoryAdapter(TipoTramiteJpaRepository jpaRepository, TipoTramiteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<TipoTramite> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<TipoTramite> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
