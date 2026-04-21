package com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.BitacoraHistorica.Model.BitacoraHistorica;
import com.sigcqal.api.domain.ModuloCorrespondencia.BitacoraHistorica.Port.BitacoraHistoricaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Mapper.BitacoraHistoricaMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Repository.BitacoraHistoricaJpaRepository;

@Component
public class BitacoraHistoricaAdapter implements BitacoraHistoricaRepositoryPort {

    @Autowired
    private BitacoraHistoricaJpaRepository repository;

    @Autowired
    private BitacoraHistoricaMapper mapper;

    @Override
    public List<BitacoraHistorica> findByIdCorrespondencia(Long idCorrespondencia) {
        return repository.findByCorrespondenciaIdOrderByFechaMovimientoAsc(idCorrespondencia)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public BitacoraHistorica save(BitacoraHistorica bitacora) {
        var entity = mapper.toEntity(bitacora);
        return mapper.toDomain(repository.save(entity));
    }
}