package com.sigcqal.api.domain.ModuloAreaSustantiva.DemandaAmparo.Port;

import java.util.Optional;

import com.sigcqal.api.web.ModuloAreaSustantiva.DemandaAmparo.Dto.EncabezadoHitoAmparoDto;

public interface DemandaAmparoRepositoryPort {

    boolean existsById(Integer idDemandaAmparo);

    Optional<EncabezadoHitoAmparoDto> obtenerEncabezadoPorAmparoId(Long idDemandaAmparo);
}
