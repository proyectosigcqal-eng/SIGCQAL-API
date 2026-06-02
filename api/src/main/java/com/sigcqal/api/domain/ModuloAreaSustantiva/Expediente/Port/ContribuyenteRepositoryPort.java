package com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Contribuyente;

public interface ContribuyenteRepositoryPort {
    Contribuyente save(Contribuyente contribuyente);
    Optional<Contribuyente> findById(Long id);
    Optional<Contribuyente> findByIdPersona(Long idPersona);
    List<Contribuyente> findAll();

}