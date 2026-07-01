package com.sigcqal.api.domain.ModuloAreaSustantiva.RLCir.Port;

import java.util.List;
import java.util.Optional;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RLCir.Model.RLCir;

public interface RLCirRepositoryPort {
    RLCir save(RLCir rlCir);
    List<RLCir> findAll();
    List<RLCir> findByIdExpediente(Long idExpediente);
    Optional<RLCir> buscarPorId(Long id);
}