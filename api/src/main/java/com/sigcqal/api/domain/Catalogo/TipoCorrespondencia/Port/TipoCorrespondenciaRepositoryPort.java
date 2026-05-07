package com.sigcqal.api.domain.Catalogo.TipoCorrespondencia.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.TipoCorrespondencia.Model.TipoCorrespondencia;

public interface TipoCorrespondenciaRepositoryPort {
    List<TipoCorrespondencia> findAll();

    Optional<TipoCorrespondencia> findById(Integer id);

    Optional<TipoCorrespondencia> findByIdNatural(String idNatural);
}
