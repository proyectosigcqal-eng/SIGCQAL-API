package com.sigcqal.api.infra.ModuloCorrespondencia.ReasignacionCorrespondencia.Adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionCorrespondencia.Model.ReasignacionCorrespondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionCorrespondencia.Port.IReasignacionCorrespondenciaPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Repository.AcuseReciboInternoJpaRepository;
import com.sigcqal.api.infra.ModuloCorrespondencia.ReasignacionCorrespondencia.Mapper.ReasignacionCorrespondenciaMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReasignacionCorrespondenciaAdapter implements IReasignacionCorrespondenciaPort {

    private final AcuseReciboInternoJpaRepository repository;
    private final ReasignacionCorrespondenciaMapper mapper;

    @Override
    public List<ReasignacionCorrespondencia> findPendientes() {
        return repository.findByEsDelAreaFalse()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}

