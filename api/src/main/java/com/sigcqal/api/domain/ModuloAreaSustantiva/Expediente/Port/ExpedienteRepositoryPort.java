package com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;

public interface ExpedienteRepositoryPort {
    Expediente save(Expediente expediente);
    List<Expediente> findByFolio(String folio);
    List<Expediente> findAll();
    
}
