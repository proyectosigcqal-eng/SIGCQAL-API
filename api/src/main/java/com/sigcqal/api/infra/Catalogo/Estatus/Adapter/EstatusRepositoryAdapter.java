package com.sigcqal.api.infra.Catalogo.Estatus.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Estatus.Model.Estatus;
import com.sigcqal.api.domain.Catalogo.Estatus.Port.EstatusRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Estatus.Maper.EstatusMaper;
import com.sigcqal.api.infra.Catalogo.Estatus.Repository.EstatusJpaRepository;


@Component
public class EstatusRepositoryAdapter implements EstatusRepositoryPort {

    @Autowired
    private final EstatusJpaRepository jpaRepository;
    private EstatusMaper maper;

    public EstatusRepositoryAdapter(EstatusJpaRepository jpaRepository){
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Estatus> findByName(String nombre) {
        return jpaRepository.findByNombreEstatus(nombre)
            .map(maper::toDomain);
    }

    @Override
    public List<Estatus> ListAll() {
        return jpaRepository.findAll().stream()
            .map(maper::toDomain)
            .collect(Collectors.toList());
    }

}

    