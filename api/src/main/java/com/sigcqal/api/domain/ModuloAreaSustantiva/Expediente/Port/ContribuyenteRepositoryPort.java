package com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port;

import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Contribuyente;

public interface ContribuyenteRepositoryPort {
    Contribuyente save(Contribuyente contribuyente);
    Optional<Contribuyente> findByRfc(String rfc);
    Optional<Contribuyente> findById(Long id);
}
