package com.sigcqal.api.infra.Catalogo.Asesor.Adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Asesor.Model.Asesor;
import com.sigcqal.api.domain.Catalogo.Asesor.Port.AsesorRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Asesor.Mapper.AsesorMapper;
import com.sigcqal.api.infra.Catalogo.Asesor.Repository.AsesorJpaRepository;

@Component
public class AsesorRepositoryAdapter implements AsesorRepositoryPort {
    private final AsesorJpaRepository jpaRepository;
    private final AsesorMapper mapper;

    public AsesorRepositoryAdapter(AsesorJpaRepository jpaRepository, AsesorMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Asesor> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
