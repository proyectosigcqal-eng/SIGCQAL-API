package com.sigcqal.api.domain.Expediente.Port;

import java.util.Optional;

import com.sigcqal.api.domain.Expediente.Model.Contribuyente;

public interface ContribuyenteRepositoryPort {
    Contribuyente save(Contribuyente contribuyente);
    Optional<Contribuyente> findByRfc(String rfc);
    Optional<Contribuyente> findById(Long id);
}
