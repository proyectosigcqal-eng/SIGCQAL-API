package com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;

public interface ExpedienteRepositoryPort {
    Expediente save(Expediente expediente);
    Optional<Expediente> findByFolio(String folio);
    Optional<Expediente> findTopByFolioPrefix(String prefix);
    List<Expediente> findAll();
    
}
