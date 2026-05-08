package com.sigcqal.api.infra.Catalogo.TipoCorrespondencia.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.TipoCorrespondencia.Model.TipoCorrespondencia;
import com.sigcqal.api.domain.Catalogo.TipoCorrespondencia.Port.TipoCorrespondenciaRepositoryPort;
import com.sigcqal.api.infra.Catalogo.TipoCorrespondencia.Mapper.TipoCorrespondenciaMapper;
import com.sigcqal.api.infra.Catalogo.TipoCorrespondencia.Repository.TipoCorrespondenciaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TipoCorrespondenciaAdapter implements TipoCorrespondenciaRepositoryPort {
    private final TipoCorrespondenciaJpaRepository jpaRepository;
    private final TipoCorrespondenciaMapper mapper;

    @Override
    public List<TipoCorrespondencia> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<TipoCorrespondencia> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}