package com.sigcqal.api.infra.Catalogo.CatTipoProceso.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.CatTipoProceso.Model.CatTipoProceso;
import com.sigcqal.api.domain.Catalogo.CatTipoProceso.Port.CatTipoProcesoRepositoryPort;
import com.sigcqal.api.infra.Catalogo.CatTipoProceso.Mapper.CatTipoProcesoMapper;
import com.sigcqal.api.infra.Catalogo.CatTipoProceso.Repository.CatTipoProcesoJpaRepository;

@Component
public class CatTipoProcesoRepositoryAdapter implements CatTipoProcesoRepositoryPort {
    private final CatTipoProcesoJpaRepository jpaRepository;
    private final CatTipoProcesoMapper mapper;

    public CatTipoProcesoRepositoryAdapter(CatTipoProcesoJpaRepository jpaRepository, CatTipoProcesoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CatTipoProceso> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<CatTipoProceso> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
