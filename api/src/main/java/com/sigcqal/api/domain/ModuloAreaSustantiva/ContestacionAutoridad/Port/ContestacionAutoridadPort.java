package com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Port;

import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Model.ContestacionAutoridad;

public interface ContestacionAutoridadPort {
    ContestacionAutoridad guardar(ContestacionAutoridad contestacion);
    Optional<ContestacionAutoridad> findByFolio(String folio);
}