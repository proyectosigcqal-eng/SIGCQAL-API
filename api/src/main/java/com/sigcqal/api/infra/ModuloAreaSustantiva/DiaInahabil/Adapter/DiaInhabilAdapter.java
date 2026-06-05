package com.sigcqal.api.infra.ModuloAreaSustantiva.DiaInahabil.Adapter;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Model.DiaInhabil;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port.DiaInhabilRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.DiaInahabil.Repository.DiaInhabilJpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DiaInhabilAdapter implements DiaInhabilRepositoryPort {

    private final DiaInhabilJpaRepository repository;

    @Override
    public List<DiaInhabil> findByRangoFechas(LocalDate desde, LocalDate hasta) {
        return repository.findByRango(desde, hasta)
                .stream()
                .map(e -> DiaInhabil.builder()
                        .id(e.getId())
                        .fecha(e.getFecha())
                        .descripcion(e.getDescripcion())
                        .activo(e.getActivo())
                        .build())
                .collect(Collectors.toList());
    }
}