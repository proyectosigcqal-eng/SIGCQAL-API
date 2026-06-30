package com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;

public interface ExpedienteRepositoryPort {
    Expediente save(Expediente expediente);
    Optional<Expediente> findByFolio(String folio);
    Optional<Expediente> findTopByFolioPrefix(String prefix);
    List<Expediente> findAll();
    boolean existsById(Long id);
    Optional<ExpedienteEntity> findEntityByFolio(String folio);
    Optional<Expediente> buscarPorId(Long id);
}
