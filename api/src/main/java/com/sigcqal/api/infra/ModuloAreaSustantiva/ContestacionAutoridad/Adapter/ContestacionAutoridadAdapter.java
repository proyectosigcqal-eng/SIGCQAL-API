package com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Model.ContestacionAutoridad;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Port.ContestacionAutoridadPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Mapper.ContestacionAutoridadMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Repository.ContestacionAutoridadJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContestacionAutoridadAdapter implements ContestacionAutoridadPort {

    private final ContestacionAutoridadJpaRepository jpaRepository;
    private final ContestacionAutoridadMapper mapper;

    public ContestacionAutoridad guardar(ContestacionAutoridad contestacion) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(contestacion)));
    }

    public Optional<ContestacionAutoridad> findByFolio(String folio) {
        return jpaRepository.findByFolioExpediente(folio).map(mapper::toDomain);
    }
}