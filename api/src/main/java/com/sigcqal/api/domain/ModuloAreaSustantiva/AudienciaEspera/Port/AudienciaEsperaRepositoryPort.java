package com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Model.AudienciaEspera;

public interface AudienciaEsperaRepositoryPort {

    AudienciaEspera save(AudienciaEspera audienciaEspera);

    Optional<AudienciaEspera> findById(Integer idAudienciaEspera);

    List<AudienciaEspera> findAll();

    boolean existsById(Integer idAudienciaEspera);
}
