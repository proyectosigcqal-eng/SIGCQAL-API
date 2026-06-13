package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port.ResolucionFinalRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper.ResolucionFinalMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Repository.ResolucionFinalJPARepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResolucionFinalAdapter implements ResolucionFinalRepositoryPort {
    private final ResolucionFinalJPARepository repository;
    private final ResolucionFinalMapper mapper;

    @Override
    public ResolucionFinal guardarResolucion(ResolucionFinal resolucion) {
        var entity = mapper.toEntity(resolucion);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ResolucionFinal> buscarPorExpediente(Integer idExpediente) {
        return repository.findByIdExpediente(idExpediente).map(mapper::toDomain);
    }

    @Override
    public boolean expedienteEnDictaminacion(Integer idExpediente) {
        return repository.expedienteEnDictaminacion(idExpediente);
    }
}
