package com.sigcqal.api.infra.ModuloCorrespondencia.ReasignacionMemorandum.Adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionMemorandum.Model.ReasignacionMemorandum;
import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionMemorandum.Port.IReasignacionMemorandumPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Repository.AcuseReciboInternoJpaRepository;
import com.sigcqal.api.infra.ModuloCorrespondencia.ReasignacionMemorandum.Mapper.ReasignacionMemorandumMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReasignacionMemorandumAdapter implements IReasignacionMemorandumPort {

    private final AcuseReciboInternoJpaRepository repository;
    private final ReasignacionMemorandumMapper mapper;

    @Override
    public List<ReasignacionMemorandum> findPendientes() {
        return repository.findByEsDelAreaFalse()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}

