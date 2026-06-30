package com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Adapter;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.DemandaAmparo.Port.DemandaAmparoRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Repository.DemandaAmparoJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DemandaAmparoAdapter implements DemandaAmparoRepositoryPort {

    private final DemandaAmparoJpaRepository repository;

    @Override
    public boolean existsById(Integer idDemandaAmparo) {
        return repository.existsById(idDemandaAmparo);
    }
}
