package com.sigcqal.api.infra.Catalogo.CatEstatusSustantiva.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.CatEstatusSustantiva.Model.CatEstatusSustantiva;
import com.sigcqal.api.domain.Catalogo.CatEstatusSustantiva.Port.CatEstatusSustantivaRepositoryPort;
import com.sigcqal.api.infra.Catalogo.CatEstatusSustantiva.Mapper.CatEstatusSustantivaMapper;
import com.sigcqal.api.infra.Catalogo.CatEstatusSustantiva.Repository.CatEstatusSustantivaJpaRepository;

@Component
public class CatEstatusSustantivaRepositoryAdapter implements CatEstatusSustantivaRepositoryPort {
    private final CatEstatusSustantivaJpaRepository jpaRepository;
    private final CatEstatusSustantivaMapper mapper;

    public CatEstatusSustantivaRepositoryAdapter(CatEstatusSustantivaJpaRepository jpaRepository, CatEstatusSustantivaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CatEstatusSustantiva> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<CatEstatusSustantiva> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
