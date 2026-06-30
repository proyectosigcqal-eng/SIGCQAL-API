package com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.DemandaAmparo.Port.DemandaAmparoRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Repository.DemandaAmparoJpaRepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.DemandaAmparo.Dto.EncabezadoHitoAmparoDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DemandaAmparoAdapter implements DemandaAmparoRepositoryPort {

    private final DemandaAmparoJpaRepository repository;

    @Override
    public boolean existsById(Integer idDemandaAmparo) {
        return repository.existsById(idDemandaAmparo);
    }

    @Override
    public Optional<EncabezadoHitoAmparoDto> obtenerEncabezadoPorAmparoId(Long idDemandaAmparo) {
        if (idDemandaAmparo == null) {
            return Optional.empty();
        }

        List<Object[]> rows = repository.findEncabezadoRawByAmparoId(idDemandaAmparo.intValue());
        if (rows.isEmpty()) {
            return Optional.empty();
        }

        Object[] row = rows.get(0);
        return Optional.of(EncabezadoHitoAmparoDto.builder()
                .idDemandaAmparo(((Number) row[0]).longValue())
                .folioGobierno((String) row[1])
                .numExpedienteOficialQueja((String) row[2])
                .nombreContribuyente((String) row[3])
                .build());
    }
}
