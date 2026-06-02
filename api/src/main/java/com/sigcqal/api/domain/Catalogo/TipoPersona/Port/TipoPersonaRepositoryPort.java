package com.sigcqal.api.domain.Catalogo.TipoPersona.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.TipoPersona.Model.TipoPersona;

public interface TipoPersonaRepositoryPort {
    Optional<TipoPersona> findById(Long id);
    List<TipoPersona> findAll();
    TipoPersona save(TipoPersona tipoPersona);
}
